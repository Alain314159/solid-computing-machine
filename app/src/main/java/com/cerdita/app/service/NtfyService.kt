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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import okhttp3.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class NtfyService : Service() {

    @Inject
    lateinit var ntfyManager: NtfyManager

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var webSocket: WebSocket? = null
    
    private val client = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "cerdita_messages"
        private const val NOTIFICATION_ID = 1001
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        ntfyManager.initialize()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "START_NTFY" -> startListening()
            "STOP_NTFY" -> stopListening()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopListening()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    // ─────────────────────────────────────────────────────────────────────
    // 1 SOLA CONEXIÓN WEBSOCKET
    // ─────────────────────────────────────────────────────────────────────

    private fun startListening() {
        val topic = ntfyManager.getActiveTopic()
        val server = ntfyManager.getActiveServer()
        
        if (topic == null || server == null) return

        val request = Request.Builder()
            .url("$server/$topic/json")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                showConnectedNotification()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                handleNtfyMessage(text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                serviceScope.launch {
                    delay(NtfyConfig.WEBSOCKET_RECONNECT_DELAY_MS)
                    startListening()
                }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                serviceScope.launch {
                    delay(NtfyConfig.WEBSOCKET_RECONNECT_DELAY_MS)
                    startListening()
                }
            }
        })
    }

    private fun stopListening() {
        webSocket?.close(1000, "User disconnected")
        webSocket = null
    }

    private fun handleNtfyMessage(json: String) {
        serviceScope.launch {
            triggerMatrixSync()
            showNotification(
                title = "Cerdita 💕",
                message = "Nuevo mensaje de tu amor 🐷🤗🐨"
            )
        }
    }

    private suspend fun triggerMatrixSync() {
        withContext(Dispatchers.Main) {
            val intent = Intent(this@NtfyService, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            startActivity(intent)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Mensajes de Cerdita",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones de mensajes románticos"
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 100, 200)
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun showConnectedNotification() {
        val topic = ntfyManager.getActiveTopic() ?: "desconocido"
        
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Cerdita 💕")
            .setContentText("Escuchando: $topic")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    companion object {
        fun start(context: Context) {
            Intent(context, NtfyService::class.java).apply {
                action = "START_NTFY"
                context.startForegroundService(this)
            }
        }

        fun stop(context: Context) {
            Intent(context, NtfyService::class.java).apply {
                action = "STOP_NTFY"
                context.startService(this)
            }
        }
    }
}
