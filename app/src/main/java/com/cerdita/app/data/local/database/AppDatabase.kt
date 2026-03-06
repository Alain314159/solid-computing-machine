package com.cerdita.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cerdita.app.data.local.database.dao.*
import com.cerdita.app.data.local.database.entity.*

@Database(
    entities = [
        MessageEntity::class,
        EventEntity::class,
        UserEntity::class,
        SettingsEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun eventDao(): EventDao
    abstract fun userDao(): UserDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        const val DATABASE_NAME = "cerdita_database"
    }
}
