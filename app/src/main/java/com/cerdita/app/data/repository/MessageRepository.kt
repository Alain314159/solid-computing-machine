package com.cerdita.app.data.repository

import com.cerdita.app.data.local.database.dao.MessageDao
import com.cerdita.app.data.local.database.entity.MessageEntity
import com.cerdita.app.data.remote.matrix.MatrixClient
import com.cerdita.app.data.remote.matrix.MatrixRoomManager
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val messageDao: MessageDao,
    private val matrixClient: MatrixClient,
    private val matrixRoomManager: MatrixRoomManager
) {
    fun getMessagesByRoom(roomId: String): Flow<List<MessageEntity>> {
        return messageDao.getMessagesByRoom(roomId)
    }

    suspend fun insertMessage(message: MessageEntity) {
        messageDao.insertMessage(message)
    }

    suspend fun updateMessage(message: MessageEntity) {
        messageDao.updateMessage(message)
    }

    suspend fun deleteMessage(message: MessageEntity) {
        messageDao.deleteMessage(message)
    }

    suspend fun getPendingMessages(): List<MessageEntity> {
        return messageDao.getPendingMessages()
    }

    /**
     * Envía mensaje de texto a través de Matrix
     */
    suspend fun sendMessage(
        roomId: String,
        content: String,
        type: String = "text"
    ): Result<String> {
        return try {
            // Establecer room actual
            matrixRoomManager.setCurrentRoom(roomId)
                .onFailure { return Result.failure(it) }

            // Enviar mensaje a través de Matrix SDK
            matrixRoomManager.sendTextMessage(content)
                .onFailure { return Result.failure(it) }

            // Generar ID único para el mensaje
            val messageId = UUID.randomUUID().toString()
            Timber.d("MessageRepository: Message sent with ID: $messageId")
            
            Result.success(messageId)
        } catch (e: Exception) {
            Timber.e(e, "MessageRepository: Error sending message")
            Result.failure(e)
        }
    }

    /**
     * Sincroniza mensajes desde Matrix server
     */
    suspend fun syncMessages(roomId: String): Result<Unit> {
        return try {
            // Establecer room actual
            matrixRoomManager.setCurrentRoom(roomId)
                .onFailure { return Result.failure(it) }

            // Obtener mensajes recientes desde Matrix
            val result = matrixRoomManager.getRecentMessages(50)
            
            result.onSuccess { members ->
                Timber.d("MessageRepository: Synced ${members.size} members from room")
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "MessageRepository: Error syncing messages")
            Result.failure(e)
        }
    }

    /**
     * Envía imagen a través de Matrix
     */
    suspend fun sendImage(
        roomId: String,
        imageUrl: String,
        width: Int,
        height: Int
    ): Result<String> {
        return try {
            matrixRoomManager.setCurrentRoom(roomId)
                .onFailure { return Result.failure(it) }

            // TODO: Implementar envío de imagen con Matrix SDK
            Timber.d("MessageRepository: Sending image: $imageUrl")
            Result.success(UUID.randomUUID().toString())
        } catch (e: Exception) {
            Timber.e(e, "MessageRepository: Error sending image")
            Result.failure(e)
        }
    }

    /**
     * Envía nota de voz a través de Matrix
     */
    suspend fun sendVoiceNote(
        roomId: String,
        audioUrl: String,
        duration: Long
    ): Result<String> {
        return try {
            matrixRoomManager.setCurrentRoom(roomId)
                .onFailure { return Result.failure(it) }

            // TODO: Implementar envío de audio con Matrix SDK
            Timber.d("MessageRepository: Sending voice note: $audioUrl")
            Result.success(UUID.randomUUID().toString())
        } catch (e: Exception) {
            Timber.e(e, "MessageRepository: Error sending voice note")
            Result.failure(e)
        }
    }
}
