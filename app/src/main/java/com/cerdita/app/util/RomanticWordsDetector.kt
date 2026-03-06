package com.cerdita.app.util

import timber.log.Timber

/**
 * Detector de palabras románticas para activar efectos
 * 
 * Detecta 50+ palabras románticas en 8 categorías diferentes
 * y activa el efecto correspondiente cuando se encuentra una coincidencia.
 */
object RomanticWordsDetector {

    data class RomanticCategory(
        val name: String,
        val words: List<String>,
        val effectType: EffectType
    )

    enum class EffectType {
        HEARTS,      // Corazones
        STARS,       // Estrellas
        SUNRISE,     // Amanecer
        MOON,        // Luna
        CONFETTI,    // Confeti
        CLOUDS,      // Nubes
        FLOWERS,     // Flores
        HUG,         // Abrazo
        NONE         // Sin efecto
    }

    val categories = listOf(
        RomanticCategory(
            name = "Amor",
            words = listOf(
                "te amo", "te quiero", "te adoro", "eres mi amor",
                "mi vida", "mi cielo", "mi corazón", "te amo mucho",
                "amor mio", "mi amor", "amor"
            ),
            effectType = EffectType.HEARTS
        ),
        RomanticCategory(
            name = "Belleza",
            words = listOf(
                "eres hermosa", "eres bella", "eres preciosa",
                "qué linda", "te ves hermosa", "hermosa", "bella",
                "preciosa", "linda", "bonita"
            ),
            effectType = EffectType.STARS
        ),
        RomanticCategory(
            name = "Buenos Días",
            words = listOf(
                "buenos días", "buen día", "feliz día",
                "que tengas lindo día", "buenos días amor",
                "que amanezcas bien", "buen día amor"
            ),
            effectType = EffectType.SUNRISE
        ),
        RomanticCategory(
            name = "Buenas Noches",
            words = listOf(
                "buenas noches", "que descanses", "dulces sueños",
                "que sueñes bonito", "buenas noches amor",
                "hasta mañana", "que duermas bien"
            ),
            effectType = EffectType.MOON
        ),
        RomanticCategory(
            name = "Cumpleaños",
            words = listOf(
                "feliz cumpleaños", "feliz cumple", "que cumplas muchos más",
                "feliz cumpleaños amor", "muchas felicidades"
            ),
            effectType = EffectType.CONFETTI
        ),
        RomanticCategory(
            name = "Extrañar",
            words = listOf(
                "te extraño", "te echo de menos", "me haces falta",
                "quiero verte", "extraño", "te necesito"
            ),
            effectType = EffectType.CLOUDS
        ),
        RomanticCategory(
            name = "Gracias",
            words = listOf(
                "gracias", "gracias mi vida", "mil gracias",
                "te agradezco", "gracias amor", "muchas gracias"
            ),
            effectType = EffectType.FLOWERS
        ),
        RomanticCategory(
            name = "Abrazo",
            words = listOf(
                "abrazo", "abrazame", "te abrazo", "abrazos",
                "abrazo fuerte", "abrazo grande", "abrazote"
            ),
            effectType = EffectType.HUG
        )
    )

    /**
     * Detecta si un mensaje contiene palabras románticas
     * @return Categoría detectada o null
     */
    fun detectCategory(message: String): RomanticCategory? {
        val lowerMessage = message.lowercase()
        
        for (category in categories) {
            for (word in category.words) {
                if (lowerMessage.contains(word)) {
                    Timber.d("RomanticWordsDetector: Detected '${category.name}' in message")
                    return category
                }
            }
        }
        
        return null
    }

    /**
     * Detecta el tipo de efecto para un mensaje
     */
    fun detectEffectType(message: String): EffectType {
        return detectCategory(message)?.effectType ?: EffectType.NONE
    }

    /**
     * Verifica si un mensaje contiene palabras románticas
     */
    fun isRomantic(message: String): Boolean {
        return detectCategory(message) != null
    }

    /**
     * Obtiene todas las palabras de una categoría
     */
    fun getWordsByCategory(category: EffectType): List<String> {
        return categories
            .filter { it.effectType == category }
            .flatMap { it.words }
    }

    /**
     * Obtiene el total de palabras románticas registradas
     */
    fun getTotalWords(): Int {
        return categories.sumOf { it.words.size }
    }
}
