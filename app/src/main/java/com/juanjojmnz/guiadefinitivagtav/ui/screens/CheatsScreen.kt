package com.juanjojmnz.guiadefinitivagtav.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class TrucoInfo(
    val id: String,
    val efecto: String,
    val comandoConsola: String? = null,
    val codigoTelefono: String? = null
)

sealed interface TrucoListItem {
    data class TrucoEntry(val info: TrucoInfo) : TrucoListItem
    data class SubHeaderText(val text: String) : TrucoListItem
}

data class TrucosCategory(
    val title: String,
    val generalDescription: String? = null,
    val items: List<TrucoListItem>
)

val listaDeCategoriasDeTrucosGTA = listOf(
    TrucosCategory(
        title = "Trucos de vehículos",
        items = listOf(
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "bmx",
                    efecto = "Aparece una BMX",
                    comandoConsola = "BANDIT",
                    codigoTelefono = "1-999-226-248"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "buzzard",
                    efecto = "Aparece un helicóptero Buzzard",
                    comandoConsola = "BUZZOFF",
                    codigoTelefono = "1-999-289-9633"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "caddy",
                    efecto = "Aparece un coche de golf",
                    comandoConsola = "HOLEIN1",
                    codigoTelefono = "1-999-4653-46-1"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "comet",
                    efecto = "Aparece un coche deportivo Comet",
                    comandoConsola = "COMET",
                    codigoTelefono = "1-999-266-38"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "duster",
                    efecto = "Aparece una avioneta fumigadora",
                    comandoConsola = "FLYSPRAY",
                    codigoTelefono = "1-999-359-77729"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "limusina",
                    efecto = "Aparece una limusina",
                    comandoConsola = "VINEWOOD",
                    codigoTelefono = "1-999-846-39663"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "parachute",
                    efecto = "Consigues un paracaídas",
                    comandoConsola = "SKYDIVE",
                    codigoTelefono = "1-999-759-3483"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "pcj",
                    efecto = "Aparece una moto PCJ-600",
                    comandoConsola = "ROCKET",
                    codigoTelefono = "1-999-762-538"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "rapid",
                    efecto = "Aparece un RAPID GT",
                    comandoConsola = "RAPIDGT",
                    codigoTelefono = "1-999-727-4348"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "sanchez",
                    efecto = "Aparece una moto Sánchez",
                    comandoConsola = "OFFROAD",
                    codigoTelefono = "1-999-633-7623"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "mallard",
                    efecto = "Aparece un avión de acrobacias",
                    comandoConsola = "BARNSTORM",
                    codigoTelefono = "1-999-2276-78676"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "trashmaster",
                    efecto = "Aparece un Trashmaster",
                    comandoConsola = "TRASHED",
                    codigoTelefono = "1-999-872-7433"
                )
            )
        )
    ),
    TrucosCategory(
        title = "Trucos de Salud y Blindaje",
        items = listOf(
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "salud_max",
                    efecto = "Salud y blindaje al máximo",
                    comandoConsola = "TURTLE",
                    codigoTelefono = "1-999-887-853"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "invencibilidad",
                    efecto = "Invencibilidad (5 minutos)",
                    comandoConsola = "PAINKILLER",
                    codigoTelefono = "1-999-724-654-5537"
                )
            )
            // ... más trucos de salud
        )
    ),
    TrucosCategory(
        title = "Trucos de Armas y Munición",
        items = listOf(
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "todas_armas",
                    efecto = "Consigue todas las armas y munición",
                    comandoConsola = "TOOLUP",
                    codigoTelefono = "1-999-8665-87"
                )
            )
            // ... más trucos de armas
        )
    ),
    TrucosCategory(
        title = "Trucos de Habilidades",
        items = listOf(
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "super_salto",
                    efecto = "Súper salto",
                    comandoConsola = "KANGAROO",
                    codigoTelefono = "1-999-467-86-48"
                )
            )
            // ... más trucos de habilidades
        )
    ),
    TrucosCategory(
        title = "Trucos de Nivel de Búsqueda",
        items = listOf(
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "reducir_busqueda",
                    efecto = "Reducir nivel de búsqueda",
                    comandoConsola = "LAWYERUP",
                    codigoTelefono = "1-999-5299-3787"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "aumentar_busqueda",
                    efecto = "Aumentar nivel de búsqueda",
                    comandoConsola = "FUGITIVE",
                    codigoTelefono = "1-999-3844-8483"
                )
            )
        )
    ),
    TrucosCategory(
        title = "Trucos de Entorno y Clima",
        items = listOf(
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "cambiar_clima",
                    efecto = "Cambiar el clima",
                    comandoConsola = "MAKEITRAIN",
                    codigoTelefono = "1-999-625-348-7246"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "gravedad_lunar",
                    efecto = "Gravedad lunar (coches flotan)",
                    comandoConsola = "FLOATER",
                    codigoTelefono = "1-999-356-2837"
                )
            )
        )
    )
    // ... más categorías
)


