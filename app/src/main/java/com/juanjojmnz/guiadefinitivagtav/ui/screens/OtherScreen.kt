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

object OtherActivityRoutes {
    const val LIST = "other_activity_list"
    const val BOUNTY_HUNTER = "other_activity_bounty_hunter"
    const val STORE_ROBBERIES = "other_activity_store_robberies"
    const val TIME_TRIALS = "other_activity_time_trials"
    const val PARACHUTE_JUMPS = "other_activity_parachute_jumps"
    const val KNIFE_FLIGHTS = "other_activity_knife_flights"
}

data class OtherActivityMenuItem(
    val title: String,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherScreen(
    navController: NavController
) {
    val otherNavController: NavHostController = rememberNavController()

    val otherActivityTypes = remember {
        listOf(
            OtherActivityMenuItem("Cazarecompensas", OtherActivityRoutes.BOUNTY_HUNTER),
            OtherActivityMenuItem("Robos en tiendas", OtherActivityRoutes.STORE_ROBBERIES),
            OtherActivityMenuItem("Contrarrelojes", OtherActivityRoutes.TIME_TRIALS),
            OtherActivityMenuItem("Saltos en paracaídas", OtherActivityRoutes.PARACHUTE_JUMPS),
            OtherActivityMenuItem("Vuelos a cuchillo", OtherActivityRoutes.KNIFE_FLIGHTS)
        )
    }

    val navBackStackEntry by otherNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var currentScreenTitle by remember { mutableStateOf("Otras Actividades") }

    LaunchedEffect(currentRoute) {
        currentScreenTitle = when (currentRoute) {
            OtherActivityRoutes.BOUNTY_HUNTER -> "Cazarecompensas"
            OtherActivityRoutes.STORE_ROBBERIES -> "Robos en Tiendas"
            OtherActivityRoutes.TIME_TRIALS -> "Contrarrelojes"
            OtherActivityRoutes.PARACHUTE_JUMPS -> "Saltos en Paracaídas"
            OtherActivityRoutes.KNIFE_FLIGHTS -> "Vuelos a Cuchillo"
            OtherActivityRoutes.LIST -> "Otras Actividades"
            else -> "Otras Actividades"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentScreenTitle) },
                navigationIcon = {
                    if (currentRoute != OtherActivityRoutes.LIST && currentRoute != null) {
                        IconButton(onClick = { otherNavController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                        }
                    } else {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Salir de Otras Actividades")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = otherNavController,
            startDestination = OtherActivityRoutes.LIST,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(OtherActivityRoutes.LIST) {
                OtherActivityTypeList(
                    otherActivityTypes = otherActivityTypes,
                    onOtherActivityTypeClick = { route ->
                        otherNavController.navigate(route)
                    }
                )
            }
            composable(OtherActivityRoutes.BOUNTY_HUNTER) {
                OtherDetailScreen("Cazarecompensas") {}
            }
            composable(OtherActivityRoutes.STORE_ROBBERIES) {
                OtherDetailScreen("Robos en Tiendas") {}
            }
            composable(OtherActivityRoutes.TIME_TRIALS) {
                OtherDetailScreen("Contrarrelojes") { }
            }
            composable(OtherActivityRoutes.PARACHUTE_JUMPS) {
                OtherDetailScreen("Saltos en Paracaídas") {}
            }
            composable(OtherActivityRoutes.KNIFE_FLIGHTS) {
                OtherDetailScreen("Vuelos a Cuchillo") {}
            }
        }
    }
}

@Composable
fun OtherActivityTypeList(
    otherActivityTypes: List<OtherActivityMenuItem>,
    onOtherActivityTypeClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(otherActivityTypes) { activityType ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOtherActivityTypeClick(activityType.route) },
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
fun OtherDetailScreen(
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
        content()
        if (content.toString().contains("Composable() -> Unit")) {
            Text("Próximamente...")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OtherScreenPreview() {
    GuiaDefinitivaGTAVTheme {
        val previewNavController = rememberNavController()
        OtherScreen(navController = previewNavController)
    }
}