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
import okhttp3.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Servicio Foreground que mantiene la conexión WebSocket con Ntfy
 * 
 * RESPONSABILIDADES:
 * 1. Mantener 1 SOLA conexión WebSocket activa
 * 2. Escuchar el topic activo actual
 * 3. Reconectar automáticamente si la conexión cae
 * 4. Mostrar notificación cuando llega mensaje nuevo
 * 5. Trigger sync con Matrix al recibir notificación
 * 
 * IMPORTANTE:
 * - Solo 1 WebSocket abierto (NO múltiples)
 * - Escucha solo el topic ACTIVO
 * - Cuando hay rotación de topic, el servicio se reinicia
 */
@AndroidEntryPoint
class NtfyService : Service() {

    @Inject
    lateinit var ntfyManager: NtfyManager

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var webSocket: WebSocket? = null
    
    private val client = OkHttpClient.Builder()
        .readTimeout(NtfyConfig.WEBSOCKET_READ_TIMEOUT_MS, TimeUnit.MILLISECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "cerdita_messages"
        private const val NOTIFICATION_FOREGROUND_ID = 1001
        private const val NOTIFICATION_MESSAGE_ID = 1002
    }

    // ─────────────────────────────────────────────────────────────────────
    // CICLO DE VIDA DEL SERVICIO
    // ─────────────────────────────────────────────────────────────────────

    override fun onCreate() {
        super.onCreate()
        Timber.d("NtfyService: onCreate")
        createNotificationChannel()
        ntfyManager.initialize()
        
        // Configurar listener para rotación de topics
        ntfyManager.onTopicRotated = { newIndex ->
            Timber.d("NtfyService: Topic rotated to $newIndex, restarting connection")
            restartConnection()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("NtfyService: onStartCommand - action: ${intent?.action}")
        
        when (intent?.action) {
            ACTION_START -> startListening()
            ACTION_STOP -> stopListening()
        }
        
        return START_STICKY // Reiniciar servicio si el sistema lo mata
    }

    override fun onDestroy() {
        Timber.d("NtfyService: onDestroy")
        super.onDestroy()
        stopListening()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    // ─────────────────────────────────────────────────────────────────────
    // CONEXIÓN WEBSOCKET (1 SOLA CONEXIÓN)
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Inicia la conexión WebSocket con el topic activo
     */
    private fun startListening() {
        val topic = ntfyManager.getActiveTopic()
        val server = ntfyManager.getActiveServer()
        
        if (topic == null) {
            Timber.e("NtfyService: Cannot start - no topic configured")
            ntfyManager.onError?.invoke("No hay topic configurado")
            return
        }
        
        if (server == null) {
            Timber.e("NtfyService: Cannot start - no server available")
            ntfyManager.onError?.invoke("No hay servidor disponible")
            return
        }

        Timber.d("NtfyService: Connecting to topic: $topic, server: ${server.name}")

        // Construir URL WebSocket
        val url = "${server.wsUrl}/$topic/json"
        
        val request = Request.Builder()
            .url(url)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Timber.d("NtfyService: WebSocket opened successfully")
                showConnectedNotification()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Timber.d("NtfyService: Message received: $text")
                handleNtfyMessage(text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Timber.e(t, "NtfyService: WebSocket failure")
                scheduleReconnect()
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Timber.d("NtfyService: WebSocket closed - code: $code, reason: $reason")
                scheduleReconnect()
            }
        })
    }

