package com.cerdita.app.presentation.ui.screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerdita.app.service.NtfyManager
import com.cerdita.app.service.NtfyStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val ntfyManager: NtfyManager
) : ViewModel() {

    private val _ntfyStats = MutableStateFlow(NtfyStats("N/A", "N/A", 0, 500, 500, 24))
    val ntfyStats: StateFlow<NtfyStats> = _ntfyStats

    init {
        loadStats()
    }

    private fun loadStats() {
        ntfyManager.initialize()
        _ntfyStats.value = ntfyManager.getStats()
    }

    fun savePartnerTopic(topic: String) {
        viewModelScope.launch {
            ntfyManager.setTopicFromPartner(topic)
        }
    }

    fun copyMyTopic(): String {
        return ntfyManager.shareTopicWithPartner()
    }

    fun clearAllData() {
        viewModelScope.launch {
            ntfyManager.clearAllData()
            loadStats()
        }
    }
}
