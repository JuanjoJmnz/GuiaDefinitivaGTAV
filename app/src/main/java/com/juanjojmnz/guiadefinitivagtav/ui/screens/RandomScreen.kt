package com.juanjojmnz.guiadefinitivagtav.ui.screens

import android.app.Application
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.juanjojmnz.guiadefinitivagtav.data.model.RandomEvent
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme
import com.juanjojmnz.guiadefinitivagtav.ui.viewmodel.RandomEventsViewModel

object RandomEventRoutes {
    const val LIST = "random_event_list"
    const val DETAIL = "random_event_detail/{eventId}"

    fun detailRoute(eventId: Int): String {
        return "random_event_detail/$eventId"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomScreen(
    mainNavController: NavController,
    randomEventsViewModel: RandomEventsViewModel = viewModel(
        factory = RandomEventsViewModelFactory(LocalContext.current.applicationContext as Application)
    )
) {
    val randomEventsNavController: NavHostController = rememberNavController()
    val eventsState by randomEventsViewModel.events.collectAsState()
    val isLoading by randomEventsViewModel.isLoading.collectAsState()
    val error by randomEventsViewModel.error.collectAsState()

    val navBackStackEntry by randomEventsNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var currentScreenTitle by remember { mutableStateOf("Eventos Aleatorios") }

    LaunchedEffect(currentRoute, eventsState) {
        currentScreenTitle = if (currentRoute?.startsWith("random_event_detail/") == true) {
            val eventIdString = navBackStackEntry?.arguments?.getString("eventId")
            val eventId = eventIdString?.toIntOrNull()
            val event = eventId?.let { randomEventsViewModel.getEventById(it) }
            event?.name ?: "Detalle del Evento"
        } else {
            "Eventos Aleatorios"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentScreenTitle) },
                navigationIcon = {
                    if (currentRoute != RandomEventRoutes.LIST && currentRoute != null) {
                        IconButton(onClick = { randomEventsNavController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                        }
                    } else {
                        IconButton(onClick = { mainNavController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Salir de Eventos Aleatorios")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = randomEventsNavController,
            startDestination = RandomEventRoutes.LIST,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(RandomEventRoutes.LIST) {
                when {
                    isLoading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    error != null -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Error: $error", color = MaterialTheme.colorScheme.error)
                        }
                    }
                    else -> {
                        RandomEventList(
                            events = eventsState,
                            onEventClick = { eventId ->
                                randomEventsNavController.navigate(RandomEventRoutes.detailRoute(eventId))
                            }
                        )
                    }
                }
            }
            composable(
                route = RandomEventRoutes.DETAIL,
                arguments = listOf(navArgument("eventId") { type = NavType.IntType })
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getInt("eventId")
                val event = eventId?.let { randomEventsViewModel.getEventById(it) }

                if (event != null) {
                    RandomEventDetail(event = event)
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Evento no encontrado.")
                    }
                }
            }
        }
    }
}

@Composable
fun RandomEventList(
    events: List<RandomEvent>,
    onEventClick: (Int) -> Unit
) {
    if (events.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No hay eventos aleatorios para mostrar.")
        }
        return
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(events, key = { it.id }) { event ->
            RandomEventItem(event = event, onClick = { onEventClick(event.id) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomEventItem(
    event: RandomEvent,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${event.id}. ${event.name}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Recompensa: ${event.reward}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun RandomEventDetail(event: RandomEvent) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "${event.id}. ${event.name}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Recompensa:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = event.reward,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Descripción:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

class RandomEventsViewModelFactory(private val application: Application) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RandomEventsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RandomEventsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


@Preview(showBackground = true, name = "Random Event Detail")
@Composable
fun RandomEventDetailPreview() {
    GuiaDefinitivaGTAVTheme {
        RandomEventDetail(event = RandomEvent(1, "Robo en Cajeros", "\$500", "Descripción detallada del evento de robo en cajeros donde debes perseguir al ladrón..."))
    }
}