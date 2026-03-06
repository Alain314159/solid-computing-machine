package com.cerdita.app.di

import com.cerdita.app.data.repository.AuthRepository
import com.cerdita.app.data.repository.MessageRepository
import com.cerdita.app.data.repository.NtfyRepository
import com.cerdita.app.domain.usecase.GetEventsUseCase
import com.cerdita.app.domain.usecase.LoginUseCase
import com.cerdita.app.domain.usecase.ReceiveMessagesUseCase
import com.cerdita.app.domain.usecase.RegisterUseCase
import com.cerdita.app.domain.usecase.RotateNtfyTopicUseCase
import com.cerdita.app.domain.usecase.SendMediaUseCase
import com.cerdita.app.domain.usecase.SendMessageUseCase
import com.cerdita.app.domain.usecase.SendVoiceNoteUseCase
import com.cerdita.app.domain.usecase.SyncRoomUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSendMessageUseCase(messageRepository: MessageRepository): SendMessageUseCase {
        return SendMessageUseCase(messageRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideReceiveMessagesUseCase(messageRepository: MessageRepository): ReceiveMessagesUseCase {
        return ReceiveMessagesUseCase(messageRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSyncRoomUseCase(messageRepository: MessageRepository): SyncRoomUseCase {
        return SyncRoomUseCase(messageRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSendMediaUseCase(messageRepository: MessageRepository): SendMediaUseCase {
        return SendMediaUseCase(messageRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSendVoiceNoteUseCase(messageRepository: MessageRepository): SendVoiceNoteUseCase {
        return SendVoiceNoteUseCase(messageRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetEventsUseCase(messageRepository: MessageRepository): GetEventsUseCase {
        return GetEventsUseCase(messageRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideRotateNtfyTopicUseCase(ntfyRepository: NtfyRepository): RotateNtfyTopicUseCase {
        return RotateNtfyTopicUseCase(ntfyRepository)
    }
}
