package com.cerdita.app.service

/**
 * Modelo de topic Ntfy con estado y contador
 * 
 * @param index Índice del topic (0, 1, 2 para Topic 1, 2, 3)
 * @param name Nombre completo del topic (ej: cerdita-t1-abc12345)
 * @param messageCount Mensajes enviados en este topic
 * @param isActive Si este topic está actualmente activo
 * @param lastResetTime Última vez que se reseteó el contador
 */
data class NtfyTopic(
    val index: Int,
    val name: String,
    val messageCount: Int = 0,
    val isActive: Boolean = false,
    val lastResetTime: Long = System.currentTimeMillis()
)

/**
 * Estadísticas completas del sistema Ntfy
 * 
 * @param activeTopic Topic actualmente activo
 * @param activeTopicIndex Índice del topic activo (0, 1, 2)
 * @param messagesToday Mensajes enviados hoy
 * @param limit Límite por topic (480 mensajes)
 * @param remaining Mensajes restantes antes de rotar
 * @param totalTopics Total de topics en el pool (3)
 * @param currentServer Servidor actual
 * @param topics Estadísticas por topic
 * @param hoursUntilReset Horas hasta el reset diario
 */
data class NtfyStats(
    val activeTopic: String,
    val activeTopicIndex: Int,
    val messagesToday: Int,
    val limit: Int,
    val remaining: Int,
    val totalTopics: Int,
    val currentServer: String,
    val topics: List<TopicStats>,
    val hoursUntilReset: Long
)

/**
 * Estadísticas de un topic individual
 * 
 * @param index Índice del topic (0, 1, 2)
 * @param name Nombre del topic
 * @param messages Mensajes enviados
 * @param isActive Si está activo
 * @param percentage Porcentaje de uso (0-100)
 */
data class TopicStats(
    val index: Int,
    val name: String,
    val messages: Int,
    val isActive: Boolean,
    val percentage: Int
)

/**
 * Resultado de operación Ntfy
 */
sealed class NtfyResult {
    object Success : NtfyResult()
    data class Error(val message: String, val exception: Exception? = null) : NtfyResult()
    object Rotating : NtfyResult()
}

/**
 * Estado de la conexión WebSocket
 */
enum class ConnectionState {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    RECONNECTING,
    ERROR
}
