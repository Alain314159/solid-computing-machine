package com.cerdita.app.service

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NtfyManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "cerdita_ntfy",
        Context.MODE_PRIVATE
    )

    private val client = OkHttpClient()

    // ─────────────────────────────────────────────────────────────────────
    // INICIALIZACIÓN
    // ─────────────────────────────────────────────────────────────────────

    fun initialize() {
        if (getActiveTopic() == null) {
            val newTopic = generateTopic()
            prefs.edit()
                .putString("ntfy_topic", newTopic)
                .putInt("ntfy_message_count", 0)
                .putLong("ntfy_reset_time", System.currentTimeMillis())
                .putInt("ntfy_server_index", 0)
                .apply()
        }
    }

    fun getActiveTopic(): String? {
        return prefs.getString("ntfy_topic", null)
    }

    fun getActiveServer(): String? {
        val serverIndex = prefs.getInt("ntfy_server_index", 0)
        return NtfyConfig.servers.getOrElse(serverIndex) { NtfyConfig.servers.first() }
    }

    // ─────────────────────────────────────────────────────────────────────
    // ENVIAR MENSAJE
    // ─────────────────────────────────────────────────────────────────────

    suspend fun sendMessage(
        title: String,
        message: String,
        priority: Int = 3,
        tags: List<String> = listOf("heart")
    ): Result<Unit> = withContext(Dispatchers.IO) {
        val topic = getActiveTopic() 
            ?: return@withContext Result.failure(Exception("No hay topic configurado"))

        val server = getActiveServer() 
            ?: return@withContext Result.failure(Exception("No hay servidor disponible"))

        checkAndResetCounter()

        val messageCount = prefs.getInt("ntfy_message_count", 0)
        if (messageCount >= NtfyConfig.MESSAGES_PER_TOPIC_LIMIT) {
            rotateTopic()
        }

        try {
            val jsonBody = """
                {
                    "topic": "$topic",
                    "title": "$title",
                    "message": "$message",
                    "priority": $priority,
                    "tags": ["${tags.joinToString("\",\"")}"]
                }
            """.trimIndent()

            val mediaType = MediaType.parse("application/json")
            val request = Request.Builder()
                .url("$server/")
                .post(RequestBody.create(mediaType, jsonBody))
                .build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                prefs.edit()
                    .putInt("ntfy_message_count", messageCount + 1)
                    .apply()
                Result.success(Unit)
            } else {
                if (response.code >= 500) {
                    rotateServer()
                    return@withContext sendMessage(title, message, priority, tags)
                }
                Result.failure(IOException("Error ntfy: ${response.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // ROTACIÓN
    // ─────────────────────────────────────────────────────────────────────

    private fun rotateTopic() {
        val newTopic = generateTopic()
        prefs.edit()
            .putString("ntfy_topic", newTopic)
            .putInt("ntfy_message_count", 0)
            .putLong("ntfy_reset_time", System.currentTimeMillis())
            .apply()
    }

    private fun rotateServer() {
        val currentIndex = prefs.getInt("ntfy_server_index", 0)
        val newIndex = (currentIndex + 1) % NtfyConfig.servers.size
        prefs.edit()
            .putInt("ntfy_server_index", newIndex)
            .apply()
    }

    private fun checkAndResetCounter() {
        val lastReset = prefs.getLong("ntfy_reset_time", 0)
        val now = System.currentTimeMillis()
        val hoursSinceReset = (now - lastReset) / (1000 * 60 * 60)

        if (hoursSinceReset >= 24) {
            prefs.edit()
                .putInt("ntfy_message_count", 0)
                .putLong("ntfy_reset_time", now)
                .apply()
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // UTILIDADES
    // ─────────────────────────────────────────────────────────────────────

    private fun generateTopic(): String {
        val random = (1..8).map { "0123456789abcdef".random() }.joinToString("")
        return "cerdita-$random"
    }

    fun getStats(): NtfyStats {
        val messageCount = prefs.getInt("ntfy_message_count", 0)
        val lastReset = prefs.getLong("ntfy_reset_time", 0)
        val hoursSinceReset = (System.currentTimeMillis() - lastReset) / (1000 * 60 * 60)

        return NtfyStats(
            topic = getActiveTopic() ?: "N/A",
            server = getActiveServer() ?: "N/A",
            messagesToday = messageCount,
            limit = 500,
            remaining = 500 - messageCount,
            hoursUntilReset = 24 - hoursSinceReset
        )
    }

    fun shareTopicWithPartner(): String {
        return getActiveTopic() ?: run {
            initialize()
            getActiveTopic() ?: ""
        }
    }

    fun setTopicFromPartner(topic: String) {
        prefs.edit()
            .putString("ntfy_topic", topic)
            .putInt("ntfy_message_count", 0)
            .putLong("ntfy_reset_time", System.currentTimeMillis())
            .apply()
    }

    fun clearAllData() {
        prefs.edit().clear().apply()
    }
}

data class NtfyStats(
    val topic: String,
    val server: String,
    val messagesToday: Int,
    val limit: Int,
    val remaining: Int,
    val hoursUntilReset: Long
)
