package com.juanjojmnz.guiadefinitivagtav.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.juanjojmnz.guiadefinitivagtav.data.model.LetterScrapItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.IOException

class LetterScrapsViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application.applicationContext
    private val sharedPreferences: SharedPreferences =
        appContext.getSharedPreferences("LetterScrapsPrefs", Context.MODE_PRIVATE)

    private val _letterScraps = MutableStateFlow<List<LetterScrapItem>>(emptyList())
    val letterScraps: StateFlow<List<LetterScrapItem>> = _letterScraps.asStateFlow()

    companion object {
        private const val LETTER_SCRAPS_FILE = "letter_scraps.json"
        private const val FOUND_SCRAP_KEY_PREFIX = "found_scrap_"
        private const val TAG = "LetterScrapsVM"
    }

    init {
        loadLetterScrapsFromJson()
    }

    private fun loadLetterScrapsFromJson() {
        viewModelScope.launch {
            try {
                val jsonString = appContext.assets.open(LETTER_SCRAPS_FILE)
                    .bufferedReader()
                    .use { it.readText() }
                Log.d(TAG, "Successfully read JSON string for letter scraps.")

                val parsedScraps = Json.decodeFromString<List<LetterScrapItem>>(jsonString)
                Log.d(TAG, "Successfully parsed ${parsedScraps.size} scraps from JSON.")

                _letterScraps.value = parsedScraps.map { scrapFromJson ->
                    scrapFromJson.copy(
                        isFound = isScrapFoundInPrefs(scrapFromJson.id)
                    )
                }
                Log.d(TAG, "Successfully loaded and processed ${_letterScraps.value.size} scraps.")

            } catch (e: IOException) {
                Log.e(TAG, "Error reading letter scraps JSON: ${e.message}", e)
                _letterScraps.value = emptyList()
            } catch (e: Exception) {
                Log.e(TAG, "Error parsing or processing letter scraps JSON: ${e.message}", e)
                _letterScraps.value = emptyList()
            }
        }
    }

    fun toggleScrapFoundStatus(scrapId: Int) {
        val currentStatus = isScrapFoundInPrefs(scrapId)
        val newStatus = !currentStatus

        with(sharedPreferences.edit()) {
            putBoolean("$FOUND_SCRAP_KEY_PREFIX$scrapId", newStatus)
            apply()
        }

        _letterScraps.update { currentList ->
            currentList.map { scrap ->
                if (scrap.id == scrapId) {
                    scrap.copy(isFound = newStatus)
                } else {
                    scrap
                }
            }
        }
        Log.d(TAG, "Toggled scrap ID $scrapId to isFound: $newStatus")
    }

    private fun isScrapFoundInPrefs(scrapId: Int): Boolean {
        return sharedPreferences.getBoolean("$FOUND_SCRAP_KEY_PREFIX$scrapId", false)
    }
}