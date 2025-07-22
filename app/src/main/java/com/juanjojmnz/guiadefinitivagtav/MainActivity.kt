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
import com.juanjojmnz.guiadefinitivagtav.navigation.AppDestinations
import com.juanjojmnz.guiadefinitivagtav.ui.screens.ActivityScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.CompletedScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.MainMenuScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.MissionDetailScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.MissionListScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.WelcomeScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.PlaceholderScreen
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme
import com.juanjojmnz.guiadefinitivagtav.ui.screens.CheatsScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.CollectionScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.InfoScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.LesterScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.OtherScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.RaceScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.RandomScreen

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
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppDestinations.WELCOME_SCREEN) {
                WelcomeScreen(
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

            composable(AppDestinations.HEISTS_SCREEN) {
                PlaceholderScreen(navController = navController, screenTitle = "Golpes")
            }
            composable(AppDestinations.COLLECTIBLES_SCREEN) {
                CollectionScreen(mainNavController = navController)
            }
            composable(AppDestinations.ACTIVITIES_SCREEN) {
                ActivityScreen(navController = navController, screenTitle = "Actividades")
            }
            composable(AppDestinations.OTHER_ACTIVITIES_SCREEN) {
                OtherScreen(navController = navController)
            }
            composable(AppDestinations.RACES_SCREEN) {
                RaceScreen(navController = navController)
            }
            composable(AppDestinations.LESTER_ASSASSINATIONS_SCREEN) {
                LesterScreen(navController = navController, screenTitle = "Asesinatos de Lester")
            }
            composable(AppDestinations.RANDOM_EVENTS_SCREEN) {
                RandomScreen(mainNavController = navController)
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
                CheatsScreen(navController = navController, screenTitle = "Trucos")
            }
            composable(AppDestinations.ONLINE_SCREEN) {
                PlaceholderScreen(navController = navController, screenTitle = "Online")
            }
            composable(AppDestinations.INFO_SCREEN) {
                InfoScreen(navController = navController, screenTitle = "Información de la app")
            }
        }
    }
}