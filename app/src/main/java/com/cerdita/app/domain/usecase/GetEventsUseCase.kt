package com.cerdita.app.domain.usecase

import com.cerdita.app.data.repository.MessageRepository
import com.cerdita.app.domain.model.Event
import javax.inject.Inject

/**
 * Caso de uso para obtener eventos/fechas especiales
 */
class GetEventsUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    /**
     * Obtiene todos los eventos guardados
     * 
     * @return Lista de eventos ordenados por fecha
     */
    suspend operator fun invoke(): Result<List<Event>> {
        return messageRepository.getEvents()
    }

    /**
     * Obtiene eventos próximos (próximos 30 días)
     * 
     * @return Lista de eventos próximos ordenados por fecha
     */
    suspend fun getUpcomingEvents(): Result<List<Event>> {
        return messageRepository.getUpcomingEvents()
    }

    /**
     * Obtiene eventos de un tipo específico
     * 
     * @param type Tipo de evento (cumpleaños, aniversario, evento personalizado)
     * @return Lista de eventos del tipo especificado
     */
    suspend fun getEventsByType(type: String): Result<List<Event>> {
        return messageRepository.getEventsByType(type)
    }
}
