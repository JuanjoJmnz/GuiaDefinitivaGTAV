package com.juanjojmnz.guiadefinitivagtav.data.repository

import android.content.Context
import com.juanjojmnz.guiadefinitivagtav.data.model.GtaData
import kotlinx.serialization.json.Json
import java.io.IOException

class MissionRepository(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true } // Ignora claves no mapeadas en tus data classes

    fun loadGtaData(): GtaData? {
        return try {
            val jsonString = context.assets.open("gta_data.json").bufferedReader().use { it.readText() }
            json.decodeFromString<GtaData>(jsonString)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null // Maneja el error apropiadamente
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }
}