package com.cerdita.app.domain.usecase

import com.cerdita.app.data.repository.MessageRepository
import javax.inject.Inject

class SyncRoomUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(roomId: String): Result<Unit> {
        return messageRepository.syncMessages(roomId)
    }
}
