package com.cerdita.app.data.remote.matrix

import im.vector.matrix.android.api.session.Session
import im.vector.matrix.android.api.session.room.Room
import im.vector.matrix.android.api.session.room.model.message.MessageContent
import im.vector.matrix.android.api.session.room.model.message.MessageType
import im.vector.matrix.android.api.session.room.model.message.TextMessageContent
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Gestor de rooms y mensajes de Matrix
 * 
 * RESPONSABILIDADES:
 * 1. Crear/obtener room de chat
 * 2. Enviar mensajes de texto
 * 3. Enviar multimedia (imágenes, videos, audio)
 * 4. Escuchar eventos de la room
 * 5. Sincronizar mensajes
 */
@Singleton
class MatrixRoomManager @Inject constructor(
    private val matrixClient: MatrixClient
) {
    private var currentRoom: Room? = null
    private var roomId: String? = null

    // ═══════════════════════════════════════════════════════════════════
    // GESTIÓN DE ROOM
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Obtiene o crea la room de chat con la pareja
     * 
     * Para 2 usuarios, se usa una room directa (DM)
     */
    suspend fun getOrCreateDirectRoom(partnerUserId: String): Result<String> {
        return try {
            val session = matrixClient.getSession() 
                ?: return Result.failure(Exception("No session"))

            // Buscar room directa existente
            val existingRoom = session.getExistingDirectRoomWithUser(partnerUserId)
            
            if (existingRoom != null) {
                currentRoom = existingRoom
                roomId = existingRoom.roomId
                Timber.d("MatrixRoomManager: Using existing room: ${existingRoom.roomId}")
                return Result.success(existingRoom.roomId)
            }

            // Crear nueva room directa
            val roomInfo = im.vector.matrix.android.api.session.room.model.create.RoomCreateContent(
                isDirect = true,
                invitedUserIds = listOf(partnerUserId)
            )

            val newRoom = suspendCancellableCoroutine<Room> { continuation ->
                session.createRoom(
                    roomCreateContent = roomInfo,
                    callback = object : im.vector.matrix.android.api.callback.MatrixCallback<Room> {
                        override fun onSuccess(data: Room) {
                            continuation.resume(data)
                        }
                        override fun onFailure(error: Throwable) {
                            continuation.resumeWithException(error)
                        }
                    }
                )
            }

            currentRoom = newRoom
            roomId = newRoom.roomId
            Timber.d("MatrixRoomManager: Created new room: ${newRoom.roomId}")
            Result.success(newRoom.roomId)
        } catch (e: Exception) {
            Timber.e(e, "MatrixRoomManager: Error getting/creating room")
            Result.failure(e)
        }
    }

    /**
     * Establece la room actual por ID
     */
    fun setCurrentRoom(roomId: String): Result<Unit> {
        return try {
            val session = matrixClient.getSession() 
                ?: return Result.failure(Exception("No session"))

            val room = session.getRoom(roomId)
                ?: return Result.failure(Exception("Room not found"))

            currentRoom = room
            this.roomId = roomId
            Timber.d("MatrixRoomManager: Set current room: $roomId")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "MatrixRoomManager: Error setting room")
            Result.failure(e)
        }
    }

    fun getCurrentRoom(): Room? = currentRoom
    fun getCurrentRoomId(): String? = roomId

    // ═══════════════════════════════════════════════════════════════════
    // ENVÍO DE MENSAJES
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Envía mensaje de texto
     */
    suspend fun sendTextMessage(text: String): Result<Unit> {
        return try {
            val room = currentRoom 
                ?: return Result.failure(Exception("No room selected"))

            Timber.d("MatrixRoomManager: Sending text message: $text")

            val messageContent = TextMessageContent(
                body = text,
                msgType = MessageType.MSGTYPE_TEXT.value
            )

            suspendCancellableCoroutine { continuation ->
                room.send(messageContent, object : im.vector.matrix.android.api.callback.MatrixCallback<Unit> {
                    override fun onSuccess(data: Unit) {
                        continuation.resume(Unit)
                    }
                    override fun onFailure(error: Throwable) {
                        continuation.resumeWithException(error)
                    }
                })
            }

            Timber.d("MatrixRoomManager: Message sent successfully")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "MatrixRoomManager: Error sending message")
            Result.failure(e)
        }
    }

    /**
     * Envía mensaje de sistema (para rotación de topics, etc.)
     */
    suspend fun sendSystemMessage(type: String, content: String): Result<Unit> {
        val systemText = "🔄 SYSTEM: $type - $content"
        return sendTextMessage(systemText)
    }

    // ═══════════════════════════════════════════════════════════════════
    // RECEPCIÓN DE MENSAJES
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Obtiene los últimos N mensajes de la room
     */
    suspend fun getRecentMessages(limit: Int = 50): Result<List<im.vector.matrix.android.api.session.room.model.RoomMember>> {
        return try {
            val room = currentRoom 
                ?: return Result.failure(Exception("No room selected"))

            val timeline = room.timelineService()
            val messages = timeline.getRecentMessages(limit)
            
            Result.success(messages)
        } catch (e: Exception) {
            Timber.e(e, "MatrixRoomManager: Error getting messages")
            Result.failure(e)
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    // LISTENERS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Agrega listener para eventos de timeline
     */
    fun addTimelineListener(listener: im.vector.matrix.android.api.session.room.timeline.TimelineListener) {
        currentRoom?.timelineService()?.addListener(listener)
    }

    /**
     * Remueve listener
     */
    fun removeTimelineListener(listener: im.vector.matrix.android.api.session.room.timeline.TimelineListener) {
        currentRoom?.timelineService()?.removeListener(listener)
    }

    // ═══════════════════════════════════════════════════════════════════
    // UTILIDADES
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Verifica si un mensaje es de sistema
     */
    fun isSystemMessage(body: String): Boolean {
        return body.startsWith("🔄 SYSTEM:")
    }

    /**
     * Parsea mensaje de sistema
     */
    fun parseSystemMessage(body: String): SystemMessage? {
        if (!isSystemMessage(body)) return null
        
        val parts = body.removePrefix("🔄 SYSTEM: ").split(" - ")
        if (parts.size != 2) return null
        
        return SystemMessage(
            type = parts[0],
            content = parts[1]
        )
    }

    /**
     * Limpia recursos
     */
    fun clear() {
        currentRoom?.timelineService()?.dispose()
        currentRoom = null
        roomId = null
    }
}

/**
 * Mensaje de sistema para comunicación interna
 */
data class SystemMessage(
    val type: String,
    val content: String
)
