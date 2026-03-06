package com.cerdita.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerdita.app.data.repository.NtfyRepository
import com.cerdita.app.service.NtfyStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val ntfyRepository: NtfyRepository
) : ViewModel() {

    private val _ntfyStats = MutableStateFlow<NtfyStats?>(null)
    val ntfyStats: StateFlow<NtfyStats?> = _ntfyStats

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            if (ntfyRepository.isInitialized()) {
                _ntfyStats.value = ntfyRepository.getStats()
            }
        }
    }

    fun getTopic1ToShare(): String? {
        return ntfyRepository.getTopic1ToShare()
    }

    fun setTopic1FromPartner(topic1: String) {
        ntfyRepository.setTopic1FromPartner(topic1)
        loadStats()
    }

    fun refreshStats() {
        loadStats()
    }

    fun isInitialized(): Boolean {
        return ntfyRepository.isInitialized()
    }
}
