package com.cerdita.app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.cerdita.app.MainActivity
import com.cerdita.app.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Servicio para sincronización en background con Matrix
 * 
 * Se ejecuta en segundo plano para:
 * - Escuchar mensajes entrantes
 * - Sincronizar estados
 * - Actualizar indicadores de lectura
 */
@AndroidEntryPoint
class MatrixSyncService : Service() {

    @Inject
    lateinit var ntfyManager: NtfyManager

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "matrix_sync"
        private const val NOTIFICATION_ID = 1003
        
        fun start(context: Context) {
            val intent = Intent(context, MatrixSyncService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
        
        fun stop(context: Context) {
            val intent = Intent(context, MatrixSyncService::class.java)
            context.stopService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "START_SYNC" -> startSyncing()
            "STOP_SYNC" -> stopSyncing()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSyncing()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startSyncing() {
        showNotification("Sincronizando mensajes...")
        
        serviceScope.launch {
            // TODO: Implementar sync real con Matrix SDK
            // Mientras tanto, mantener el servicio activo
            delay(60000) // Sync cada minuto
            showNotification("Escuchando mensajes...")
        }
    }

    private fun stopSyncing() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Sincronización Matrix",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Sincronización de mensajes en segundo plano"
                setShowBadge(false)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(content: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Cerdita 💕")
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }
}
