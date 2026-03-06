package com.cerdita.app.domain.usecase

import com.cerdita.app.data.repository.MessageRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        roomId: String,
        content: String,
        type: String = "text"
    ): Result<String> {
        return messageRepository.sendMessage(roomId, content, type)
    }
}
