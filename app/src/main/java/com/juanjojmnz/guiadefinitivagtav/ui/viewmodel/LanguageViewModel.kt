package com.juanjojmnz.guiadefinitivagtav.ui.viewmodel

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.juanjojmnz.guiadefinitivagtav.data.repository.UserPreferencesRepository
import com.juanjojmnz.guiadefinitivagtav.ui.AppLanguage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.text.isNotBlank

private const val INITIAL_LOADING_STATE = "_INITIAL_LOADING_"

class LanguageViewModel(
    application: Application,
    private val userPreferencesRepository: UserPreferencesRepository
) : AndroidViewModel(application) {

    val savedLanguageCode: StateFlow<String?> = userPreferencesRepository.appLanguageCodeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = INITIAL_LOADING_STATE
        )

    val currentAppLanguage: StateFlow<AppLanguage?> = savedLanguageCode.map { code ->
        if (code == INITIAL_LOADING_STATE) null
        else AppLanguage.entries.find { it.code == code }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )

    fun saveAndApplyLanguage(language: AppLanguage) {
        viewModelScope.launch {
            userPreferencesRepository.saveAppLanguageCode(language.code)
            setAppLocale(language.code)
        }
    }

    private fun setAppLocale(languageCode: String) {
        if (languageCode.isBlank() || languageCode == INITIAL_LOADING_STATE) return

        val locale = Locale(languageCode)
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    fun applyLocaleFromSavedPreferenceOnAppStart() {
        viewModelScope.launch {
            val languageCode = userPreferencesRepository.appLanguageCodeFlow.firstOrNull()
            if (languageCode != null && languageCode.isNotBlank()) {
                setAppLocale(languageCode)
            }
        }
    }
}

class LanguageViewModelFactory(
    private val application: Application,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LanguageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LanguageViewModel(application, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}