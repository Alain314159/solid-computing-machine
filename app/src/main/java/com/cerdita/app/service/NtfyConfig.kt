package com.cerdita.app.service

object NtfyConfig {
    
    // Servidores ntfy disponibles (ordenados por prioridad)
    val servers = listOf(
        "https://ntfy.sh",           // Principal
        "https://ntfy.services",      // Backup 1
        "https://push.terminal.email" // Backup 2
    )
    
    // Límite de mensajes por topic (dejamos margen de seguridad)
    const val MESSAGES_PER_TOPIC_LIMIT = 450 // 500 - 50 de margen
    
    // Tiempo de reset (24 horas en milisegundos)
    const val RESET_TIME_MS = 24 * 60 * 60 * 1000L
    
    // WebSocket timeout
    const val WEBSOCKET_RECONNECT_DELAY_MS = 5000L
}
