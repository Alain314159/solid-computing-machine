package com.cerdita.app.data.repository

import com.cerdita.app.service.NtfyManager
import com.cerdita.app.service.NtfyResult
import com.cerdita.app.service.NtfyStats
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositorio para operaciones de notificaciones Ntfy
 * 
 * Responsabilidades:
 * - Gestionar los 3 topics (1 principal + 2 automáticos)
 * - Rotación automática cuando se alcanza el límite
 * - Envío de notificaciones push
 * - Obtener estadísticas del sistema
 * - Compartir topic con la pareja
 */
@Singleton
class NtfyRepository @Inject constructor(
    private val ntfyManager: NtfyManager
) {
    /**
     * Inicializa el sistema Ntfy
     */
    fun initialize() {
        ntfyManager.initialize()
    }

    /**
     * Obtiene el Topic 1 para compartir con la pareja
     */
    fun getTopic1ToShare(): String? {
        return ntfyManager.getTopic1ToShare()
    }

    /**
     * Configura el Topic 1 desde la pareja y genera automáticamente 2 y 3
     */
    fun setTopic1FromPartner(topic1: String) {
        ntfyManager.setTopic1FromPartner(topic1)
    }

    /**
     * Envía una notificación push
     */
    suspend fun sendMessage(
        title: String,
        message: String,
        priority: Int = 3,
        tags: List<String> = listOf("heart", "love")
    ): NtfyResult {
        val result = ntfyManager.sendMessage(title, message, priority, tags)
        return if (result.isSuccess) {
            NtfyResult.Success
        } else {
            NtfyResult.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
        }
    }

    /**
     * Obtiene estadísticas completas del sistema Ntfy
     */
    fun getStats(): NtfyStats {
        return ntfyManager.getStats()
    }

    /**
     * Obtiene todos los topics
     */
    fun getAllTopics(): List<String> {
        return ntfyManager.getAllTopics()
    }

    /**
     * Obtiene el topic activo actual
     */
    fun getActiveTopic(): String? {
        return ntfyManager.getActiveTopic()
    }

    /**
     * Verifica si el sistema está inicializado
     */
    fun isInitialized(): Boolean {
        return ntfyManager.isInitialized()
    }

    /**
     * Limpia todos los datos de Ntfy
     */
    fun clearAllData() {
        ntfyManager.clearAllData()
    }

    /**
     * Configura listener para rotación de topics
     */
    fun setOnTopicRotatedListener(listener: (Int) -> Unit) {
        ntfyManager.onTopicRotated = listener
    }

    /**
     * Configura listener para errores
     */
    fun setOnErrorListener(listener: (String) -> Unit) {
        ntfyManager.onError = listener
    }
}
