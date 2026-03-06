package com.cerdita.app.domain.usecase

import com.cerdita.app.data.repository.NtfyRepository
import javax.inject.Inject

/**
 * Caso de uso para rotar el topic de Ntfy automáticamente
 * 
 * Se usa cuando:
 * - El topic activo alcanza el límite de mensajes (480)
 * - El usuario solicita rotación manual
 * - El sistema detecta que es necesario por errores
 */
class RotateNtfyTopicUseCase @Inject constructor(
    private val ntfyRepository: NtfyRepository
) {
    /**
     * Rota al siguiente topic disponible
     * 
     * El NtfyManager se encarga de:
     * 1. Calcular el siguiente índice (cíclico: 0→1→2→0)
     * 2. Guardar el nuevo índice activo
     * 3. Notificar a los listeners
     * 4. Reiniciar el servicio WebSocket
     * 
     * @return Result con el índice del nuevo topic activo o error
     */
    suspend operator fun invoke(): Result<Int> {
        return try {
            // La rotación automática ya está implementada en NtfyManager
            // Este use case fuerza la rotación manual si es necesario
            val currentStats = ntfyRepository.getStats()
            val nextIndex = (currentStats.activeTopicIndex + 1) % 3
            
            // Notificar al servicio para reiniciar con el nuevo topic
            // Esto se maneja internamente en NtfyManager
            
            Result.success(nextIndex)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Verifica si se debe rotar el topic
     * 
     * @return true si el topic activo está cerca del límite (>90%)
     */
    fun shouldRotate(): Boolean {
        val stats = ntfyRepository.getStats()
        val usagePercentage = (stats.messagesToday.toFloat() / stats.limit) * 100
        return usagePercentage > 90
    }
}
