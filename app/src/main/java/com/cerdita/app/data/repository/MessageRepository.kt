package com.cerdita.app.data.repository

import com.cerdita.app.data.local.database.dao.MessageDao
import com.cerdita.app.data.local.database.entity.MessageEntity
import com.cerdita.app.data.remote.matrix.MatrixClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val messageDao: MessageDao,
    private val matrixClient: MatrixClient
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

    suspend fun sendMessage(
        roomId: String,
        content: String,
        type: String = "text"
    ): Result<String> {
        return try {
            // TODO: Implementar envío real con Matrix SDK
            Result.success("message_id_placeholder")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun syncMessages(roomId: String): Result<Unit> {
        return try {
            // TODO: Implementar sync con Matrix SDK
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
