package com.cerdita.app.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Preferencias para configuración de Ntfy
 * 
 * Almacena:
 * - Topics (1, 2, 3)
 * - Contadores de mensajes
 * - Índice del topic activo
 * - Servidor seleccionado
 * - Tiempo de último reset
 */
@Singleton
class NtfyPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    /**
     * Guarda un topic
     */
    fun saveTopic(index: Int, topic: String) {
        prefs.edit().putString("${KEY_TOPIC_PREFIX}${index}", topic).apply()
    }

    /**
     * Obtiene un topic
     */
    fun getTopic(index: Int): String? {
        return prefs.getString("${KEY_TOPIC_PREFIX}${index}", null)
    }

    /**
     * Guarda el contador de mensajes de un topic
     */
    fun saveMessageCount(index: Int, count: Int) {
        prefs.edit().putInt("${KEY_MESSAGE_COUNT_PREFIX}${index}", count).apply()
    }

    /**
     * Obtiene el contador de mensajes de un topic
     */
    fun getMessageCount(index: Int): Int {
        return prefs.getInt("${KEY_MESSAGE_COUNT_PREFIX}${index}", 0)
    }

    /**
     * Incrementa el contador de mensajes
     */
    fun incrementMessageCount(index: Int) {
        val current = getMessageCount(index)
        saveMessageCount(index, current + 1)
    }

    /**
     * Guarda el índice del topic activo
     */
    fun setActiveTopicIndex(index: Int) {
        prefs.edit().putInt(KEY_ACTIVE_TOPIC_INDEX, index).apply()
    }

    /**
     * Obtiene el índice del topic activo
     */
    fun getActiveTopicIndex(): Int {
        return prefs.getInt(KEY_ACTIVE_TOPIC_INDEX, 0)
    }

    /**
     * Guarda el índice del servidor activo
     */
    fun setActiveServerIndex(index: Int) {
        prefs.edit().putInt(KEY_SERVER_INDEX, index).apply()
    }

    /**
     * Obtiene el índice del servidor activo
     */
    fun getActiveServerIndex(): Int {
        return prefs.getInt(KEY_SERVER_INDEX, 0)
    }

    /**
     * Guarda el tiempo del último reset
     */
    fun saveResetTime(time: Long) {
        prefs.edit().putLong(KEY_RESET_TIME, time).apply()
    }

    /**
     * Obtiene el tiempo del último reset
     */
    fun getResetTime(): Long {
        return prefs.getLong(KEY_RESET_TIME, 0L)
    }

    /**
     * Resetea todos los contadores
     */
    fun resetAllCounters() {
        val editor = prefs.edit()
        for (i in 0 until TOPIC_POOL_SIZE) {
            editor.putInt("${KEY_MESSAGE_COUNT_PREFIX}${i}", 0)
        }
        editor.putLong(KEY_RESET_TIME, System.currentTimeMillis())
        editor.apply()
    }

    /**
     * Limpia todas las preferencias
     */
    fun clear() {
        prefs.edit().clear().apply()
    }

    /**
     * Verifica si está inicializado
     */
    fun isInitialized(): Boolean {
        return getTopic(0) != null
    }

    companion object {
        private const val PREFS_NAME = "cerdita_ntfy_prefs"
        private const val KEY_TOPIC_PREFIX = "ntfy_topic_"
        private const val KEY_MESSAGE_COUNT_PREFIX = "ntfy_message_count_"
        private const val KEY_ACTIVE_TOPIC_INDEX = "active_topic_index"
        private const val KEY_SERVER_INDEX = "ntfy_server_index"
        private const val KEY_RESET_TIME = "ntfy_reset_time"
        private const val TOPIC_POOL_SIZE = 3
    }
}
