package com.juanjojmnz.guiadefinitivagtav.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.juanjojmnz.guiadefinitivagtav.R
import com.juanjojmnz.guiadefinitivagtav.data.model.MainMission
import com.juanjojmnz.guiadefinitivagtav.data.model.MissionCharacter
import com.juanjojmnz.guiadefinitivagtav.data.model.StrangersAndFreaksMission
import com.juanjojmnz.guiadefinitivagtav.navigation.AppDestinations // Importa AppDestinations
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme
import com.juanjojmnz.guiadefinitivagtav.ui.viewmodel.MissionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionListScreen(
    navController: NavController,
    category: String,
    missionViewModel: MissionViewModel = viewModel()
) {
    val selectedCharacter by missionViewModel.selectedCharacter.collectAsState()

    val filteredMainMissions by missionViewModel.filteredMainMissions.collectAsState()
    val filteredStrangersAndFreaksMissions by missionViewModel.filteredStrangersAndFreaksMissions.collectAsState()

    val originalMainMissions by missionViewModel.mainMissions.collectAsState()
    val originalStrangersAndFreaksMissions by missionViewModel.strangersAndFreaksMissions.collectAsState()

    val missionsToShow = when (category) {
        "Misiones principales" -> filteredMainMissions
        "Extraños y locos" -> filteredStrangersAndFreaksMissions
        else -> emptyList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = category, style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            CharacterFilterRow(
                selectedCharacter = selectedCharacter,
                onCharacterSelected = { character ->
                    missionViewModel.selectCharacter(character)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            val noMissionsForFilter = missionsToShow.isEmpty() &&
                    ((category == "Misiones principales" && originalMainMissions.isNotEmpty()) ||
                            (category == "Extraños y locos" && originalStrangersAndFreaksMissions.isNotEmpty()))

            val isLoading = when(category) {
                "Misiones principales" -> originalMainMissions.isEmpty() && missionsToShow.isEmpty()
                "Extraños y locos" -> originalStrangersAndFreaksMissions.isEmpty() && missionsToShow.isEmpty()
                else -> false
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (noMissionsForFilter) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No hay misiones disponibles para el personaje seleccionado en esta categoría.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            } else if (missionsToShow.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No hay misiones disponibles en esta categoría.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
            else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(missionsToShow, key = { mission ->
                        when (mission) {
                            is MainMission -> mission.id
                            is StrangersAndFreaksMission -> mission.id
                            else -> java.util.UUID.randomUUID().toString()
                        }
                    }) { mission ->
                        // Define la acción de clic aquí para pasarla a las tarjetas
                        val missionId = when (mission) {
                            is MainMission -> mission.id
                            is StrangersAndFreaksMission -> mission.id
                            else -> "" // No debería ocurrir
                        }
                        val onClickAction = {
                            if (missionId.isNotEmpty()) {
                                // Construye la ruta de navegación de forma segura
                                val route = AppDestinations.MISSION_DETAIL_SCREEN
                                    .replace("{category}", category)
                                    .replace("{missionId}", missionId)
                                navController.navigate(route)
                            }
                        }

                        when (mission) {
                            is MainMission -> MissionCard(
                                mission = mission,
                                onClick = onClickAction // Pasa la acción de clic
                            )
                            is StrangersAndFreaksMission -> StrangersAndFreaksMissionCard(
                                mission = mission,
                                onClick = onClickAction // Pasa la acción de clic
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterFilterRow(
    selectedCharacter: MissionCharacter?,
    onCharacterSelected: (MissionCharacter?) -> Unit
) {
    val characterUiOptions = listOf(
        null,
        MissionCharacter.MICHAEL,
        MissionCharacter.FRANKLIN,
        MissionCharacter.TREVOR
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        characterUiOptions.forEach { characterOption ->
            val iconRes = when (characterOption) {
                MissionCharacter.MICHAEL -> R.drawable.icon_michael
                MissionCharacter.FRANKLIN -> R.drawable.icon_franklin
                MissionCharacter.TREVOR -> R.drawable.icon_trevor
                MissionCharacter.ALL -> R.drawable.icon_all_players
                null -> R.drawable.icon_all_players
            }
            val characterName = characterOption?.name?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "Todos"

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "Filtrar por $characterName",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = if (selectedCharacter == characterOption) MaterialTheme.colorScheme.primary else Color.Gray,
                            shape = CircleShape
                        )
                        .clickable { onCharacterSelected(characterOption) }
                        .padding(4.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = characterName,
                    fontSize = 12.sp,
                    color = if (selectedCharacter == characterOption) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

// Modificado para aceptar un lambda onClick
@Composable
fun MissionCard(mission: MainMission, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, // <-- HACE LA TARJETA CLICABLE
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = mission.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = mission.description, style = MaterialTheme.typography.bodyMedium, maxLines = 3)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Personaje(s): ${mission.character}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

// Modificado para aceptar un lambda onClick
@Composable
fun StrangersAndFreaksMissionCard(mission: StrangersAndFreaksMission, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, // <-- HACE LA TARJETA CLICABLE
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = mission.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = mission.description, style = MaterialTheme.typography.bodyMedium, maxLines = 3)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Personaje: ${mission.character}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Localización: ${mission.location}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true, name = "Mission List Preview (Misiones Principales)")
@Composable
fun MissionListScreenPreview_Main() {
    GuiaDefinitivaGTAVTheme {
        MissionListScreen(
            navController = rememberNavController(),
            category = "Misiones principales"
        )
    }
}

@Preview(showBackground = true, name = "Mission List Preview (Extraños y locos)")
@Composable
fun MissionListScreenPreview_SnF() {
    GuiaDefinitivaGTAVTheme {
        MissionListScreen(
            navController = rememberNavController(),
            category = "Extraños y locos"
        )
    }
}

@Preview(showBackground = true, name = "Mission List - Simple Preview")
@Composable
fun MissionListScreenSimplePreview() {
    GuiaDefinitivaGTAVTheme {
        MissionListScreen(
            navController = rememberNavController(),
            category = "Misiones principales"
        )
    }
}