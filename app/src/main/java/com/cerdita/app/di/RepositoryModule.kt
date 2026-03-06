package com.cerdita.app.di

import com.cerdita.app.data.local.database.dao.MessageDao
import com.cerdita.app.data.remote.matrix.MatrixClient
import com.cerdita.app.data.remote.matrix.MatrixRoomManager
import com.cerdita.app.data.repository.*
import com.cerdita.app.service.NtfyManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(matrixClient: MatrixClient): AuthRepository {
        return AuthRepository(matrixClient)
    }

    @Provides
    @Singleton
    fun provideMessageRepository(
        messageDao: MessageDao,
        matrixClient: MatrixClient,
        matrixRoomManager: MatrixRoomManager
    ): MessageRepository {
        return MessageRepository(messageDao, matrixClient, matrixRoomManager)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userDao: com.cerdita.app.data.local.database.dao.UserDao
    ): UserRepository {
        return UserRepository(userDao)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        settingsDao: com.cerdita.app.data.local.database.dao.SettingsDao
    ): SettingsRepository {
        return SettingsRepository(settingsDao)
    }

    @Provides
    @Singleton
    fun provideNtfyRepository(ntfyManager: NtfyManager): NtfyRepository {
        return NtfyRepository(ntfyManager)
    }

    @Provides
    @Singleton
    fun provideMatrixRoomManager(matrixClient: MatrixClient): MatrixRoomManager {
        return MatrixRoomManager(matrixClient)
    }
}
