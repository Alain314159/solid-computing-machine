package com.cerdita.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerdita.app.data.repository.SettingsRepository
import com.cerdita.app.presentation.ui.theme.ThemeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _theme = MutableStateFlow(ThemeType.CERDITA)
    val theme: StateFlow<ThemeType> = _theme

    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    private val _romanticEffectsEnabled = MutableStateFlow(true)
    val romanticEffectsEnabled: StateFlow<Boolean> = _romanticEffectsEnabled

    private val _effectIntensity = MutableStateFlow(EffectIntensity.NORMAL)
    val effectIntensity: StateFlow<EffectIntensity> = _effectIntensity

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            settingsRepository.getAllSettings().collect { settings ->
                settings.forEach { setting ->
                    when (setting.settingKey) {
                        "theme" -> _theme.value = ThemeType.valueOf(setting.settingValue)
                        "notifications_enabled" -> _notificationsEnabled.value = setting.settingValue.toBoolean()
                        "romantic_effects_enabled" -> _romanticEffectsEnabled.value = setting.settingValue.toBoolean()
                        "effect_intensity" -> _effectIntensity.value = EffectIntensity.valueOf(setting.settingValue)
                    }
                }
            }
        }
    }

    fun setTheme(themeType: ThemeType) {
        _theme.value = themeType
        saveSetting("theme", themeType.name)
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        _notificationsEnabled.value = enabled
        saveSetting("notifications_enabled", enabled.toString())
    }

    fun setRomanticEffectsEnabled(enabled: Boolean) {
        _romanticEffectsEnabled.value = enabled
        saveSetting("romantic_effects_enabled", enabled.toString())
    }

    fun setEffectIntensity(intensity: EffectIntensity) {
        _effectIntensity.value = intensity
        saveSetting("effect_intensity", intensity.name)
    }

    private fun saveSetting(key: String, value: String) {
        viewModelScope.launch {
            settingsRepository.saveSetting(key, value)
        }
    }
}

enum class EffectIntensity {
    SOFT,
    NORMAL,
    INTENSE
}
