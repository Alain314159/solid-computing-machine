package com.cerdita.app.data.local.preferences

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            context,
            "cerdita_settings_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveTheme(themeName: String) {
        prefs.edit().putString("theme", themeName).apply()
    }

    fun getTheme(): String {
        return prefs.getString("theme", "CERDITA") ?: "CERDITA"
    }

    fun saveNotificationsEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("notifications_enabled", enabled).apply()
    }

    fun getNotificationsEnabled(): Boolean {
        return prefs.getBoolean("notifications_enabled", true)
    }

    fun saveRomanticEffectsEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("romantic_effects_enabled", enabled).apply()
    }

    fun getRomanticEffectsEnabled(): Boolean {
        return prefs.getBoolean("romantic_effects_enabled", true)
    }

    fun saveEffectIntensity(intensity: String) {
        prefs.edit().putString("effect_intensity", intensity).apply()
    }

    fun getEffectIntensity(): String {
        return prefs.getString("effect_intensity", "NORMAL") ?: "NORMAL"
    }

    fun saveNtfyTopic(topic: String) {
        prefs.edit().putString("ntfy_topic", topic).apply()
    }

    fun getNtfyTopic(): String? {
        return prefs.getString("ntfy_topic", null)
    }

    fun savePartnerTopic(topic: String) {
        prefs.edit().putString("partner_topic", topic).apply()
    }

    fun getPartnerTopic(): String? {
        return prefs.getString("partner_topic", null)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
