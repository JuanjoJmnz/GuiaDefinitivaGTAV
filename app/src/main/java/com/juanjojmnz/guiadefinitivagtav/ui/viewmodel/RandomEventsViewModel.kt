package com.juanjojmnz.guiadefinitivagtav.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.juanjojmnz.guiadefinitivagtav.data.model.RandomEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.IOException

class RandomEventsViewModel(application: Application) : AndroidViewModel(application) {

    private val _events = MutableStateFlow<List<RandomEvent>>(emptyList())
    val events: StateFlow<List<RandomEvent>> = _events

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadRandomEvents()
    }

    private fun loadRandomEvents() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val jsonString = getApplication<Application>().assets.open("random_events.json")
                    .bufferedReader()
                    .use { it.readText() }
                val eventList = Json.decodeFromString<List<RandomEvent>>(jsonString)
                _events.value = eventList
            } catch (e: IOException) {
                _error.value = "Error al cargar los eventos: ${e.message}"
                e.printStackTrace()
            } catch (e: Exception) {
                _error.value = "Error al parsear los eventos: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getEventById(eventId: Int): RandomEvent? {
        return _events.value.find { it.id == eventId }
    }
}