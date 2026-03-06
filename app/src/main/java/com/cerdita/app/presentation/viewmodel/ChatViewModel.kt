package com.cerdita.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerdita.app.data.local.database.dao.MessageDao
import com.cerdita.app.data.local.database.entity.MessageEntity
import com.cerdita.app.data.remote.matrix.MatrixClient
import com.cerdita.app.data.repository.MessageRepository
import com.cerdita.app.service.NtfyManager
import com.cerdita.app.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val matrixClient: MatrixClient,
    private val messageRepository: MessageRepository,
    private val ntfyManager: NtfyManager
) : ViewModel() {

    private val _messages = MutableStateFlow<List<MessageEntity>>(emptyList())
    val messages: StateFlow<List<MessageEntity>> = _messages

    private val _roomId = MutableStateFlow<String>("room_1")
    val roomId: StateFlow<String> = _roomId

    private val _typingIndicator = MutableStateFlow(false)
    val typingIndicator: StateFlow<Boolean> = _typingIndicator

    private val _partnerTyping = MutableStateFlow(false)
    val partnerTyping: StateFlow<Boolean> = _partnerTyping

    init {
        loadMessages()
    }

    private fun loadMessages() {
        viewModelScope.launch {
            messageRepository.getMessagesByRoom(_roomId.value)
                .collect { entities ->
                    _messages.value = entities.sortedBy { it.timestamp }
                }
        }
    }

    fun sendMessage(content: String) {
        if (content.isBlank()) return

        viewModelScope.launch {
            val message = MessageEntity(
                messageId = UUID.randomUUID().toString(),
                roomId = _roomId.value,
                senderId = matrixClient.getUserId() ?: "unknown",
                content = content,
                timestamp = System.currentTimeMillis(),
                status = Constants.STATUS_PENDING,
                type = Constants.TYPE_TEXT
            )

            // Guardar en DB local
            messageRepository.insertMessage(message)

            // Enviar a Matrix server
            messageRepository.sendMessage(_roomId.value, content)
                .onSuccess { messageId ->
                    // Actualizar estado a "sent"
                    val sentMessage = message.copy(
                        messageId = messageId,
                        status = Constants.STATUS_SENT
                    )
                    messageRepository.insertMessage(sentMessage)

                    // Enviar notificación ntfy a la pareja
                    ntfyManager.sendMessage(
                        title = "Nuevo mensaje 💕",
                        message = content.take(100),
                        tags = listOf("heart", "messaging")
                    )
                }
                .onFailure { error ->
                    // Mantener como pending para reintentar
                    val errorMessage = message.copy(status = Constants.STATUS_PENDING)
                    messageRepository.insertMessage(errorMessage)
                }
        }
    }

    fun onTyping(isTyping: Boolean) {
        _typingIndicator.value = isTyping
        // TODO: Enviar indicador de escribiendo a Matrix
    }

    fun setPartnerTyping(isTyping: Boolean) {
        _partnerTyping.value = isTyping
    }

    fun deleteMessage(messageId: String) {
        viewModelScope.launch {
            val messages = _messages.value
            val message = messages.find { it.messageId == messageId }
            message?.let { messageRepository.deleteMessage(it) }
        }
    }

    fun updateMessageStatus(messageId: String, status: String) {
        viewModelScope.launch {
            val messages = _messages.value
            val message = messages.find { it.messageId == messageId }
            message?.let {
                messageRepository.insertMessage(it.copy(status = status))
            }
        }
    }

    fun setRoomId(newRoomId: String) {
        _roomId.value = newRoomId
        loadMessages()
    }

    suspend fun sendImage(imageUrl: String, width: Int, height: Int) {
        viewModelScope.launch {
            val message = MessageEntity(
                messageId = UUID.randomUUID().toString(),
                roomId = _roomId.value,
                senderId = matrixClient.getUserId() ?: "unknown",
                content = "",
                timestamp = System.currentTimeMillis(),
                status = Constants.STATUS_PENDING,
                type = Constants.TYPE_IMAGE,
                mediaUrl = imageUrl
            )
            messageRepository.insertMessage(message)
            // TODO: Implementar envío real de imagen
        }
    }

    suspend fun sendVoiceNote(audioUrl: String, duration: Long, waveform: List<Int>) {
        viewModelScope.launch {
            val message = MessageEntity(
                messageId = UUID.randomUUID().toString(),
                roomId = _roomId.value,
                senderId = matrixClient.getUserId() ?: "unknown",
                content = "",
                timestamp = System.currentTimeMillis(),
                status = Constants.STATUS_PENDING,
                type = Constants.TYPE_VOICE,
                mediaUrl = audioUrl
            )
            messageRepository.insertMessage(message)
            // TODO: Implementar envío real de nota de voz
        }
    }
}
