package com.cerdita.app.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val settingKey: String,
    val settingValue: String,
    val updatedAt: Long = System.currentTimeMillis()
)
