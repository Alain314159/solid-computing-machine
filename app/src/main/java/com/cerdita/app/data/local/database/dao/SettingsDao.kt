package com.cerdita.app.data.local.database.dao

import androidx.room.*
import com.cerdita.app.data.local.database.entity.SettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE settingKey = :key")
    suspend fun getSettingByKey(key: String): SettingsEntity?

    @Query("SELECT * FROM settings")
    fun getAllSettings(): Flow<List<SettingsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting: SettingsEntity)

    @Update
    suspend fun updateSetting(setting: SettingsEntity)

    @Delete
    suspend fun deleteSetting(setting: SettingsEntity)

    @Query("SELECT settingValue FROM settings WHERE settingKey = :key")
    suspend fun getSettingValue(key: String): String?
}
