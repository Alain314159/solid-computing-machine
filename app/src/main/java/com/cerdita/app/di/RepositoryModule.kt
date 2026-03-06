package com.cerdita.app.di

import com.cerdita.app.data.local.database.dao.MessageDao
import com.cerdita.app.data.remote.matrix.MatrixClient
import com.cerdita.app.data.repository.AuthRepository
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
}
