package com.cerdita.app.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val eventId: String,
    val title: String,
    val description: String,
    val date: Long, // Timestamp en milisegundos
    val type: String, // birthday, anniversary, event, unique
    val recurring: Boolean,
    val reminderDays: Int, // Días antes para recordar
    val createdAt: Long = System.currentTimeMillis()
)
