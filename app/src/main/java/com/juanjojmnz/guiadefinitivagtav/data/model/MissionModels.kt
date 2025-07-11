package com.juanjojmnz.guiadefinitivagtav.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GtaData(
    val mainMissions: List<MainMission>,
    val strangersAndFreaksMissions: List<StrangersAndFreaksMission>
    // Puedes añadir más listas aquí para otras categorías si tienen estructuras diferentes
)

@Serializable
data class MainMission(
    val id: String,
    val name: String,
    val description: String,
    val character: String, // Podrías querer parsear esto a una List<String>
    val reward: String? = null,
    val goldMedalRequirements: List<String>,
    val unlocksAtEvent: String,
    val notes: String? = null
)

@Serializable
data class StrangersAndFreaksMission(
    val id: String,
    val name: String,
    val description: String,
    val character: String, // Podrías querer parsear esto a un enum o String específico
    val characterEncountered: String,
    val reward: String? = null,
    val availability: String,
    val location: String,
    val goldMedalRequirements: List<String>
)

// Enum para los personajes (opcional pero recomendado)
enum class MissionCharacter {
    MICHAEL, FRANKLIN, TREVOR, ALL
}

// Funciones de utilidad para convertir la cadena "character" a una lista de enum
// (Esto es un ejemplo, podrías necesitar una lógica más robusta)
fun String.toCharacterList(): List<MissionCharacter> {
    val characters = mutableListOf<MissionCharacter>()
    if (this.contains("Michael", ignoreCase = true)) characters.add(MissionCharacter.MICHAEL)
    if (this.contains("Franklin", ignoreCase = true)) characters.add(MissionCharacter.FRANKLIN)
    if (this.contains("Trevor", ignoreCase = true)) characters.add(MissionCharacter.TREVOR)
    if (characters.isEmpty() && this.equals("All", ignoreCase = true)) { // O alguna otra palabra clave para todos
        characters.add(MissionCharacter.ALL)
    }
    return characters
}

fun String.toSingleCharacter(): MissionCharacter? {
    return when {
        this.equals("Michael", ignoreCase = true) -> MissionCharacter.MICHAEL
        this.equals("Franklin", ignoreCase = true) -> MissionCharacter.FRANKLIN
        this.equals("Trevor", ignoreCase = true) -> MissionCharacter.TREVOR
        else -> null
    }
}
    