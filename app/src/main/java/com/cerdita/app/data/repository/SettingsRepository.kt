package com.cerdita.app.data.repository

import com.cerdita.app.data.local.database.dao.SettingsDao
import com.cerdita.app.data.local.database.entity.SettingsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val settingsDao: SettingsDao
) {
    fun getAllSettings(): Flow<List<SettingsEntity>> {
        return settingsDao.getAllSettings()
    }

    suspend fun getSettingByKey(key: String): String? {
        return settingsDao.getSettingValue(key)
    }

    suspend fun saveSetting(key: String, value: String) {
        settingsDao.insertSetting(
            SettingsEntity(
                settingKey = key,
                settingValue = value
            )
        )
    }

    suspend fun deleteSetting(key: String) {
        val setting = settingsDao.getSettingByKey(key)
        setting?.let { settingsDao.deleteSetting(it) }
    }
}
