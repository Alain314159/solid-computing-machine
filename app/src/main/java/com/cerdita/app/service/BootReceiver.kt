package com.cerdita.app.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Receiver para iniciar servicios automáticamente al encender el dispositivo
 * 
 * Esto asegura que las notificaciones Ntfy sigan funcionando
 * incluso después de reiniciar el teléfono.
 */
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON"
        ) {
            // Iniciar NtfyService automáticamente
            NtfyService.start(context)
            
            // Iniciar SyncService automáticamente
            SyncService.start(context)
        }
    }
}
