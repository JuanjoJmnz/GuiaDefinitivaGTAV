package com.juanjojmnz.guiadefinitivagtav.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GtaData(
    val mainMissions: List<MainMission>,
    val strangersAndFreaksMissions: List<StrangersAndFreaksMission>
)

@Serializable
data class MainMission(
    val id: String,
    val name: String,
    val description: String,
    val character: String,
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
    val character: String,
    val characterEncountered: String,
    val reward: String? = null,
    val availability: String,
    val location: String,
    val goldMedalRequirements: List<String>
)

// Enum para los personajes
enum class MissionCharacter {
    MICHAEL, FRANKLIN, TREVOR, ALL
}


fun String.toCharacterList(): List<MissionCharacter> {
    val characters = mutableListOf<MissionCharacter>()
    if (this.contains("Michael", ignoreCase = true)) characters.add(MissionCharacter.MICHAEL)
    if (this.contains("Franklin", ignoreCase = true)) characters.add(MissionCharacter.FRANKLIN)
    if (this.contains("Trevor", ignoreCase = true)) characters.add(MissionCharacter.TREVOR)
    if (characters.isEmpty() && this.equals("All", ignoreCase = true)) {
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
    