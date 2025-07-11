package com.juanjojmnz.guiadefinitivagtav.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceholderScreen(navController: NavController, screenTitle: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
                navigationIcon = {
                    // Solo muestra el botón de retroceso si hay algo a lo que volver
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer // Color del icono de navegación
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Aplica el padding del Scaffold
                .padding(16.dp), // Un padding adicional para el contenido dentro de la columna
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Contenido para: $screenTitle",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Próximamente...",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceholderScreenPreview() {
    GuiaDefinitivaGTAVTheme {
        // Para la preview, creamos un NavController de prueba que no puede retroceder
        val navController = rememberNavController()
        PlaceholderScreen(navController = navController, screenTitle = "Pantalla de Ejemplo")
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceholderScreenWithBackPreview() {
    GuiaDefinitivaGTAVTheme {
        // Simulamos un NavController que SÍ puede retroceder para ver el icono
        val navController = rememberNavController()
        // Para simular que hay una pantalla anterior, podrías añadir una entrada "falsa"
        // pero para una preview simple, esta condición de if (navController.previousBackStackEntry != null)
        // será false, así que el icono de atrás no se mostrará en esta preview particular
        // sin una configuración de NavHost más compleja para la preview.
        // Lo importante es que en la app real funcionará.
        PlaceholderScreen(navController = navController, screenTitle = "Con Opción de Volver")
    }
}
