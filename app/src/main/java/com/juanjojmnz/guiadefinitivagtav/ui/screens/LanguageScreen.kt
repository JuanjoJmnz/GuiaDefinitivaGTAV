package com.juanjojmnz.guiadefinitivagtav.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juanjojmnz.guiadefinitivagtav.ui.AppLanguage
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme

@Composable
fun LanguageScreen(
    onLanguageSelected: (AppLanguage) -> Unit,
    onNavigateToNextScreen: () -> Unit
) {
    var selectedLanguage by remember { mutableStateOf<AppLanguage?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selecciona tu idioma / Select your language",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        AppLanguage.entries.forEach { language ->
            Button(
                onClick = {
                    selectedLanguage = language
                    onLanguageSelected(language)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedLanguage == language) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (selectedLanguage == language) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text(language.displayName)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (selectedLanguage != null) {
                    onNavigateToNextScreen()
                }
            },
            enabled = selectedLanguage != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar / Continue")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageSelectionScreenPreview() {
    GuiaDefinitivaGTAVTheme {
        LanguageScreen(onLanguageSelected = {}, onNavigateToNextScreen = {})
    }
}
