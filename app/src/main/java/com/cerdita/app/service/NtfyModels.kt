package com.cerdita.app.service

/**
 * Modelo de topic Ntfy con estado y contador
 */
data class NtfyTopic(
    val index: Int,              // 0, 1, 2 (Topic 1, 2, 3)
    val name: String,            // cerdita-t1-abc12345
    val messageCount: Int = 0,   // Mensajes enviados en este topic
    val isActive: Boolean = false, // Si este topic está actualmente activo
    val lastResetTime: Long = System.currentTimeMillis()
)

/**
 * Estadísticas completas del sistema Ntfy
 */
data class NtfyStats(
    val activeTopic: String,           // Topic actualmente activo
    val activeTopicIndex: Int,         // Índice del topic activo (0, 1, 2)
    val messagesToday: Int,            // Mensajes enviados hoy
    val limit: Int,                    // Límite por topic
    val remaining: Int,                // Mensajes restantes antes de rotar
    val totalTopics: Int,              // Total de topics en el pool
    val currentServer: String,         // Servidor actual
    val topics: List<TopicStats>,      // Estadísticas por topic
    val hoursUntilReset: Long          // Horas hasta el reset diario
)

/**
 * Estadísticas de un topic individual
 */
data class TopicStats(
    val index: Int,        // Índice del topic (0, 1, 2)
    val name: String,      // Nombre del topic
    val messages: Int,     // Mensajes enviados
    val isActive: Boolean, // Si está activo
    val percentage: Int    // Porcentaje de uso (0-100)
)

/**
 * Resultado de operación Ntfy
 */
sealed class NtfyResult {
    object Success : NtfyResult()
    data class Error(val message: String, val exception: Exception? = null) : NtfyResult()
    object Rotating : NtfyResult() // Indica que se está rotando de topic
}
