package com.cerdita.app.service

import timber.log.Timber

/**
 * Configuración centralizada para el sistema de notificaciones Ntfy
 * 
 * ARQUITECTURA DE 3 TOPICS:
 * - Topic 1: Principal (se comparte con la pareja)
 * - Topic 2: Backup 1 (generado automáticamente)
 * - Topic 3: Backup 2 (generado automáticamente)
 * 
 * Cuando un topic alcanza el límite (480 mensajes), 
 * la app rota automáticamente al siguiente.
 */
object NtfyConfig {
    
    // ═══════════════════════════════════════════════════════════════════
    // SERVIDORES NTFY DISPONIBLES
    // ═══════════════════════════════════════════════════════════════════
    
    val servers = listOf(
        NtfyServer(
            id = "server1",
            name = "ntfy.sh",
            wsUrl = "wss://ntfy.sh",
            httpUrl = "https://ntfy.sh",
            priority = 1,
            isDefault = true
        ),
        NtfyServer(
            id = "server2",
            name = "ntfy.services",
            wsUrl = "wss://ntfy.services",
            httpUrl = "https://ntfy.services",
            priority = 2,
            isDefault = false
        ),
        NtfyServer(
            id = "server3",
            name = "push.terminal.email",
            wsUrl = "wss://push.terminal.email",
            httpUrl = "https://push.terminal.email",
            priority = 3,
            isDefault = false
        )
    )
    
    // ═══════════════════════════════════════════════════════════════════
    // CONFIGURACIÓN DE TOPICS
    // ═══════════════════════════════════════════════════════════════════
    
    // Cantidad de topics en el pool
    const val TOPIC_POOL_SIZE = 3
    
    // Prefijo para todos los topics
    const val TOPIC_PREFIX = "cerdita"
    
    // Límite de mensajes por topic (480 de 500, margen de seguridad de 20)
    const val MESSAGES_PER_TOPIC_LIMIT = 480
    
    // Tiempo de reset automático (24 horas en milisegundos)
    const val RESET_TIME_MS = 24 * 60 * 60 * 1000L
    
    // ═══════════════════════════════════════════════════════════════════
    // CONFIGURACIÓN DE WEBSOCKET
    // ═══════════════════════════════════════════════════════════════════
    
    // Delay para reconexión WebSocket en caso de fallo
    const val WEBSOCKET_RECONNECT_DELAY_MS = 5000L
    
    // Timeout de lectura (0 = infinito para WebSocket persistente)
    const val WEBSOCKET_READ_TIMEOUT_MS = 0L
    
    // ═══════════════════════════════════════════════════════════════════
    // CONFIGURACIÓN DE NOTIFICACIONES
    // ═══════════════════════════════════════════════════════════════════
    
    // ID del canal de notificación
    const val NOTIFICATION_CHANNEL_ID = "cerdita_messages"
    
    // ID de la notificación foreground
    const val NOTIFICATION_FOREGROUND_ID = 1001
    
    // ID de la notificación de mensaje
    const val NOTIFICATION_MESSAGE_ID = 1002
    
    // ═══════════════════════════════════════════════════════════════════
    // CONFIGURACIÓN DE MENSAJES NTFY
    // ═══════════════════════════════════════════════════════════════════
    
    // Prioridad por defecto para notificaciones
    const val DEFAULT_PRIORITY = 3
    
    // Tags por defecto para notificaciones
    val DEFAULT_TAGS = listOf("heart", "love")
    
    // Título por defecto para notificaciones
    const val DEFAULT_TITLE = "Cerdita 💕"
    
    // Mensaje por defecto para notificaciones
    const val DEFAULT_MESSAGE = "Nuevo mensaje de tu amor 🐷🤗🐨"

    /**
     * Imprime configuración para debugging
     */
    fun printConfig() {
        Timber.d("NtfyConfig: ${servers.size} servers, $TOPIC_POOL_SIZE topics, limit: $MESSAGES_PER_TOPIC_LIMIT msgs/topic")
    }
}

/**
 * Modelo de servidor Ntfy
 */
data class NtfyServer(
    val id: String,
    val name: String,
    val wsUrl: String,
    val httpUrl: String,
    val priority: Int,
    val isDefault: Boolean
)
