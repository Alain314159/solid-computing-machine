package com.cerdita.app.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Receiver para manejar acciones de notificaciones
 * (responder mensaje, marcar como leído, etc.)
 */
class NotificationReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_REPLY = "com.cerdita.app.action.REPLY"
        const val ACTION_MARK_READ = "com.cerdita.app.action.MARK_READ"
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_MESSAGE_ID = "extra_message_id"
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_REPLY -> {
                // TODO: Implementar respuesta rápida desde notificación
                val replyText = intent.getStringExtra(EXTRA_MESSAGE)
                val messageId = intent.getStringExtra(EXTRA_MESSAGE_ID)
                // Enviar respuesta...
            }
            
            ACTION_MARK_READ -> {
                // TODO: Marcar mensaje como leído
                val messageId = intent.getStringExtra(EXTRA_MESSAGE_ID)
                // Actualizar estado...
            }
        }
    }
}
