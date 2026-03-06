package com.cerdita.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cerdita.app.data.local.database.dao.EventDao
import com.cerdita.app.data.local.database.dao.MessageDao
import com.cerdita.app.data.local.database.entity.EventEntity
import com.cerdita.app.data.local.database.entity.MessageEntity

@Database(entities = [MessageEntity::class, EventEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun eventDao(): EventDao
}
