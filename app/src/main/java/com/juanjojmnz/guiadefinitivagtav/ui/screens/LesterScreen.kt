package com.juanjojmnz.guiadefinitivagtav.ui.screens

import android.app.Application
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.IOException

@Serializable
data class StockInvestmentInfo(
    val companyName: String,
    val tickerSymbol: String,
    val action: String,
    val details: String? = null
)

@Serializable
data class AssassinationMissionInfo(
    val id: String,
    val missionName: String,
    val unlockedByCharacter: String = "Franklin",
    val availableAfterMission: String,
    val targetCompanyDown: StockInvestmentInfo,
    val companyToInvestInBefore: StockInvestmentInfo?,
    val generalTips: List<String>? = null,
    val postMissionInvestmentTips: List<StockInvestmentInfo>? = null
)

class LesterMissionsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LesterMissionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LesterMissionsViewModel(application.applicationContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class for LesterMissionsViewModelFactory")
    }
}

class LesterMissionsViewModel(private val context: Context) : ViewModel() {
    private val _missions = MutableStateFlow<List<AssassinationMissionInfo>>(emptyList())
    val missions: StateFlow<List<AssassinationMissionInfo>> = _missions

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val jsonParser = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    init {
        loadMissions()
    }

    fun loadMissions() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val jsonString = context.assets.open("lester_assassination_missions.json")
                    .bufferedReader()
                    .use { it.readText() }

                val missionList = jsonParser.decodeFromString<List<AssassinationMissionInfo>>(jsonString)
                _missions.value = missionList
            } catch (e: IOException) {
                _error.value = "Error al leer el archivo de misiones: ${e.message}"
                e.printStackTrace()
            } catch (e: kotlinx.serialization.SerializationException) {
                _error.value = "Error al parsear los datos de las misiones: ${e.message}"
                e.printStackTrace()
            } catch (e: Exception) {
                _error.value = "Un error inesperado ocurrió: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LesterScreen(
    navController: NavController,
    screenTitle: String = "Misiones de Asesinato (Lester)"
) {
    val context = LocalContext.current
    val viewModel: LesterMissionsViewModel = viewModel(
        factory = LesterMissionsViewModelFactory(context.applicationContext as Application)
    )

    val missions by viewModel.missions.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error al cargar misiones:\n$error",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            text = "Estas misiones son cruciales para ganar mucho dinero en el mercado de valores. ¡Sigue las instrucciones con cuidado!",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "CONSEJO GENERAL: Es altamente recomendable completar la historia principal ANTES de hacer la mayoría de estas misiones (excepto la primera, que es obligatoria para avanzar) para maximizar las ganancias con los 3 personajes.",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    items(missions, key = { it.id }) { mission ->
                        MissionInfoCard(mission = mission)
                    }
                }
            }
        }
    }
}

@Composable
fun MissionInfoCard(mission: AssassinationMissionInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = mission.missionName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            InfoRow(icon = Icons.Filled.Person, label = "Personaje", value = mission.unlockedByCharacter)
            InfoRow(icon = Icons.Filled.Warning, label = "Disponible después de", value = mission.availableAfterMission)

            Spacer(modifier = Modifier.height(12.dp))
            Text("Estrategia de Inversión:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 6.dp))

            mission.companyToInvestInBefore?.let { investment ->
                if (investment.companyName.isNotBlank() && !investment.action.contains("NO invertir", ignoreCase = true)) {
                    InvestmentDetailView(investment = investment, isPositiveGain = true, prefix = "Invertir ANTES de la misión:")
                } else if (investment.action.contains("NO invertir", ignoreCase = true)) {
                    Row(modifier = Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Warning,
                            contentDescription = "No invertir",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = investment.action,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    investment.details?.let {
                        Text(
                            text = "Razón: $it",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 28.dp)
                        )
                    }
                }
            }

            InvestmentDetailView(investment = mission.targetCompanyDown, isPositiveGain = false, prefix = "Empresa Objetivo (acciones BAJARÁN):")

            mission.postMissionInvestmentTips?.forEach { tip ->
                InvestmentDetailView(investment = tip, isPositiveGain = true, prefix = "Inversión Post-Misión:")
            }

            mission.generalTips?.let { tips ->
                Spacer(modifier = Modifier.height(10.dp))
                Text("Consejos Adicionales:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
                tips.forEach { tip ->
                    Row(modifier = Modifier.padding(start = 8.dp, bottom = 4.dp), verticalAlignment = Alignment.Top) {
                        Icon(Icons.Filled.Info, contentDescription = "Tip", modifier = Modifier.size(18.dp).padding(end = 6.dp), tint = MaterialTheme.colorScheme.secondary)
                        Text(tip, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun InvestmentDetailView(
    investment: StockInvestmentInfo,
    isPositiveGain: Boolean,
    prefix: String? = null
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        prefix?.let {
            Text(it, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))
        }
        Row(verticalAlignment = Alignment.Top) {
            Icon(
                imageVector = Icons.Filled.ThumbUp,
                contentDescription = "Investment action",
                tint = if (isPositiveGain) Color(0xFF006400) else MaterialTheme.colorScheme.error,
                modifier = Modifier.size(20.dp).padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "${investment.companyName} (${investment.tickerSymbol})",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = investment.action,
                    style = MaterialTheme.typography.bodyMedium
                )
                investment.details?.let {
                    Text(
                        text = "Detalle: $it",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = label, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.secondary)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium
        )
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true, name = "Lester Screen (Loading State)")
@Composable
fun LesterScreenPreview_Loading() {
    GuiaDefinitivaGTAVTheme {
        LesterScreen(navController = rememberNavController())
    }
}

