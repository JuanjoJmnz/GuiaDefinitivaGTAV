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

object RaceRoutes {
    const val LIST = "race_list"
    const val CAR_RACES = "race_car"
    const val MOTORCYCLE_RACES = "race_motorcycle"
    const val BOAT_RACES = "race_boat"
}

data class RaceMenuItem(
    val title: String,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaceScreen(
    navController: NavController
) {
    val raceNavController: NavHostController = rememberNavController()

    val raceTypes = remember {
        listOf(
            RaceMenuItem("Carreras de Coches", RaceRoutes.CAR_RACES),
            RaceMenuItem("Carreras de Motos", RaceRoutes.MOTORCYCLE_RACES),
            RaceMenuItem("Carreras de Barcos", RaceRoutes.BOAT_RACES)
        )
    }

    val navBackStackEntry by raceNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var currentScreenTitle by remember { mutableStateOf("Carreras") }

    LaunchedEffect(currentRoute) {
        currentScreenTitle = when (currentRoute) {
            RaceRoutes.CAR_RACES -> "Carreras de Coches"
            RaceRoutes.MOTORCYCLE_RACES -> "Carreras de Motos"
            RaceRoutes.BOAT_RACES -> "Carreras de Barcos"
            RaceRoutes.LIST -> "Carreras"
            else -> "Carreras"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentScreenTitle) },
                navigationIcon = {
                    if (currentRoute != RaceRoutes.LIST && currentRoute != null) {
                        IconButton(onClick = { raceNavController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                        }
                    } else {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Salir de Carreras")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = raceNavController,
            startDestination = RaceRoutes.LIST,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(RaceRoutes.LIST) {
                RaceTypeList(
                    raceTypes = raceTypes,
                    onRaceTypeClick = { route ->
                        raceNavController.navigate(route)
                    }
                )
            }
            composable(RaceRoutes.CAR_RACES) {
                RaceDetailScreen("Carreras de Coches") {}
            }
            composable(RaceRoutes.MOTORCYCLE_RACES) {
                RaceDetailScreen("Carreras de Motos") {}
            }
            composable(RaceRoutes.BOAT_RACES) {
                RaceDetailScreen("Carreras de Barcos") {}
            }
        }
    }
}

@Composable
fun RaceTypeList(
    raceTypes: List<RaceMenuItem>,
    onRaceTypeClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(raceTypes) { raceType ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onRaceTypeClick(raceType.route) },
            ) {
                Text(
                    text = raceType.title,
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
fun RaceDetailScreen(
    raceTypeName: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Detalles de: $raceTypeName", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        content()
        if (content.toString().contains("Composable() -> Unit")) {
            Text("Próximamente...")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RaceScreenPreview() {
    GuiaDefinitivaGTAVTheme {
        val previewNavController = rememberNavController()
        RaceScreen(navController = previewNavController)
    }
}