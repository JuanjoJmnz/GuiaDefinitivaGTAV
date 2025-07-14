package com.juanjojmnz.guiadefinitivagtav.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.juanjojmnz.guiadefinitivagtav.data.model.MainMission
import com.juanjojmnz.guiadefinitivagtav.data.model.StrangersAndFreaksMission
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme
import com.juanjojmnz.guiadefinitivagtav.ui.viewmodel.MissionViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionDetailScreen(
    navController: NavController,
    missionId: String,
    category: String,
    missionViewModel: MissionViewModel = viewModel()
) {
    val mainMissions by missionViewModel.mainMissions.collectAsState()
    val strangersAndFreaksMissions by missionViewModel.strangersAndFreaksMissions.collectAsState()

    var missionDetails: Any? = null

    if (category == "Misiones principales") {
        missionDetails = mainMissions.find { it.id == missionId }
    } else if (category == "Extraños y locos") {
        missionDetails = strangersAndFreaksMissions.find { it.id == missionId }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val titleText = when (missionDetails) {
                        is MainMission -> missionDetails.name
                        is StrangersAndFreaksMission -> missionDetails.name
                        else -> "Detalle de Misión"
                    }
                    Text(text = titleText)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (missionDetails != null) {
                when (missionDetails) {
                    is MainMission -> MainMissionDetailsContent(mission = missionDetails)
                    is StrangersAndFreaksMission -> StrangersAndFreaksMissionDetailsContent(mission = missionDetails)
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Misión no encontrada o cargando detalles...")
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun MainMissionDetailsContent(mission: MainMission) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = mission.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = DividerDefaults.Thickness,
            color = DividerDefaults.color
        )
        Text(text = "Descripción:", style = MaterialTheme.typography.titleMedium)
        Text(text = mission.description, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Personaje(s): ${mission.character}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))


        if (mission.goldMedalRequirements.isNotEmpty()) {
            Text(text = "Requisitos para Medalla de Oro:", style = MaterialTheme.typography.titleMedium)
            mission.goldMedalRequirements.forEach { requirement ->
                Text(text = "• $requirement", style = MaterialTheme.typography.bodyMedium)
            }
        }
        if (mission.reward != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Recompensa: ${mission.reward}", style = MaterialTheme.typography.bodyMedium)
        }
        if (mission.notes != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Notas: ${mission.notes}", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Se desbloquea en: ${mission.unlocksAtEvent}", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun StrangersAndFreaksMissionDetailsContent(mission: StrangersAndFreaksMission) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = mission.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = DividerDefaults.Thickness,
            color = DividerDefaults.color
        )
        Text(text = "Descripción:", style = MaterialTheme.typography.titleMedium)
        Text(text = mission.description, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Personaje: ${mission.character}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Interpretado por: ${mission.characterEncountered}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Localización: ${mission.location}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Disponibilidad: ${mission.availability}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))


        if (mission.goldMedalRequirements.isNotEmpty()) {
            Text(text = "Requisitos para Medalla de Oro:", style = MaterialTheme.typography.titleMedium)
            mission.goldMedalRequirements.forEach { requirement ->
                Text(text = "• $requirement", style = MaterialTheme.typography.bodyMedium)
            }
        }
        if (mission.reward != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Recompensa: ${mission.reward}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}


@Preview(showBackground = true, name = "Main Mission Detail Preview")
@Composable
fun MainMissionDetailPreview() {
    GuiaDefinitivaGTAVTheme {
        val navController = rememberNavController()
        val sampleMainMission = MainMission(
            id = "main_01",
            name = "Prólogo",
            description = "Una breve introducción a los eventos que desencadenan la historia principal.",
            character = "Michael, Trevor",
            reward = "$200,000 (valor de botín)",
            goldMedalRequirements = listOf(
                "Completa en menos de 5:00",
                "Logra 10 disparos a la cabeza"
            ),
            unlocksAtEvent = "Después de la misión 'Secuestro'",
            notes = "Esta misión sirve como tutorial para los atracos."
        )

        val mockViewModel: MissionViewModel =
            object : MissionViewModel(application = androidx.test.core.app.ApplicationProvider.getApplicationContext()) {
                override val mainMissions: StateFlow<List<MainMission>> =
                    MutableStateFlow(listOf(sampleMainMission))
                override val strangersAndFreaksMissions: StateFlow<List<StrangersAndFreaksMission>> =
                    MutableStateFlow(emptyList())
            }

        MissionDetailScreen(
            navController = navController,
            missionId = "main_01",
            category = "Misiones principales",
            missionViewModel = mockViewModel
        )
    }
}

@Preview(showBackground = true, name = "S&F Mission Detail Preview")
@Composable
fun StrangersAndFreaksMissionDetailPreview() {
    GuiaDefinitivaGTAVTheme {
        val navController = rememberNavController()
        val sampleSnFMission = StrangersAndFreaksMission(
            id = "snf_franklin_01",
            name = "Sacar la grúa",
            description = "Ayuda a Tonya con sus problemas de remolque.",
            character = "Franklin",
            characterEncountered = "Tonya Wiggins",
            reward = "Dinero y respeto",
            availability = "Después de 'Reposesión'",
            location = "Strawberry, cerca del taller de Franklin",
            goldMedalRequirements = listOf(
                "Completa en menos de 2:30",
                "No dañes el vehículo remolcado"
            )
        )
        val mockViewModelSnF: MissionViewModel =
            object : MissionViewModel(application = androidx.test.core.app.ApplicationProvider.getApplicationContext()) {
                override val strangersAndFreaksMissions: StateFlow<List<StrangersAndFreaksMission>> =
                    MutableStateFlow(listOf(sampleSnFMission))
                override val mainMissions: StateFlow<List<MainMission>> =
                    MutableStateFlow(emptyList())
            }

        MissionDetailScreen(
            navController = navController,
            missionId = "snf_franklin_01",
            category = "Extraños y locos",
            missionViewModel = mockViewModelSnF
        )
    }
}