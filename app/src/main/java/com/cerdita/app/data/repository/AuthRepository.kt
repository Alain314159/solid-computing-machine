package com.cerdita.app.data.repository

import com.cerdita.app.data.remote.matrix.MatrixClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val matrixClient: MatrixClient
) {
    val sessionState: Flow<MatrixClient.SessionState> = matrixClient.sessionState
    
    fun isLogged(): Boolean = matrixClient.isLogged()

    suspend fun register(username: String, password: String): Result<String> =
        matrixClient.register(username, password)

    suspend fun login(username: String, password: String): Result<String> =
        matrixClient.login(username, password)

    suspend fun loginWithToken(): Result<Unit> =
        matrixClient.loginWithToken()

    fun logout() = matrixClient.logout()
}
