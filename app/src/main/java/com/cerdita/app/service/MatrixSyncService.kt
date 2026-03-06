package com.cerdita.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Servicio para sincronización en segundo plano con Matrix
 * 
 * RESPONSABILIDADES:
 * 1. Sincronizar mensajes periódicamente
 * 2. Actualizar estados de mensajes (delivered, read)
 * 3. Ejecutarse en background con WorkManager
 */
@AndroidEntryPoint
class MatrixSyncService : Service() {

    @Inject
    lateinit var ntfyManager: NtfyManager

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    companion object {
        private const val SYNC_INTERVAL_MS = 5 * 60 * 1000L // 5 minutos
    }

    override fun onCreate() {
        super.onCreate()
        Timber.d("MatrixSyncService: onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("MatrixSyncService: onStartCommand")
        
        when (intent?.action) {
            ACTION_SYNC_NOW -> performSync()
            ACTION_STOP -> stopSelf()
        }
        
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        Timber.d("MatrixSyncService: onDestroy")
        super.onDestroy()
        serviceScope.cancel()
    }

    /**
     * Realiza la sincronización con Matrix
     */
    private fun performSync() {
        serviceScope.launch {
            try {
                Timber.d("MatrixSyncService: Performing sync...")
                
                // TODO: Implementar sync real con Matrix SDK
                // val matrixClient = matrixClientProvider.get()
                // matrixClient.syncMessages()
                
                Timber.d("MatrixSyncService: Sync completed")
            } catch (e: Exception) {
                Timber.e(e, "MatrixSyncService: Sync failed")
            }
        }
    }

    companion object {
        private const val ACTION_SYNC_NOW = "com.cerdita.app.SYNC_NOW"
        private const val ACTION_STOP = "com.cerdita.app.STOP_SYNC"

        /**
         * Inicia sincronización inmediata
         */
        fun syncNow(context: Context) {
            Intent(context, MatrixSyncService::class.java).apply {
                action = ACTION_SYNC_NOW
                context.startService(this)
            }
            Timber.d("MatrixSyncService: Sync requested")
        }

        /**
         * Detiene el servicio
         */
        fun stop(context: Context) {
            Intent(context, MatrixSyncService::class.java).apply {
                action = ACTION_STOP
                context.startService(this)
            }
            Timber.d("MatrixSyncService: Stop requested")
        }
    }
}
