package com.cerdita.app.data.remote.matrix

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MatrixClient @Inject constructor(
    private val context: Context
) {
    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Disconnected)
    val sessionState: Flow<SessionState> = _sessionState

    private val _userId = MutableStateFlow<String?>(null)
    val userId: Flow<String?> = _userId

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

    init {
        checkExistingSession()
    }

    private fun checkExistingSession() {
        val userId = encryptedPrefs.getString("user_id", null)
        val accessToken = encryptedPrefs.getString("access_token", null)
        
        if (userId != null && accessToken != null) {
            _userId.value = userId
            _sessionState.value = SessionState.Connected
        }
    }

    suspend fun register(username: String, password: String): Result<String> {
        return try {
            // NOTA: Matrix SDK requiere implementación con OkHttp para llamadas HTTP
            // Esta es una implementación simplificada
            Result.failure(Exception("Registro debe hacerse en matrix.org web por ahora"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(username: String, password: String): Result<String> {
        return try {
            // Implementación con OkHttp para login en Matrix
            val url = "$defaultHomeserver/_matrix/client/v3/login"
            
            val json = """
                {
                    "type": "m.login.password",
                    "identifier": {
                        "type": "m.id.user",
                        "user": "$username"
                    },
                    "password": "$password",
                    "initial_device_display_name": "Cerdita App"
                }
            """.trimIndent()
            
            val client = okhttp3.OkHttpClient.Builder()
                .build()
            
            val mediaType = okhttp3.MediaType.parse("application/json")
            val body = okhttp3.RequestBody.create(mediaType, json)
            
            val request = okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .build()
            
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            
            if (response.isSuccessful && responseBody != null) {
                val org.json.JSONObject = org.json.JSONObject(responseBody)
                val accessToken = JSONObject.getString("access_token")
                val userId = JSONObject.getString("user_id")
                val deviceId = JSONObject.optString("device_id", "")
                
                saveCredentials(userId, accessToken, deviceId)
                
                _userId.value = userId
                _sessionState.value = SessionState.Connected
                
                Result.success(userId)
            } else {
                Result.failure(Exception("Login fallido: ${response.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginWithToken(): Result<Unit> {
        return try {
            val userId = encryptedPrefs.getString("user_id", null)
            val accessToken = encryptedPrefs.getString("access_token", null)

            if (userId == null || accessToken == null) {
                return Result.failure(Exception("No credentials"))
            }

            _userId.value = userId
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

    fun getAccessToken(): String? {
        return encryptedPrefs.getString("access_token", null)
    }

    fun getUserId(): String? {
        return encryptedPrefs.getString("user_id", null)
    }

    fun logout() {
        encryptedPrefs.edit().clear().apply()
        _userId.value = null
        _sessionState.value = SessionState.Disconnected
    }

    fun isLogged(): Boolean = encryptedPrefs.contains("access_token")

    sealed class SessionState {
        object Connected : SessionState()
        object Disconnected : SessionState()
        object Connecting : SessionState()
    }
}
