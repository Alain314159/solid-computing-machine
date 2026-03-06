package com.cerdita.app.domain.usecase

import com.cerdita.app.data.repository.AuthRepository
import javax.inject.Inject

/**
 * Caso de uso para registro de usuario en Matrix
 */
class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<String> {
        return authRepository.register(username, password)
    }
}