    /**
     * Detiene la conexión WebSocket
     */
    private fun stopListening() {
        Timber.d("NtfyService: Stopping WebSocket connection")
        webSocket?.close(1000, "Service stopped")
        webSocket = null
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    /**
     * Reinicia la conexión (usado después de rotación de topic)
     */
    private fun restartConnection() {
        Timber.d("NtfyService: Restarting connection")
        stopListening()
        serviceScope.launch {
            delay(1000) // Pequeña pausa antes de reconectar
            startListening()
        }
    }

    /**
     * Programa reconexión automática en caso de fallo
     */
    private fun scheduleReconnect() {
        serviceScope.launch {
            Timber.d("NtfyService: Scheduling reconnect in ${NtfyConfig.WEBSOCKET_RECONNECT_DELAY_MS}ms")
            delay(NtfyConfig.WEBSOCKET_RECONNECT_DELAY_MS)
            startListening()
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // MANEJO DE MENSAJES RECIBIDOS
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Procesa mensaje recibido de Ntfy
     */
    private fun handleNtfyMessage(json: String) {
        serviceScope.launch {
            try {
                // Trigger sync con Matrix para obtener el mensaje real
                triggerMatrixSync()
                
                // Mostrar notificación al usuario
                showMessageNotification()
                
                Timber.d("NtfyService: Message handled successfully")
            } catch (e: Exception) {
                Timber.e(e, "NtfyService: Error handling message")
            }
        }
    }

    /**
     * Trigger para sincronizar con Matrix
     */
    private suspend fun triggerMatrixSync() {
        withContext(Dispatchers.Main) {
            try {
                // Opción 1: Abrir MainActivity (trae al frente)
                val intent = Intent(this@NtfyService, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    putExtra("TRIGGER_SYNC", true)
                }
                startActivity(intent)
            } catch (e: Exception) {
                Timber.e(e, "NtfyService: Error triggering sync")
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // NOTIFICACIONES
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Crea el canal de notificaciones (Android 8+)
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Canal para notificaciones de mensajes (ALTA prioridad)
            val messageChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Mensajes de Cerdita",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones de mensajes románticos"
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 100, 200)
                enableLights(true)
                lightColor = android.graphics.Color.parseColor("#FFB6C1")
                setShowBadge(true)
            }

            // Canal para notificación foreground (BAJA prioridad)
            val foregroundChannel = NotificationChannel(
                FOREGROUND_CHANNEL_ID,
                "Servicio en Segundo Plano",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Mantiene la conexión activa para notificaciones"
                setShowBadge(false)
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(messageChannel)
            notificationManager.createNotificationChannel(foregroundChannel)
            
            Timber.d("NtfyService: Notification channels created")
        }
    }

    /**
     * Muestra notificación cuando llega mensaje nuevo
     */
    private fun showMessageNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("OPEN_CHAT", true)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_heart)
            .setContentTitle(NtfyConfig.DEFAULT_TITLE)
            .setContentText(NtfyConfig.DEFAULT_MESSAGE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .setVibrate(longArrayOf(100, 200, 100, 200))
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_MESSAGE_ID, notification)
        
        Timber.d("NtfyService: Message notification shown")
    }

    /**
     * Muestra notificación foreground (siempre visible mientras el servicio corre)
     */
    private fun showConnectedNotification() {
        val stats = ntfyManager.getStats()
        
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, FOREGROUND_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Cerdita 💕")
            .setContentText("Topic ${stats.activeTopicIndex + 1}/${NtfyConfig.TOPIC_POOL_SIZE} • ${stats.remaining} restantes")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .build()

        startForeground(NOTIFICATION_FOREGROUND_ID, notification)
        
        Timber.d("NtfyService: Foreground notification shown")
    }

    // ─────────────────────────────────────────────────────────────────────
    // MÉTODOS PÚBLICOS PARA CONTROL EXTERNO
    // ─────────────────────────────────────────────────────────────────────

    companion object {
        private const val ACTION_START = "com.cerdita.app.START_NTFY"
        private const val ACTION_STOP = "com.cerdita.app.STOP_NTFY"
        private const val FOREGROUND_CHANNEL_ID = "cerdita_foreground"

        /**
         * Inicia el servicio Ntfy
         */
        fun start(context: Context) {
            Intent(context, NtfyService::class.java).apply {
                action = ACTION_START
                context.startForegroundService(this)
            }
            Timber.d("NtfyService: Start command sent")
        }

        /**
         * Detiene el servicio Ntfy
         */
        fun stop(context: Context) {
            Intent(context, NtfyService::class.java).apply {
                action = ACTION_STOP
                context.startService(this)
            }
            Timber.d("NtfyService: Stop command sent")
        }

        /**
         * Verifica si el servicio está corriendo
         */
        fun isRunning(context: Context): Boolean {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
            @Suppress("DEPRECATION")
            return manager.getRunningServices(Int.MAX_VALUE)
                .any { it.service.className == NtfyService::class.java.name }
        }
    }
}
