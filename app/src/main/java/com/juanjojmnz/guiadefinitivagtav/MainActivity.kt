package com.juanjojmnz.guiadefinitivagtav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.juanjojmnz.guiadefinitivagtav.navigation.AppDestinations // Importa tus destinos
import com.juanjojmnz.guiadefinitivagtav.ui.screens.CompletedScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.MainMenuScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.MissionDetailScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.MissionListScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.WelcomeScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.PlaceholderScreen // Asegúrate de importar PlaceholderScreen
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GuiaDefinitivaGTAVTheme {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestinations.WELCOME_SCREEN,
            modifier = Modifier.padding(innerPadding) // Aplica el padding global del Scaffold
        ) {
            composable(AppDestinations.WELCOME_SCREEN) {
                WelcomeScreen( // Pasando navController a WelcomeScreen
                    onEnterClicked = {
                        navController.navigate(AppDestinations.MAIN_MENU_SCREEN) {
                            popUpTo(AppDestinations.WELCOME_SCREEN) { inclusive = true }
                        }
                    }
                )
            }
            composable(AppDestinations.MAIN_MENU_SCREEN) {
                MainMenuScreen(navController = navController)
            }
            composable(AppDestinations.HUNDRED_PERCENT_SCREEN) {
                CompletedScreen(navController = navController, screenTitle = "100%")
            }
            composable(
                route = AppDestinations.MISSION_LIST_SCREEN,
                arguments = listOf(navArgument("category") { type = NavType.StringType })
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category")
                if (category != null) {
                    MissionListScreen(
                        navController = navController,
                        category = category
                    )
                } else {
                    Text(
                        text = "Error: Categoría no especificada.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            composable(
                route = AppDestinations.MISSION_DETAIL_SCREEN,
                arguments = listOf(
                    navArgument("category") { type = NavType.StringType },
                    navArgument("missionId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category")
                val missionId = backStackEntry.arguments?.getString("missionId")

                if (category != null && missionId != null) {
                    MissionDetailScreen(
                        navController = navController,
                        missionId = missionId,
                        category = category
                    )
                } else {
                    Text(
                        text = "Error: Falta información para mostrar detalles de la misión.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // --- NUEVAS RUTAS AÑADIDAS ---
            composable(AppDestinations.HEISTS_SCREEN) {
                PlaceholderScreen(navController = navController, screenTitle = "Golpes")
            }
            composable(AppDestinations.COLLECTIBLES_SCREEN) {
                PlaceholderScreen(navController = navController, screenTitle = "Coleccionables")
            }
            composable(AppDestinations.ACTIVITIES_SCREEN) {
                PlaceholderScreen(navController = navController, screenTitle = "Actividades")
            }
            composable(AppDestinations.OTHER_ACTIVITIES_SCREEN) {
                PlaceholderScreen(navController = navController, screenTitle = "Otras Actividades")
            }
            composable(AppDestinations.RACES_SCREEN) {
                PlaceholderScreen(navController = navController, screenTitle = "Carreras")
            }
            composable(AppDestinations.LESTER_ASSASSINATIONS_SCREEN) {
                PlaceholderScreen(navController = navController, screenTitle = "Asesinatos de Lester")
            }
            composable(AppDestinations.RANDOM_EVENTS_SCREEN) {
                PlaceholderScreen(navController = navController, screenTitle = "Eventos Aleatorios")
            }
            composable(AppDestinations.PROPERTIES_SCREEN) {
                PlaceholderScreen(navController = navController, screenTitle = "Propiedades")
            }
            composable(AppDestinations.INTERACTIVE_MAP_SCREEN) {
                PlaceholderScreen(navController = navController, screenTitle = "Mapa Interactivo")
            }
            composable(AppDestinations.TRIVIA_SCREEN) {
                PlaceholderScreen(navController = navController, screenTitle = "Curiosidades")
            }
            composable(AppDestinations.CHEATS_SCREEN) {
                PlaceholderScreen(navController = navController, screenTitle = "Trucos")
            }
        }
    }
}