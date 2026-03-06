package com.cerdita.app.service

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Gestor principal del sistema de notificaciones Ntfy con 3 topics
 * 
 * RESPONSABILIDADES:
 * 1. Generar y gestionar 3 topics (1 principal + 2 automáticos)
 * 2. Rotación automática cuando se alcanza el límite (480 mensajes)
 * 3. Envío de notificaciones push a través de HTTP POST
 * 4. Sincronización automática entre dispositivos
 * 5. Reset diario de contadores cada 24 horas
 */
@Singleton
class NtfyManager @Inject constructor(
    private val context: Context
) {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(0, TimeUnit.MILLISECONDS) // Infinite for long polling
        .build()

    // Listeners
    var onTopicRotated: ((Int) -> Unit)? = null
    var onError: ((String) -> Unit)? = null

    /**
     * Inicializa el sistema Ntfy
     */
    fun initialize() {
        Timber.d("NtfyManager: Initializing...")
        
        val existingTopic1 = getTopic(0)
        
        if (existingTopic1 == null) {
            Timber.d("NtfyManager: First launch - generating 3 new topics")
            generateAllTopics()
        } else {
            val existingTopic2 = getTopic(1)
            val existingTopic3 = getTopic(2)
            
            if (existingTopic2 == null || existingTopic3 == null) {
                Timber.d("NtfyManager: Generating missing topics from Topic 1")
                generateMissingTopicsFromTopic1()
            }
        }
        
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
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                .putString("${KEY_TOPIC_PREFIX}${index}", topicName)
                .putInt("${KEY_MESSAGE_COUNT_PREFIX}${index}", 0)
                .apply()
            Timber.d("NtfyManager: Generated topic $index: $topicName")
        }
        
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .putLong(KEY_RESET_TIME, System.currentTimeMillis())
            .putInt(KEY_SERVER_INDEX, 0)
            .putInt(KEY_ACTIVE_TOPIC_INDEX, 0)
            .apply()
    }

    /**
     * Genera Topics 2 y 3 basándose en el suffix del Topic 1
     */
    private fun generateMissingTopicsFromTopic1() {
        val topic1 = getTopic(0) ?: return
        
        val suffix = extractSuffixFromTopic(topic1)
        Timber.d("NtfyManager: Extracted suffix from Topic 1: $suffix")
        
        if (suffix.isEmpty()) {
            Timber.e("NtfyManager: Could not extract suffix from Topic 1")
            return
        }
        
        for (index in 1 until NtfyConfig.TOPIC_POOL_SIZE) {
            val topicName = "${NtfyConfig.TOPIC_PREFIX}-t${index + 1}-$suffix"
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                .putString("${KEY_TOPIC_PREFIX}${index}", topicName)
                .putInt("${KEY_MESSAGE_COUNT_PREFIX}${index}", 0)
                .apply()
            Timber.d("NtfyManager: Generated topic $index: $topicName")
        }
    }

    private fun extractSuffixFromTopic(topic: String): String {
        val parts = topic.split("-")
        return if (parts.size >= 3) parts[2] else ""
    }

    private fun generateRandomSuffix(): String {
        val chars = "0123456789abcdef"
        return (1..8).map { chars.random() }.joinToString("")
    }

    /**
     * Obtiene el nombre de un topic por índice
     */
    fun getTopic(index: Int): String? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString("${KEY_TOPIC_PREFIX}${index}", null)
    }

    /**
     * Obtiene el índice del topic actualmente activo
     */
    fun getActiveTopicIndex(): Int {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_ACTIVE_TOPIC_INDEX, 0)
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
        val serverIndex = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_SERVER_INDEX, 0)
        return NtfyConfig.servers.getOrElse(serverIndex) { NtfyConfig.servers.firstOrNull() }
    }

    /**
     * Obtiene todos los topics como lista
     */
    fun getAllTopics(): List<String> {
        return (0 until NtfyConfig.TOPIC_POOL_SIZE)
            .mapNotNull { index -> getTopic(index) }
    }

    /**
     * Verifica si se debe rotar al siguiente topic
     */
    private fun shouldRotate(): Boolean {
        val currentIndex = getActiveTopicIndex()
        val messageCount = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt("${KEY_MESSAGE_COUNT_PREFIX}${currentIndex}", 0)
        return messageCount >= NtfyConfig.MESSAGES_PER_TOPIC_LIMIT
    }

    /**
     * Rota al siguiente topic automáticamente
     */
    private fun rotateToNextTopic() {
        val currentIndex = getActiveTopicIndex()
        val nextIndex = (currentIndex + 1) % NtfyConfig.TOPIC_POOL_SIZE
        
        Timber.d("NtfyManager: Rotating from topic $currentIndex to topic $nextIndex")
        
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .putInt(KEY_ACTIVE_TOPIC_INDEX, nextIndex)
            .apply()
        
        onTopicRotated?.invoke(nextIndex)
    }

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
            if (shouldRotate()) {
                Timber.d("NtfyManager: Rotating topic before send")
                rotateToNextTopic()
            }

            val topic = getActiveTopic()
                ?: return@withContext Result.failure(Exception("No hay topic activo"))

            val server = getActiveServer()
                ?: return@withContext Result.failure(Exception("No hay servidor disponible"))

            Timber.d("NtfyManager: Sending message to topic: $topic, server: ${server.name}")

            val jsonBody = buildNtfyJson(title, message, priority, tags)

            val request = Request.Builder()
                .url("${server.httpUrl}/")
                .post(RequestBody.create(
                    MediaType.parse("application/json"),
                    jsonBody
                ))
                .build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                incrementMessageCount()
                Timber.d("NtfyManager: Message sent successfully")
                Result.success(Unit)
            } else {
                Timber.e("NtfyManager: Server error: ${response.code}")
                
                if (response.code >= 500) {
                    rotateServer()
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

    private fun incrementMessageCount() {
        val currentIndex = getActiveTopicIndex()
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentCount = prefs.getInt("${KEY_MESSAGE_COUNT_PREFIX}${currentIndex}", 0)
        prefs.edit()
            .putInt("${KEY_MESSAGE_COUNT_PREFIX}${currentIndex}", currentCount + 1)
            .apply()
        Timber.d("NtfyManager: Message count for topic $currentIndex: ${currentCount + 1}")
    }

    private fun rotateServer() {
        val currentIndex = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_SERVER_INDEX, 0)
        val newIndex = (currentIndex + 1) % NtfyConfig.servers.size
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .putInt(KEY_SERVER_INDEX, newIndex)
            .apply()
        Timber.d("NtfyManager: Rotated server from $currentIndex to $newIndex")
    }

    private fun checkAndResetDaily() {
        val lastReset = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getLong(KEY_RESET_TIME, 0)
        val now = System.currentTimeMillis()
        val hoursSinceReset = (now - lastReset) / (1000 * 60 * 60)

        if (hoursSinceReset >= 24) {
            Timber.d("NtfyManager: Resetting daily counters (${hoursSinceReset}h since last reset)")
            
            repeat(NtfyConfig.TOPIC_POOL_SIZE) { index ->
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                    .putInt("${KEY_MESSAGE_COUNT_PREFIX}${index}", 0)
                    .apply()
            }
            
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                .putLong(KEY_RESET_TIME, now)
                .apply()
        }
    }

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
        
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .putString("${KEY_TOPIC_PREFIX}0", topic1)
            .putInt("${KEY_MESSAGE_COUNT_PREFIX}0", 0)
            .apply()
        
        generateMissingTopicsFromTopic1()
        
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .putInt(KEY_ACTIVE_TOPIC_INDEX, 0)
            .putLong(KEY_RESET_TIME, System.currentTimeMillis())
            .apply()
        
        Timber.d("NtfyManager: Topics 2 and 3 generated automatically")
    }

    /**
     * Obtiene estadísticas completas del sistema Ntfy
     */
    fun getStats(): NtfyStats {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
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

    fun clearAllData() {
        Timber.d("NtfyManager: Clearing all data")
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().clear().apply()
    }

    fun isInitialized(): Boolean {
        return getTopic(0) != null
    }

    companion object {
        private const val PREFS_NAME = "cerdita_ntfy"
        private const val KEY_TOPIC_PREFIX = "ntfy_topic_"
        private const val KEY_MESSAGE_COUNT_PREFIX = "ntfy_message_count_"
        private const val KEY_ACTIVE_TOPIC_INDEX = "active_topic_index"
        private const val KEY_SERVER_INDEX = "ntfy_server_index"
        private const val KEY_RESET_TIME = "ntfy_reset_time"
    }
}
