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
import com.juanjojmnz.guiadefinitivagtav.ui.screens.collection.LetterScrapsScreen
import com.juanjojmnz.guiadefinitivagtav.ui.screens.collection.SpaceshipPartsScreen
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme


object CollectionRoutes {
    const val LIST = "collection_list"
    const val SPACESHIP_PARTS = "collection_spaceship_parts"
    const val LETTER_SCRAPS = "collection_letter_scraps"
    const val NUCLEAR_WASTE = "collection_nuclear_waste"
    const val SUBMARINE_PARTS = "collection_submarine_parts"
    const val MONKEY_MOSAICS = "collection_monkey_mosaics"
    const val EPSILON_TRACTS = "collection_epsilon_tracts"
    const val PEYOTE_PLANTS = "collection_peyote_plants"
}

data class CollectibleMenuItem(
    val title: String,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(
    mainNavController: NavController
) {
    val collectionNavController: NavHostController = rememberNavController()
    val collectibleTypes = remember {
        listOf(
            CollectibleMenuItem("Piezas de la Nave Espacial", CollectionRoutes.SPACESHIP_PARTS),
            CollectibleMenuItem("Fragmentos de Cartas", CollectionRoutes.LETTER_SCRAPS),
            CollectibleMenuItem("Desperdicios Nucleares", CollectionRoutes.NUCLEAR_WASTE),
            CollectibleMenuItem("Piezas de Submarino", CollectionRoutes.SUBMARINE_PARTS),
            CollectibleMenuItem("Mosaicos de Mono", CollectionRoutes.MONKEY_MOSAICS),
            CollectibleMenuItem("Tratados de Épsilon", CollectionRoutes.EPSILON_TRACTS),
            CollectibleMenuItem("Plantas de Peyote", CollectionRoutes.PEYOTE_PLANTS)
        )
    }

    val navBackStackEntry by collectionNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var currentTitle by remember { mutableStateOf("Coleccionables") }

    LaunchedEffect(currentRoute) {
        currentTitle = when (currentRoute) {
            CollectionRoutes.SPACESHIP_PARTS -> "Piezas de la nave"
            CollectionRoutes.LETTER_SCRAPS -> "Fragmentos de Cartas"
            CollectionRoutes.NUCLEAR_WASTE -> "Desperdicios Nucleares"
            CollectionRoutes.SUBMARINE_PARTS -> "Piezas de Submarino"
            CollectionRoutes.MONKEY_MOSAICS -> "Mosaicos de Mono"
            CollectionRoutes.EPSILON_TRACTS -> "Tratados de Épsilon"
            CollectionRoutes.PEYOTE_PLANTS -> "Plantas de Peyote"
            CollectionRoutes.LIST -> "Coleccionables"
            else -> "Coleccionables"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentTitle) },
                navigationIcon = {
                    if (currentRoute != CollectionRoutes.LIST && currentRoute != null) {
                        IconButton(onClick = { collectionNavController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                        }
                    } else {
                        IconButton(onClick = { mainNavController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Salir de Coleccionables")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = collectionNavController,
            startDestination = CollectionRoutes.LIST,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(CollectionRoutes.LIST) {
                CollectibleTypeList(
                    collectibleTypes = collectibleTypes,
                    onCollectibleTypeClick = { route ->
                        collectionNavController.navigate(route)
                    }
                )
            }
            composable(CollectionRoutes.SPACESHIP_PARTS) {
                SpaceshipPartsScreen()
            }
            composable(CollectionRoutes.LETTER_SCRAPS) {
                LetterScrapsScreen()
            }
            composable(CollectionRoutes.NUCLEAR_WASTE) {
                CollectibleDetailScreen("Desperdicios Nucleares") {  }
            }
            composable(CollectionRoutes.SUBMARINE_PARTS) {
                CollectibleDetailScreen("Piezas de Submarino") {  }
            }
            composable(CollectionRoutes.MONKEY_MOSAICS) {
                CollectibleDetailScreen("Mosaicos de Mono") {  }
            }
            composable(CollectionRoutes.EPSILON_TRACTS) {
                CollectibleDetailScreen("Tratados de Épsilon") {  }
            }
            composable(CollectionRoutes.PEYOTE_PLANTS) {
                CollectibleDetailScreen("Plantas de Peyote") {  }
            }
        }
    }
}

@Composable
fun CollectibleTypeList(
    collectibleTypes: List<CollectibleMenuItem>,
    onCollectibleTypeClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(collectibleTypes) { collectibleType ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCollectibleTypeClick(collectibleType.route) },
            ) {
                Text(
                    text = collectibleType.title,
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
fun CollectibleDetailScreen(
    collectibleName: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
         Text("Detalles de: $collectibleName", style = MaterialTheme.typography.headlineMedium)
         Spacer(modifier = Modifier.height(16.dp))
         Text("Próximamente...")
    }
}

@Preview(showBackground = true)
@Composable
fun CollectionScreenPreview() {
    GuiaDefinitivaGTAVTheme {
        val previewNavController = rememberNavController()
        CollectionScreen(mainNavController = previewNavController)
    }
}