// --- VIEWMODEL ---
class TrucosScreenViewModel : ViewModel() {
    private val _categoriasDeTrucos = MutableStateFlow<List<TrucosCategory>>(emptyList())
    val categoriasDeTrucos: StateFlow<List<TrucosCategory>> = _categoriasDeTrucos

    init {
        _categoriasDeTrucos.value = listaDeCategoriasDeTrucosGTA
    }
}

// --- COMPOSABLE PRINCIPAL DE LA PANTALLA DE TRUCOS ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrucosScreen(
    navController: NavController,
    screenTitle: String = "Trucos GTA V", // Título por defecto
    viewModel: TrucosScreenViewModel = viewModel()
) {
    val categorias by viewModel.categoriasDeTrucos.collectAsState()

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Aquí encontrarás todos los trucos disponibles para Grand Theft Auto V. ¡Úsalos con precaución!",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(categorias, key = { it.title }) { categoria ->
                TrucoCategoryCard(category = categoria)
            }
        }
    }
}

// --- COMPOSABLE PARA UNA TARJETA DE CATEGORÍA DE TRUCOS ---
@Composable
fun TrucoCategoryCard(category: TrucosCategory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = category.title,
                style = MaterialTheme.typography.headlineSmall, // Ajustado para ser un poco más pequeño que el de CompletedScreen
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            category.generalDescription?.let { desc ->
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            if (category.items.isNotEmpty()) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                category.items.forEachIndexed { index, trucoListItem ->
                    when (trucoListItem) {
                        is TrucoListItem.TrucoEntry -> TrucoEntryView(trucoInfo = trucoListItem.info)
                        is TrucoListItem.SubHeaderText -> {
                            Text(
                                text = trucoListItem.text,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                            )
                        }
                    }
                    if (index < category.items.size - 1) {
                        Spacer(modifier = Modifier.height(10.dp)) // Espacio entre trucos individuales
                    }
                }
            }
        }
    }
}

// --- COMPOSABLE PARA MOSTRAR UN TRUCO INDIVIDUAL ---
@Composable
fun TrucoEntryView(trucoInfo: TrucoInfo) {
    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp)) {
                    append("Efecto: ")
                }
                append(trucoInfo.efecto)
            },
            style = MaterialTheme.typography.bodyLarge
        )

        trucoInfo.comandoConsola?.let {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                        append("Comando PC: ")
                    }
                    append(it)
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        trucoInfo.codigoTelefono?.let {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                        append("Teléfono: ")
                    }
                    append(it)
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}


// --- PREVIEW ---
@Preview(showBackground = true, name = "Trucos Screen Light")
@Composable
fun TrucosScreenPreview() {
    GuiaDefinitivaGTAVTheme {
        TrucosScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Truco Entry Preview")
@Composable
fun TrucoEntryPreview() {
    GuiaDefinitivaGTAVTheme {
        CheatsScreen(
            navController = rememberNavController(),
            screenTitle = "Trucos"
        )
    }
}