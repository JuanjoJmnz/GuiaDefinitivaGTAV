package com.juanjojmnz.guiadefinitivagtav.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.juanjojmnz.guiadefinitivagtav.data.model.SpaceshipPartItem
import com.juanjojmnz.guiadefinitivagtav.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.IOException

class SpaceshipPartsViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application.applicationContext
    private val sharedPreferences: SharedPreferences =
        appContext.getSharedPreferences("SpaceshipPartsPrefs", Context.MODE_PRIVATE)

    private val _spaceshipParts = MutableStateFlow<List<SpaceshipPartItem>>(emptyList())
    val spaceshipParts: StateFlow<List<SpaceshipPartItem>> = _spaceshipParts.asStateFlow()

    companion object {
        private const val SPACESHIP_PARTS_FILE = "spaceship_parts.json"
        private const val FOUND_PARTS_KEY_PREFIX = "found_part_"
        private const val TAG = "SpaceshipVM"
    }

    init {
        loadSpaceshipPartsFromJson()
    }

    private fun loadSpaceshipPartsFromJson() {
        viewModelScope.launch {
            try {
                val jsonString = appContext.assets.open(SPACESHIP_PARTS_FILE)
                    .bufferedReader()
                    .use { it.readText() }
                Log.d(TAG, "Successfully read JSON string. Length: ${jsonString.length} characters.")

                val parsedParts = Json.decodeFromString<List<SpaceshipPartItem>>(jsonString)
                Log.d(TAG, "Successfully parsed ${parsedParts.size} parts from JSON.")

                _spaceshipParts.value = parsedParts.map { partFromJson ->
                    val mapResId = getDrawableResourceIdByName(partFromJson.mapImageResourceName)
                    val locResId = getDrawableResourceIdByName(partFromJson.locationImageResourceName)

                    partFromJson.copy(
                        mapImageResId = mapResId,
                        locationImageResId = locResId,
                        isFound = isPartFoundInPrefs(partFromJson.id)
                    )
                }
                Log.d(TAG, "Successfully loaded and processed ${_spaceshipParts.value.size} parts.")

            } catch (e: IOException) {
                Log.e(TAG, "Error reading spaceship parts JSON: ${e.message}", e)
                _spaceshipParts.value = emptyList()
            } catch (e: Exception) {
                Log.e(TAG, "Error parsing or processing spaceship parts JSON: ${e.message}", e)
                _spaceshipParts.value = emptyList()
            }
        }
    }

    private fun getDrawableResourceIdByName(resourceName: String): Int {
        val resourceId = appContext.resources.getIdentifier(
            resourceName,
            "drawable",
            appContext.packageName
        )
        if (resourceId == 0) {
            Log.w(TAG, "Drawable resource not found for name: $resourceName. Using 0.")
        }
        return resourceId
    }

    fun togglePartFoundStatus(partId: Int) {
        val currentStatus = isPartFoundInPrefs(partId)
        val newStatus = !currentStatus

        with(sharedPreferences.edit()) {
            putBoolean("$FOUND_PARTS_KEY_PREFIX$partId", newStatus)
            apply()
        }

        _spaceshipParts.update { currentList ->
            currentList.map { part ->
                if (part.id == partId) {
                    part.copy(isFound = newStatus)
                } else {
                    part
                }
            }
        }
        Log.d(TAG, "Toggled part ID $partId to isFound: $newStatus")
    }

    private fun isPartFoundInPrefs(partId: Int): Boolean {
        return sharedPreferences.getBoolean("$FOUND_PARTS_KEY_PREFIX$partId", false)
    }
}