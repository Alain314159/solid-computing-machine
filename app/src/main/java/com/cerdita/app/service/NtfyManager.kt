package com.cerdita.app.service

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Gestor principal del sistema de notificaciones Ntfy
 * 
 * RESPONSABILIDADES:
 * 1. Generar y gestionar 3 topics (1 principal + 2 automáticos)
 * 2. Rotación automática cuando se alcanza el límite (480 mensajes)
 * 3. Envío de notificaciones push a través de WebSocket
 * 4. Sincronización automática entre dispositivos
 * 5. Reset diario de contadores cada 24 horas
 * 
 * ARQUITECTURA:
 * - Topic 1: Se genera al inicio y se comparte con la pareja
 * - Topics 2 y 3: Se generan automáticamente con el mismo suffix
 * - Ambos dispositivos terminan con los mismos 3 topics
 * - Rotación automática y sincronizada
 */
@Singleton
class NtfyManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // SharedPreferences para persistencia local
    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    // Cliente HTTP para enviar notificaciones
    private val client = OkHttpClient.Builder()
        .readTimeout(NtfyConfig.WEBSOCKET_READ_TIMEOUT_MS, java.util.concurrent.TimeUnit.MILLISECONDS)
        .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    // Listener para notificar cambios de topic
    var onTopicRotated: ((Int) -> Unit)? = null
    
    // Listener para notificar errores
    var onError: ((String) -> Unit)? = null

    // ═══════════════════════════════════════════════════════════════════
    // INICIALIZACIÓN
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Inicializa el sistema Ntfy
     */
    fun initialize() {
        Timber.d("NtfyManager: Initializing...")
        
        val existingTopic1 = getTopic(0)
        
        if (existingTopic1 == null) {
            // Primer inicio - generar los 3 topics
            Timber.d("NtfyManager: First launch - generating 3 new topics")
            generateAllTopics()
        } else {
            // Verificar si Topics 2 y 3 existen
            val existingTopic2 = getTopic(1)
            val existingTopic3 = getTopic(2)
            
            if (existingTopic2 == null || existingTopic3 == null) {
                // Extraer el suffix del Topic 1 y generar 2 y 3
                Timber.d("NtfyManager: Generating missing topics from Topic 1 suffix")
                generateMissingTopicsFromTopic1()
            }
        }
        
        // Asegurar que hay un índice de topic activo
        if (!prefs.contains(KEY_ACTIVE_TOPIC_INDEX)) {
            prefs.edit().putInt(KEY_ACTIVE_TOPIC_INDEX, 0).apply()
        }
        
        // Resetear contadores si pasaron 24 horas
        checkAndResetDaily()
        
        Timber.d("NtfyManager: Initialization complete")
    }

    /**
     * Genera los 3 topics iniciales con un suffix aleatorio común
     */
    private fun generateAllTopics() {
        val randomSuffix = generateRandomSuffix()
        Timber.d("NtfyManager: Generated random suffix: $randomSuffix")
        
        repeat(NtfyConfig.TOPIC_POOL_SIZE) { index ->
            val topicName = "${NtfyConfig.TOPIC_PREFIX}-t${index + 1}-$randomSuffix"
            prefs.edit()
                .putString("${KEY_TOPIC_PREFIX}${index}", topicName)
                .putInt("${KEY_MESSAGE_COUNT_PREFIX}${index}", 0)
                .apply()
            Timber.d("NtfyManager: Generated topic $index: $topicName")
        }
        
        prefs.edit()
            .putLong(KEY_RESET_TIME, System.currentTimeMillis())
            .putInt(KEY_SERVER_INDEX, 0)
            .apply()
    }

    /**
     * Genera Topics 2 y 3 basándose en el suffix del Topic 1
     */
    private fun generateMissingTopicsFromTopic1() {
        val topic1 = getTopic(0) ?: return
        
        // Extraer el suffix del Topic 1
        val suffix = extractSuffixFromTopic(topic1)
        Timber.d("NtfyManager: Extracted suffix from Topic 1: $suffix")
        
        if (suffix.isEmpty()) {
            Timber.e("NtfyManager: Could not extract suffix from Topic 1")
            return
        }
        
        // Generar Topics 2 y 3 con el mismo suffix
        for (index in 1 until NtfyConfig.TOPIC_POOL_SIZE) {
            val topicName = "${NtfyConfig.TOPIC_PREFIX}-t${index + 1}-$suffix"
            prefs.edit()
                .putString("${KEY_TOPIC_PREFIX}${index}", topicName)
                .putInt("${KEY_MESSAGE_COUNT_PREFIX}${index}", 0)
                .apply()
            Timber.d("NtfyManager: Generated topic $index: $topicName")
        }
    }

    /**
     * Extrae el suffix de un topic
     * Ej: "cerdita-t1-abc12345" → "abc12345"
     */
    private fun extractSuffixFromTopic(topic: String): String {
        val parts = topic.split("-")
        return if (parts.size >= 3) parts[2] else ""
    }

    /**
     * Genera un suffix aleatorio de 8 caracteres
     */
    private fun generateRandomSuffix(): String {
        val chars = "0123456789abcdef"
        return (1..8).map { chars.random() }.joinToString("")
    }

    // ═══════════════════════════════════════════════════════════════════
    // GESTIÓN DE TOPICS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Obtiene el nombre de un topic por índice
     */
    fun getTopic(index: Int): String? {
        return prefs.getString("${KEY_TOPIC_PREFIX}${index}", null)
    }

    /**
     * Obtiene el índice del topic actualmente activo
     */
    fun getActiveTopicIndex(): Int {
        return prefs.getInt(KEY_ACTIVE_TOPIC_INDEX, 0)
    }

    /**
     * Obtiene el nombre del topic actualmente activo
     */
    fun getActiveTopic(): String? {
        val index = getActiveTopicIndex()
        return getTopic(index)
    }

    /**
     * Obtiene el servidor actualmente activo
     */
    fun getActiveServer(): NtfyServer? {
        val serverIndex = prefs.getInt(KEY_SERVER_INDEX, 0)
        return NtfyConfig.servers.getOrElse(serverIndex) { NtfyConfig.servers.firstOrNull() }
    }

    /**
     * Obtiene todos los topics como lista
     */
    fun getAllTopics(): List<String> {
        return (0 until NtfyConfig.TOPIC_POOL_SIZE)
            .mapNotNull { index -> getTopic(index) }
    }

    // ═══════════════════════════════════════════════════════════════════
    // ROTACIÓN AUTOMÁTICA DE TOPICS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Verifica si se debe rotar al siguiente topic
     */
    private fun shouldRotate(): Boolean {
        val currentIndex = getActiveTopicIndex()
        val messageCount = prefs.getInt("${KEY_MESSAGE_COUNT_PREFIX}${currentIndex}", 0)
        val shouldRotate = messageCount >= NtfyConfig.MESSAGES_PER_TOPIC_LIMIT
        
        if (shouldRotate) {
            Timber.d("NtfyManager: Should rotate - count: $messageCount, limit: ${NtfyConfig.MESSAGES_PER_TOPIC_LIMIT}")
        }
        
        return shouldRotate
    }

    /**
     * Rota al siguiente topic automáticamente
     */
    private fun rotateToNextTopic() {
        val currentIndex = getActiveTopicIndex()
        val nextIndex = (currentIndex + 1) % NtfyConfig.TOPIC_POOL_SIZE
        
        Timber.d("NtfyManager: Rotating from topic $currentIndex to topic $nextIndex")
        
        prefs.edit()
            .putInt(KEY_ACTIVE_TOPIC_INDEX, nextIndex)
            .apply()
        
        // Notificar listeners
        onTopicRotated?.invoke(nextIndex)
        
        // Reiniciar el servicio WebSocket con el nuevo topic
        restartNtfyService()
    }

    /**
     * Reinicia el servicio Ntfy con el nuevo topic
     */
    private fun restartNtfyService() {
        Timber.d("NtfyManager: Restarting NtfyService")
        NtfyService.stop(context)
        NtfyService.start(context)
    }

    // ═══════════════════════════════════════════════════════════════════
    // ENVÍO DE MENSAJES
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Envía una notificación push a través de Ntfy
     */
    suspend fun sendMessage(
        title: String = NtfyConfig.DEFAULT_TITLE,
        message: String = NtfyConfig.DEFAULT_MESSAGE,
        priority: Int = NtfyConfig.DEFAULT_PRIORITY,
        tags: List<String> = NtfyConfig.DEFAULT_TAGS
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            // Verificar rotación ANTES de enviar
            if (shouldRotate()) {
                Timber.d("NtfyManager: Rotating topic before send")
                rotateToNextTopic()
            }

            val topic = getActiveTopic()
                ?: return@withContext Result.failure(Exception("No hay topic activo"))

            val server = getActiveServer()
                ?: return@withContext Result.failure(Exception("No hay servidor disponible"))

            Timber.d("NtfyManager: Sending message to topic: $topic, server: ${server.name}")

            // Construir payload JSON
            val jsonBody = buildNtfyJson(title, message, priority, tags)

            // Crear petición HTTP
            val request = Request.Builder()
                .url("${server.httpUrl}/")
                .post(RequestBody.create(
                    MediaType.parse("application/json"),
                    jsonBody
                ))
                .build()

            // Ejecutar petición
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                // Incrementar contador del topic activo
                incrementMessageCount()
                Timber.d("NtfyManager: Message sent successfully")
                Result.success(Unit)
            } else {
                // Manejar errores del servidor
                Timber.e("NtfyManager: Server error: ${response.code}")
                
                if (response.code >= 500) {
                    // Error del servidor - rotar a otro servidor
                    rotateServer()
                    // Reintentar con nuevo servidor
                    return@withContext sendMessage(title, message, priority, tags)
                }
                
                Result.failure(IOException("Error ntfy: ${response.code}"))
            }
        } catch (e: Exception) {
            Timber.e(e, "NtfyManager: Error sending message")
            onError?.invoke(e.message ?: "Error desconocido")
            Result.failure(e)
        }
    }

    /**
     * Construye el payload JSON para Ntfy
     */
    private fun buildNtfyJson(
        title: String,
        message: String,
        priority: Int,
        tags: List<String>
    ): String {
        val tagsJson = tags.joinToString("\", \"") { it }
        return """
            {
                "topic": "${getActiveTopic()}",
                "title": "$title",
                "message": "$message",
                "priority": $priority,
                "tags": ["$tagsJson"]
            }
        """.trimIndent()
    }

    /**
     * Incrementa el contador de mensajes del topic activo
     */
    private fun incrementMessageCount() {
        val currentIndex = getActiveTopicIndex()
        val currentCount = prefs.getInt("${KEY_MESSAGE_COUNT_PREFIX}${currentIndex}", 0)
        prefs.edit()
            .putInt("${KEY_MESSAGE_COUNT_PREFIX}${currentIndex}", currentCount + 1)
            .apply()
        Timber.d("NtfyManager: Message count for topic $currentIndex: ${currentCount + 1}")
    }

    // ═══════════════════════════════════════════════════════════════════
    // GESTIÓN DE SERVIDORES
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Rota al siguiente servidor en la lista
     */
    private fun rotateServer() {
        val currentIndex = prefs.getInt(KEY_SERVER_INDEX, 0)
        val newIndex = (currentIndex + 1) % NtfyConfig.servers.size
        prefs.edit()
            .putInt(KEY_SERVER_INDEX, newIndex)
            .apply()
        Timber.d("NtfyManager: Rotated server from $currentIndex to $newIndex")
    }

    // ═══════════════════════════════════════════════════════════════════
    // RESET DIARIO
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Verifica y resetea los contadores si pasaron 24 horas
     */
    private fun checkAndResetDaily() {
        val lastReset = prefs.getLong(KEY_RESET_TIME, 0)
        val now = System.currentTimeMillis()
        val hoursSinceReset = (now - lastReset) / (1000 * 60 * 60)

        if (hoursSinceReset >= 24) {
            Timber.d("NtfyManager: Resetting daily counters (${hoursSinceReset}h since last reset)")
            
            // Resetear TODOS los contadores de topics
            repeat(NtfyConfig.TOPIC_POOL_SIZE) { index ->
                prefs.edit()
                    .putInt("${KEY_MESSAGE_COUNT_PREFIX}${index}", 0)
                    .apply()
            }
            
            prefs.edit()
                .putLong(KEY_RESET_TIME, now)
                .apply()
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    // COMPARTIR TOPICS CON LA PAREJA
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Obtiene solo el Topic 1 para compartir con la pareja
     */
    fun getTopic1ToShare(): String? {
        return getTopic(0)
    }

    /**
     * Configura el Topic 1 desde la pareja y genera automáticamente 2 y 3
     */
    fun setTopic1FromPartner(topic1: String) {
        Timber.d("NtfyManager: Setting Topic 1 from partner: $topic1")
        
        // Guardar Topic 1
        prefs.edit()
            .putString("${KEY_TOPIC_PREFIX}0", topic1)
            .putInt("${KEY_MESSAGE_COUNT_PREFIX}0", 0)
            .apply()
        
        // Generar Topics 2 y 3 automáticamente
        generateMissingTopicsFromTopic1()
        
        // Resetear tiempo y activar Topic 1
        prefs.edit()
            .putInt(KEY_ACTIVE_TOPIC_INDEX, 0)
            .putLong(KEY_RESET_TIME, System.currentTimeMillis())
            .apply()
        
        Timber.d("NtfyManager: Topics 2 and 3 generated automatically")
    }

    // ═══════════════════════════════════════════════════════════════════
    // ESTADÍSTICAS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Obtiene estadísticas completas del sistema Ntfy
     */
    fun getStats(): NtfyStats {
        val currentIndex = getActiveTopicIndex()
        val messageCount = prefs.getInt("${KEY_MESSAGE_COUNT_PREFIX}${currentIndex}", 0)
        val lastReset = prefs.getLong(KEY_RESET_TIME, 0)
        val hoursSinceReset = (System.currentTimeMillis() - lastReset) / (1000 * 60 * 60)
        
        val topicsStats = (0 until NtfyConfig.TOPIC_POOL_SIZE).map { index ->
            val count = prefs.getInt("${KEY_MESSAGE_COUNT_PREFIX}${index}", 0)
            TopicStats(
                index = index,
                name = getTopic(index) ?: "N/A",
                messages = count,
                isActive = index == currentIndex,
                percentage = ((count.toFloat() / NtfyConfig.MESSAGES_PER_TOPIC_LIMIT) * 100).toInt()
            )
        }

        return NtfyStats(
            activeTopic = getActiveTopic() ?: "N/A",
            activeTopicIndex = currentIndex,
            messagesToday = messageCount,
            limit = NtfyConfig.MESSAGES_PER_TOPIC_LIMIT,
            remaining = NtfyConfig.MESSAGES_PER_TOPIC_LIMIT - messageCount,
            totalTopics = NtfyConfig.TOPIC_POOL_SIZE,
            currentServer = getActiveServer()?.name ?: "N/A",
            topics = topicsStats,
            hoursUntilReset = 24 - hoursSinceReset
        )
    }

    // ═══════════════════════════════════════════════════════════════════
    // UTILIDADES
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Limpia todos los datos de Ntfy
     */
    fun clearAllData() {
        Timber.d("NtfyManager: Clearing all data")
        prefs.edit().clear().apply()
    }

    /**
     * Verifica si el sistema está inicializado
     */
    fun isInitialized(): Boolean {
        return getTopic(0) != null
    }

    // ═══════════════════════════════════════════════════════════════════
    // CONSTANTES
    // ═══════════════════════════════════════════════════════════════════

    companion object {
        private const val PREFS_NAME = "cerdita_ntfy"
        private const val KEY_TOPIC_PREFIX = "ntfy_topic_"
        private const val KEY_MESSAGE_COUNT_PREFIX = "ntfy_message_count_"
        private const val KEY_ACTIVE_TOPIC_INDEX = "active_topic_index"
        private const val KEY_SERVER_INDEX = "ntfy_server_index"
        private const val KEY_RESET_TIME = "ntfy_reset_time"
    }
}
