package com.cerdita.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerdita.app.data.local.database.dao.EventDao
import com.cerdita.app.data.local.database.entity.EventEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val eventDao: EventDao
) : ViewModel() {

    val events: StateFlow<List<EventEntity>> = eventDao.getAllEvents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addEvent(
        title: String,
        description: String,
        date: Long,
        type: String,
        recurring: Boolean = false,
        reminderDays: Int = 1
    ) {
        viewModelScope.launch {
            val event = EventEntity(
                eventId = UUID.randomUUID().toString(),
                title = title,
                description = description,
                date = date,
                type = type,
                recurring = recurring,
                reminderDays = reminderDays
            )
            eventDao.insertEvent(event)
        }
    }

    fun deleteEvent(event: EventEntity) {
        viewModelScope.launch {
            eventDao.deleteEvent(event)
        }
    }

    fun updateEvent(event: EventEntity) {
        viewModelScope.launch {
            eventDao.updateEvent(event)
        }
    }
}
