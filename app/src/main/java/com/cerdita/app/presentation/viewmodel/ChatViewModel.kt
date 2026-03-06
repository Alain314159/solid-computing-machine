package com.cerdita.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerdita.app.data.local.database.dao.MessageDao
import com.cerdita.app.data.local.database.entity.MessageEntity
import com.cerdita.app.data.remote.matrix.MatrixClient
import com.cerdita.app.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val matrixClient: MatrixClient,
    private val messageDao: MessageDao
) : ViewModel() {

    private val _messages = MutableStateFlow<List<MessageEntity>>(emptyList())
    val messages: StateFlow<List<MessageEntity>> = _messages

    private val _roomId = MutableStateFlow<String>("room_1") // TODO: Room ID real de Matrix
    val roomId: StateFlow<String> = _roomId

    private val _typingIndicator = MutableStateFlow(false)
    val typingIndicator: StateFlow<Boolean> = _typingIndicator

    init {
        loadMessages()
    }

    private fun loadMessages() {
        viewModelScope.launch {
            messageDao.getMessagesByRoom(_roomId.value)
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
            messageDao.insertMessage(message)

            // TODO: Enviar a Matrix server
            // Actualizar estado a "sent"
            val sentMessage = message.copy(status = Constants.STATUS_SENT)
            messageDao.updateMessage(sentMessage)
        }
    }

    fun onTyping(isTyping: Boolean) {
        _typingIndicator.value = isTyping
        // TODO: Enviar indicador de escribiendo a Matrix
    }

    fun deleteMessage(messageId: String) {
        viewModelScope.launch {
            val messages = _messages.value
            val message = messages.find { it.messageId == messageId }
            message?.let { messageDao.deleteMessage(it) }
        }
    }

    fun updateMessageStatus(messageId: String, status: String) {
        viewModelScope.launch {
            val messages = _messages.value
            val message = messages.find { it.messageId == messageId }
            message?.let {
                messageDao.updateMessage(it.copy(status = status))
            }
        }
    }
}
