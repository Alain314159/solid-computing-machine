package com.cerdita.app.domain.usecase

import com.cerdita.app.data.repository.MessageRepository
import javax.inject.Inject

/**
 * Caso de uso para enviar notas de voz
 */
class SendVoiceNoteUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    /**
     * Envía una nota de voz a una sala Matrix
     * 
     * @param roomId ID de la sala Matrix
     * @param audioUri URI del archivo de audio (content:// o file://)
     * @param durationMs Duración del audio en milisegundos
     * @param fileName Nombre del archivo de audio
     * @return Result con el ID del mensaje enviado o error
     */
    suspend operator fun invoke(
        roomId: String,
        audioUri: String,
        durationMs: Long,
        fileName: String
    ): Result<String> {
        return messageRepository.sendVoiceNote(roomId, audioUri, durationMs, fileName)
    }
}
