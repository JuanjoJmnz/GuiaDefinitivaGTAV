package com.juanjojmnz.guiadefinitivagtav.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.juanjojmnz.guiadefinitivagtav.data.model.MainMission
import com.juanjojmnz.guiadefinitivagtav.data.model.MissionCharacter
import com.juanjojmnz.guiadefinitivagtav.data.model.StrangersAndFreaksMission
import com.juanjojmnz.guiadefinitivagtav.data.model.toCharacterList
import com.juanjojmnz.guiadefinitivagtav.data.model.toSingleCharacter
import com.juanjojmnz.guiadefinitivagtav.data.repository.MissionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class MissionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MissionRepository(application)

    private val _mainMissions = MutableStateFlow<List<MainMission>>(emptyList())
    open val mainMissions: StateFlow<List<MainMission>> = _mainMissions.asStateFlow()

    private val _strangersAndFreaksMissions = MutableStateFlow<List<StrangersAndFreaksMission>>(emptyList())
    open val strangersAndFreaksMissions: StateFlow<List<StrangersAndFreaksMission>> = _strangersAndFreaksMissions.asStateFlow()

    private val _selectedCharacter = MutableStateFlow<MissionCharacter?>(null)
    val selectedCharacter: StateFlow<MissionCharacter?> = _selectedCharacter.asStateFlow()

    init {
        loadMissions()
    }

    private fun loadMissions() {
        viewModelScope.launch {
            val gtaData = repository.loadGtaData()
            gtaData?.let {
                _mainMissions.value = it.mainMissions
                _strangersAndFreaksMissions.value = it.strangersAndFreaksMissions
            }
        }
    }


    val filteredMainMissions: StateFlow<List<MainMission>> =
        combine(_mainMissions, _selectedCharacter) { missions, character ->
            if (character == null || character == MissionCharacter.ALL) {
                missions
            } else {
                missions.filter { mission ->
                    val missionCharacters = mission.character.toCharacterList()
                    missionCharacters.contains(character) || missionCharacters.contains(MissionCharacter.ALL)
                }
            }
        }.stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000), emptyList())


    val filteredStrangersAndFreaksMissions: StateFlow<List<StrangersAndFreaksMission>> =
        combine(_strangersAndFreaksMissions, _selectedCharacter) { missions, selectedChar ->
            if (selectedChar == null || selectedChar == MissionCharacter.ALL) {
                missions
            } else {
                missions.filter { mission ->
                    val missionCharacter = mission.character.toSingleCharacter()
                    missionCharacter == selectedChar
                }
            }
        }.stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000), emptyList())


    fun selectCharacter(character: MissionCharacter?) {
        _selectedCharacter.value = character
    }
}