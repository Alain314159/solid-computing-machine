package com.cerdita.app.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

/**
 * Receiver que se activa cuando el dispositivo arranca
 * 
 * RESPONSABILIDAD:
 * - Iniciar automáticamente NtfyService después del boot
 * - Asegurar que las notificaciones funcionen sin abrir la app
 */
@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var ntfyManager: NtfyManager

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON") {
            
            Timber.d("BootReceiver: Device booted, starting NtfyService")
            
            // Iniciar servicio Ntfy automáticamente
            NtfyService.start(context)
            
            Timber.d("BootReceiver: NtfyService started")
        }
    }
}
