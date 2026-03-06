package com.cerdita.app.data.remote.matrix

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import im.vector.matrix.android.api.Matrix
import im.vector.matrix.android.api.MatrixConfiguration
import im.vector.matrix.android.api.Session
import im.vector.matrix.android.api.auth.data.Credentials
import im.vector.matrix.android.api.auth.data.HomeServerConnectionConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Cliente oficial de Matrix SDK
 * 
 * NOTA: Este archivo requiere el SDK oficial de Matrix
 * Si el SDK no está disponible, usar MatrixClient.kt con OkHttp
 */
@Singleton
class MatrixSdkClient @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Disconnected)
    val sessionState: Flow<SessionState> = _sessionState

    private var session: Session? = null
    private val defaultHomeserver = "https://matrix-client.matrix.org"

    private val encryptedPrefs by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            context,
            "cerdita_secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    /**
     * Registro de nuevo usuario en matrix.org
     */
    suspend fun register(username: String, password: String): Result<String> {
        return try {
            val hsConfig = HomeServerConnectionConfig.Builder()
                .withHomeServerUri(defaultHomeserver)
                .build()

            val authService = Matrix.getAuthenticationService(hsConfig)
            val response = authService.register(
                username = username,
                password = password,
                inhibitOtherDevices = false
            )

            saveCredentials(
                userId = response.userId,
                accessToken = response.accessToken,
                deviceId = response.deviceId
            )

            _sessionState.value = SessionState.Connected
            Result.success(response.userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Login con usuario y contraseña
     */
    suspend fun login(username: String, password: String): Result<String> {
        return try {
            val hsConfig = HomeServerConnectionConfig.Builder()
                .withHomeServerUri(defaultHomeserver)
                .build()

            val authService = Matrix.getAuthenticationService(hsConfig)
            val response = authService.login(
                username = username,
                password = password
            )

            saveCredentials(
                userId = response.userId,
                accessToken = response.accessToken,
                deviceId = response.deviceId
            )

            _sessionState.value = SessionState.Connected
            Result.success(response.userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Login con token guardado
     */
    suspend fun loginWithToken(): Result<Unit> {
        return try {
            val userId = encryptedPrefs.getString("user_id", null)
            val accessToken = encryptedPrefs.getString("access_token", null)
            val deviceId = encryptedPrefs.getString("device_id", null)

            if (userId == null || accessToken == null) {
                return Result.failure(Exception("No credentials"))
            }

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
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun saveCredentials(userId: String, accessToken: String, deviceId: String) {
        encryptedPrefs.edit().apply {
            putString("user_id", userId)
            putString("access_token", accessToken)
            putString("device_id", deviceId)
            apply()
        }
    }

    fun getSession(): Session? = session
    
    fun isLogged(): Boolean = session != null

    fun logout() {
        session?.logout()
        session = null
        encryptedPrefs.edit().clear().apply()
        _sessionState.value = SessionState.Disconnected
    }

    sealed class SessionState {
        object Connected : SessionState()
        object Disconnected : SessionState()
        object Connecting : SessionState()
    }
}
