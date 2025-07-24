package com.juanjojmnz.guiadefinitivagtav.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.appSettingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

class UserPreferencesRepository(private val context: Context) {

    private object PreferencesKeys {
        val APP_LANGUAGE_CODE = stringPreferencesKey("app_language_code")
    }

    val appLanguageCodeFlow: Flow<String?> = context.appSettingsDataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.APP_LANGUAGE_CODE]
        }

    suspend fun saveAppLanguageCode(languageCode: String) {
        context.appSettingsDataStore.edit { preferences ->
            preferences[PreferencesKeys.APP_LANGUAGE_CODE] = languageCode
        }
    }
}
