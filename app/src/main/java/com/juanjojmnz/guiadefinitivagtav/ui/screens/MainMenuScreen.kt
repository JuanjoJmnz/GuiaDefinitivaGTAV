package com.juanjojmnz.guiadefinitivagtav.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.juanjojmnz.guiadefinitivagtav.R
import com.juanjojmnz.guiadefinitivagtav.navigation.AppDestinations
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val NAVIGATION_DEBOUNCE_DELAY_MS = 500L

data class MenuItemApp(
    val title: String,
    @DrawableRes val iconDrawableId: Int? = null,
    val route: String? = null,
    val category: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(navController: NavController) {
    val menuItems = listOf(
        MenuItemApp(
            title = "100%",
            iconDrawableId = R.drawable.icono_cien_por_cien,
            route = AppDestinations.HUNDRED_PERCENT_SCREEN
        ),
        MenuItemApp(
            title = "Misiones Principales",
            iconDrawableId = R.drawable.icono_misiones_principales,
            route = AppDestinations.MISSION_LIST_SCREEN,
            category = "Misiones principales"
        ),
        MenuItemApp(
            title = "Extraños y Locos",
            iconDrawableId = R.drawable.icono_extranos_locos,
            route = AppDestinations.MISSION_LIST_SCREEN,
            category = "Extraños y locos"
        ),
        MenuItemApp(
            title = "Golpes",
            iconDrawableId = R.drawable.icono_golpes,
            route = AppDestinations.HEISTS_SCREEN
        ),
        MenuItemApp(
            title = "Asesinatos de Lester",
            iconDrawableId = R.drawable.icono_asesinatos_lester,
            route = AppDestinations.LESTER_ASSASSINATIONS_SCREEN
        ),
        MenuItemApp(
            title = "Coleccionables",
            iconDrawableId = R.drawable.icono_coleccionables,
            route = AppDestinations.COLLECTIBLES_SCREEN
        ),
        MenuItemApp(
            title = "Actividades",
            iconDrawableId = R.drawable.icono_actividades,
            route = AppDestinations.ACTIVITIES_SCREEN
        ),
        MenuItemApp(
            title = "Otras actividades",
            iconDrawableId = R.drawable.icono_otras_actividades,
            route = AppDestinations.OTHER_ACTIVITIES_SCREEN
        ),
        MenuItemApp(
            title = "Carreras",
            iconDrawableId = R.drawable.icono_carreras,
            route = AppDestinations.RACES_SCREEN
        ),
        MenuItemApp(
            title = "Eventos aleatorios",
            iconDrawableId = R.drawable.icono_eventos_aleatorios,
            route = AppDestinations.RANDOM_EVENTS_SCREEN
        ),
        MenuItemApp(
            title = "Propiedades",
            iconDrawableId = R.drawable.icono_propiedades,
            route = AppDestinations.PROPERTIES_SCREEN
        ),
        MenuItemApp(
            title = "Mapa Interactivo",
            iconDrawableId = R.drawable.icono_mapa,
            route = AppDestinations.INTERACTIVE_MAP_SCREEN
        ),
        MenuItemApp(
            title = "Curiosidades",
            iconDrawableId = R.drawable.icono_curiosidades,
            route = AppDestinations.TRIVIA_SCREEN
        ),
        MenuItemApp(
            title = "Trucos",
            iconDrawableId = R.drawable.icono_trucos,
            route = AppDestinations.CHEATS_SCREEN
        ),
        MenuItemApp(
            title = "Online",
            iconDrawableId = R.drawable.icon_all_players,
            route = AppDestinations.ONLINE_SCREEN
        ),
        MenuItemApp(
            title = "Información de la app",
            iconDrawableId = R.drawable.icono_informacion,
            route = AppDestinations.INFO_SCREEN
        )
    )

    val isNavigating = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menú Principal") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(all = 25.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(menuItems) { menuItem ->
                AppIconCard(
                    navController = navController,
                    item = menuItem,
                    isNavigating = isNavigating
                )
            }
        }
    }
}

@Composable
fun AppIconCard(
    navController: NavController,
    item: MenuItemApp,
    isNavigating: MutableState<Boolean>
) {
    val coroutineScope = rememberCoroutineScope()

    ElevatedCard(
        onClick = {
            if (!isNavigating.value) {
                isNavigating.value = true
                coroutineScope.launch {
                    val finalRoute = if (item.category != null) {
                        item.route?.replace("{category}", item.category)
                    } else {
                        item.route
                    }

                    finalRoute?.let {
                        navController.navigate(it) {
                            launchSingleTop = true
                        }
                    }

                    delay(NAVIGATION_DEBOUNCE_DELAY_MS)
                    isNavigating.value = false
                }
            }
        },
        enabled = !isNavigating.value,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.Transparent),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item.iconDrawableId?.let { drawableId ->
                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = item.title,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(width = 1.dp, color = Color.White, shape = CircleShape),
                    contentScale = ContentScale.Fit,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true, device = "spec:width=360dp,height=640dp,dpi=480")
@Composable
fun MainMenuScreenPhonePreview() {
    GuiaDefinitivaGTAVTheme {
        MainMenuScreen(navController = rememberNavController())
    }
}