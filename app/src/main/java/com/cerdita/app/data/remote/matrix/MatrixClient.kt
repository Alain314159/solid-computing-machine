package com.cerdita.app.data.remote.matrix

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.cerdita.app.service.NtfyService
import dagger.hilt.android.qualifiers.ApplicationContext
import im.vector.matrix.android.api.Matrix
import im.vector.matrix.android.api.MatrixConfiguration
import im.vector.matrix.android.api.Session
import im.vector.matrix.android.api.auth.callback.AuthCallback
import im.vector.matrix.android.api.auth.callback.RegisterCallback
import im.vector.matrix.android.api.auth.data.Credentials
import im.vector.matrix.android.api.auth.data.HomeServerConnectionConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Cliente principal para conexión con Matrix
 * 
 * RESPONSABILIDADES:
 * 1. Autenticación (login/register)
 * 2. Gestión de sesión persistente
 * 3. Conexión con homeserver (matrix.org)
 * 4. Proveer acceso a rooms y mensajes
 * 
 * SEGURIDAD:
 * - Access tokens guardados en EncryptedSharedPreferences
 * - Sin logs de credenciales sensibles
 */
@Singleton
class MatrixClient @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Disconnected)
    val sessionState: Flow<SessionState> = _sessionState

    private var session: Session? = null
    
    // Homeserver configuration
    private val defaultHomeserver = "https://matrix-client.matrix.org"

    // Encrypted storage for credentials
    private val encryptedPrefs by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // ═══════════════════════════════════════════════════════════════════
    // REGISTRO DE NUEVA CUENTA
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Registra una nueva cuenta en Matrix
     * 
     * @param username Nombre de usuario (sin @ ni dominio)
     * @param password Contraseña
     * @return Result con el user ID completo (@usuario:matrix.org)
     */
    suspend fun register(username: String, password: String): Result<String> {
        return try {
            Timber.d("MatrixClient: Registering user: $username")
            
            val hsConfig = HomeServerConnectionConfig.Builder()
                .withHomeServerUri(defaultHomeserver)
                .withIdentityServerUri("https://vector.im")
                .build()

            val authService = Matrix.getAuthenticationService(hsConfig)
            
            // Nota: El registro puede requerir captcha en matrix.org
            val response = suspendCancellableCoroutine { continuation ->
                authService.register(
                    username = username,
                    password = password,
                    inhibitOtherDevices = false,
                    callback = object : RegisterCallback {
                        override fun onSuccess(credentials: Credentials) {
                            continuation.resume(credentials)
                        }
                        override fun onFailure(error: Throwable) {
                            continuation.resumeWithException(error)
                        }
                    }
                )
            }

            // Guardar credenciales
            saveCredentials(
                userId = response.userId,
                accessToken = response.accessToken,
                deviceId = response.deviceId ?: "unknown"
            )

            Timber.d("MatrixClient: Registration successful for: ${response.userId}")
            Result.success(response.userId)
        } catch (e: Exception) {
            Timber.e(e, "MatrixClient: Registration failed")
            Result.failure(e)
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    // LOGIN
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Inicia sesión con usuario y contraseña
     * 
     * @param username Nombre de usuario o user ID completo
     * @param password Contraseña
     * @return Result con el user ID completo
     */
    suspend fun login(username: String, password: String): Result<String> {
        return try {
            Timber.d("MatrixClient: Logging in user: $username")
            
            val hsConfig = HomeServerConnectionConfig.Builder()
                .withHomeServerUri(defaultHomeserver)
                .build()

            val authService = Matrix.getAuthenticationService(hsConfig)
            
            val response = suspendCancellableCoroutine { continuation ->
                authService.login(
                    username = username,
                    password = password,
                    callback = object : AuthCallback {
                        override fun onSuccess(credentials: Credentials) {
                            continuation.resume(credentials)
                        }
                        override fun onFailure(error: Throwable) {
                            continuation.resumeWithException(error)
                        }
                    }
                )
            }

            saveCredentials(
                userId = response.userId,
                accessToken = response.accessToken,
                deviceId = response.deviceId ?: "unknown"
            )

            Timber.d("MatrixClient: Login successful for: ${response.userId}")
            Result.success(response.userId)
        } catch (e: Exception) {
            Timber.e(e, "MatrixClient: Login failed")
            Result.failure(e)
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    // LOGIN CON TOKEN (SESIÓN PERSISTENTE)
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Restaura sesión con token guardado
     * 
     * Se llama al inicio de la app para evitar login manual
     */
    suspend fun loginWithToken(): Result<Unit> {
        return try {
            val userId = encryptedPrefs.getString(KEY_USER_ID, null)
            val accessToken = encryptedPrefs.getString(KEY_ACCESS_TOKEN, null)
            val deviceId = encryptedPrefs.getString(KEY_DEVICE_ID, null)

            if (userId == null || accessToken == null) {
                Timber.d("MatrixClient: No saved credentials found")
                return Result.failure(Exception("No credentials"))
            }

            Timber.d("MatrixClient: Restoring session for: $userId")

            val hsConfig = HomeServerConnectionConfig.Builder()
                .withHomeServerUri(defaultHomeserver)
                .build()

            val credentials = Credentials(
                userId = userId,
                accessToken = accessToken,
                deviceId = deviceId,
                homeServer = defaultHomeserver
            )

            session = Matrix.getInstance(context)
                .getSession(credentials, hsConfig, MatrixConfiguration())

            _sessionState.value = SessionState.Connected
            Timber.d("MatrixClient: Session restored successfully")
            
            // Iniciar NtfyService si hay sesión existente
            NtfyService.start(context)
            
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "MatrixClient: Session restore failed")
            Result.failure(e)
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    // GESTIÓN DE CREDENCIALES
    // ═══════════════════════════════════════════════════════════════════

    private fun saveCredentials(userId: String, accessToken: String, deviceId: String) {
        encryptedPrefs.edit().apply {
            putString(KEY_USER_ID, userId)
            putString(KEY_ACCESS_TOKEN, accessToken)
            putString(KEY_DEVICE_ID, deviceId)
            apply()
        }
        Timber.d("MatrixClient: Credentials saved securely")
    }

    // ═══════════════════════════════════════════════════════════════════
    // LOGOUT
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Cierra sesión y limpia credenciales
     */
    fun logout() {
        Timber.d("MatrixClient: Logging out")
        session?.logout()
        session = null
        encryptedPrefs.edit().clear().apply()
        _sessionState.value = SessionState.Disconnected
        
        // Detener NtfyService al cerrar sesión
        NtfyService.stop(context)
    }

    // ═══════════════════════════════════════════════════════════════════
    // ACCESO A SESIÓN
    // ═══════════════════════════════════════════════════════════════════

    fun getSession(): Session? = session
    
    fun isLogged(): Boolean = session != null
    
    fun getUserId(): String? = session?.myUserId

    // ═══════════════════════════════════════════════════════════════════
    // ESTADO DE SESIÓN
    // ═══════════════════════════════════════════════════════════════════

    sealed class SessionState {
        object Connected : SessionState()
        object Disconnected : SessionState()
        object Connecting : SessionState()
    }

    // ═══════════════════════════════════════════════════════════════════
    // CONSTANTES
    // ═══════════════════════════════════════════════════════════════════

    companion object {
        private const val PREFS_NAME = "cerdita_matrix_secure"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_DEVICE_ID = "device_id"
    }
}
