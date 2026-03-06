package com.cerdita.app.di

import com.cerdita.app.data.remote.matrix.MatrixClient
import com.cerdita.app.data.repository.*
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
        messageDao: com.cerdita.app.data.local.database.dao.MessageDao,
        matrixClient: MatrixClient
    ): MessageRepository {
        return MessageRepository(messageDao, matrixClient)
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
}
