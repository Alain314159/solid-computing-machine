package com.cerdita.app.domain.usecase

import com.cerdita.app.data.repository.MessageRepository
import javax.inject.Inject

/**
 * Caso de uso para enviar archivos multimedia (imágenes, videos, audio)
 */
class SendMediaUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    /**
     * Envía un archivo multimedia a una sala Matrix
     * 
     * @param roomId ID de la sala Matrix
     * @param mediaUri URI del archivo multimedia (content:// o file://)
     * @param mimeType Tipo MIME del archivo (image/jpeg, video/mp4, audio/mp3)
     * @param fileName Nombre original del archivo
     * @return Result con el ID del mensaje enviado o error
     */
    suspend operator fun invoke(
        roomId: String,
        mediaUri: String,
        mimeType: String,
        fileName: String
    ): Result<String> {
        return messageRepository.sendMedia(roomId, mediaUri, mimeType, fileName)
    }
}
