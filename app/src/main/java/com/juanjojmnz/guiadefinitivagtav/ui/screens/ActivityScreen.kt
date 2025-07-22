package com.juanjojmnz.guiadefinitivagtav.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme

object ActivityRoutes {
    const val LIST = "activity_list"
    const val GOLF = "activity_golf"
    const val YOGA = "activity_yoga"
    const val STRIP_CLUB = "activity_strip_club"
    const val TENNIS = "activity_tennis"
    const val TRIATHLONS = "activity_triathlons"
    const val DARTS = "activity_darts"
    const val CINEMA = "activity_cinema"
}

data class ActivityMenuItem(
    val title: String,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    navController: NavController, screenTitle: String
) {
    val activityNavController: NavHostController = rememberNavController()

    val activityTypes = remember {
        listOf(
            ActivityMenuItem("Golf", ActivityRoutes.GOLF),
            ActivityMenuItem("Yoga", ActivityRoutes.YOGA),
            ActivityMenuItem("Stripclub", ActivityRoutes.STRIP_CLUB),
            ActivityMenuItem("Tenis", ActivityRoutes.TENNIS),
            ActivityMenuItem("Triatlones", ActivityRoutes.TRIATHLONS),
            ActivityMenuItem("Dardos", ActivityRoutes.DARTS),
            ActivityMenuItem("Cine", ActivityRoutes.CINEMA)
        )
    }

    val navBackStackEntry by activityNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var currentScreenTitle by remember { mutableStateOf("Actividades") }

    LaunchedEffect(currentRoute) {
        currentScreenTitle = when (currentRoute) {
            ActivityRoutes.GOLF -> "Golf"
            ActivityRoutes.YOGA -> "Yoga"
            ActivityRoutes.STRIP_CLUB -> "Stripclub"
            ActivityRoutes.TENNIS -> "Tenis"
            ActivityRoutes.TRIATHLONS -> "Triatlones"
            ActivityRoutes.DARTS -> "Dardos"
            ActivityRoutes.CINEMA -> "Cine"
            ActivityRoutes.LIST -> "Actividades"
            else -> "Actividades"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentScreenTitle) },
                navigationIcon = {
                    if (currentRoute != ActivityRoutes.LIST && currentRoute != null) {
                        IconButton(onClick = { activityNavController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                        }
                    } else {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Salir de Actividades")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = activityNavController,
            startDestination = ActivityRoutes.LIST,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(ActivityRoutes.LIST) {
                ActivityTypeList(
                    activityTypes = activityTypes,
                    onActivityTypeClick = { route ->
                        activityNavController.navigate(route)
                    }
                )
            }
            composable(ActivityRoutes.GOLF) {
                ActivityDetailScreen("Golf") {}
            }
            composable(ActivityRoutes.YOGA) {
                ActivityDetailScreen("Yoga") {}
            }
            composable(ActivityRoutes.STRIP_CLUB) {
                ActivityDetailScreen("Stripclub") {}
            }
            composable(ActivityRoutes.TENNIS) {
                ActivityDetailScreen("Tenis") {}
            }
            composable(ActivityRoutes.TRIATHLONS) {
                ActivityDetailScreen("Triatlones") {}
            }
            composable(ActivityRoutes.DARTS) {
                ActivityDetailScreen("Dardos") {}
            }
            composable(ActivityRoutes.CINEMA) {
                ActivityDetailScreen("Cine") {}
            }
        }
    }
}

@Composable
fun ActivityTypeList(
    activityTypes: List<ActivityMenuItem>,
    onActivityTypeClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(activityTypes) { activityType ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onActivityTypeClick(activityType.route) },
            ) {
                Text(
                    text = activityType.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ActivityDetailScreen(
    activityName: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Detalles de: $activityName", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        if (content.toString().contains("Composable() -> Unit")) {
            Text("Próximamente...")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityScreenPreview() {
    GuiaDefinitivaGTAVTheme {
        val previewNavController = rememberNavController()
        ActivityScreen(navController = previewNavController, screenTitle = "Pantalla de Actividades")
    }
}

