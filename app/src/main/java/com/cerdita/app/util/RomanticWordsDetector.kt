package com.cerdita.app.util

object RomanticWordsDetector {
    
    data class RomanticWord(
        val word: String,
        val effect: RomanticEffectType,
        val category: WordCategory
    )
    
    enum class RomanticEffectType {
        LOVE,      // 💕
        BEAUTY,    // ✨
        MORNING,   // 🌅
        NIGHT,     // 🌙
        BIRTHDAY,  // 🎂
        MISS,      // 💭
        THANKS,    // 💕
        SORRY,     // 😢
        CONGRATS,  // 🎊
        ENCOURAGE, // 💪
        MEET       // 🤗
    }
    
    enum class WordCategory {
        AMOR,
        BELLEZA,
        SALUDOS,
        CUMPLEANOS,
        EXTRANAR,
        GRATITUD,
        PERDON,
        FELICIDADES,
        ANIMOS,
        ENCUENTRO
    }
    
    private val romanticWords = listOf(
        // Amor
        RomanticWord("te amo", RomanticEffectType.LOVE, WordCategory.AMOR),
        RomanticWord("te quiero", RomanticEffectType.LOVE, WordCategory.AMOR),
        RomanticWord("te adoro", RomanticEffectType.LOVE, WordCategory.AMOR),
        RomanticWord("eres mi amor", RomanticEffectType.LOVE, WordCategory.AMOR),
        RomanticWord("mi vida", RomanticEffectType.LOVE, WordCategory.AMOR),
        RomanticWord("mi corazón", RomanticEffectType.LOVE, WordCategory.AMOR),
        
        // Belleza
        RomanticWord("eres hermosa", RomanticEffectType.BEAUTY, WordCategory.BELLEZA),
        RomanticWord("eres bella", RomanticEffectType.BEAUTY, WordCategory.BELLEZA),
        RomanticWord("eres preciosa", RomanticEffectType.BEAUTY, WordCategory.BELLEZA),
        RomanticWord("qué linda", RomanticEffectType.BEAUTY, WordCategory.BELLEZA),
        
        // Buenos días
        RomanticWord("buenos días", RomanticEffectType.MORNING, WordCategory.SALUDOS),
        RomanticWord("buen día", RomanticEffectType.MORNING, WordCategory.SALUDOS),
        RomanticWord("feliz día", RomanticEffectType.MORNING, WordCategory.SALUDOS),
        
        // Buenas noches
        RomanticWord("buenas noches", RomanticEffectType.NIGHT, WordCategory.SALUDOS),
        RomanticWord("que descanses", RomanticEffectType.NIGHT, WordCategory.SALUDOS),
        RomanticWord("dulces sueños", RomanticEffectType.NIGHT, WordCategory.SALUDOS),
        
        // Cumpleaños
        RomanticWord("feliz cumpleaños", RomanticEffectType.BIRTHDAY, WordCategory.CUMPLEANOS),
        RomanticWord("feliz cumple", RomanticEffectType.BIRTHDAY, WordCategory.CUMPLEANOS),
        
        // Extrañar
        RomanticWord("te extraño", RomanticEffectType.MISS, WordCategory.EXTRANAR),
        RomanticWord("te echo de menos", RomanticEffectType.MISS, WordCategory.EXTRANAR),
        RomanticWord("me haces falta", RomanticEffectType.MISS, WordCategory.EXTRANAR),
        
        // Gracias
        RomanticWord("gracias", RomanticEffectType.THANKS, WordCategory.GRATITUD),
        RomanticWord("gracias mi vida", RomanticEffectType.THANKS, WordCategory.GRATITUD),
        RomanticWord("mil gracias", RomanticEffectType.THANKS, WordCategory.GRATITUD),
        
        // Perdón
        RomanticWord("perdón", RomanticEffectType.SORRY, WordCategory.PERDON),
        RomanticWord("perdóname", RomanticEffectType.SORRY, WordCategory.PERDON),
        RomanticWord("lo siento", RomanticEffectType.SORRY, WordCategory.PERDON),
        RomanticWord("disculpa", RomanticEffectType.SORRY, WordCategory.PERDON),
        
        // Felicidades
        RomanticWord("felicidades", RomanticEffectType.CONGRATS, WordCategory.FELICIDADES),
        RomanticWord("lo lograste", RomanticEffectType.CONGRATS, WordCategory.FELICIDADES),
        RomanticWord("orgulloso", RomanticEffectType.CONGRATS, WordCategory.FELICIDADES),
        
        // Ánimos
        RomanticWord("tú puedes", RomanticEffectType.ENCOURAGE, WordCategory.ANIMOS),
        RomanticWord("ánimos", RomanticEffectType.ENCOURAGE, WordCategory.ANIMOS),
        RomanticWord("eres fuerte", RomanticEffectType.ENCOURAGE, WordCategory.ANIMOS),
        
        // Encontro
        RomanticWord("ya quiero verte", RomanticEffectType.MEET, WordCategory.ENCUENTRO),
        RomanticWord("nos vemos pronto", RomanticEffectType.MEET, WordCategory.ENCUENTRO),
        RomanticWord("te voy a ver", RomanticEffectType.MEET, WordCategory.ENCUENTRO)
    )
    
    fun detectWords(text: String): List<RomanticWord> {
        val lowerText = text.lowercase()
        return romanticWords.filter { it.word in lowerText }
    }
    
    fun hasRomanticWords(text: String): Boolean {
        return detectWords(text).isNotEmpty()
    }
    
    fun getEffectForWord(text: String): RomanticEffectType? {
        return detectWords(text).firstOrNull()?.effect
    }
    
    fun addCustomWord(word: String, effect: RomanticEffectType, category: WordCategory) {
        // TODO: Implementar almacenamiento de palabras personalizadas
        romanticWords.add(RomanticWord(word, effect, category))
    }
}
