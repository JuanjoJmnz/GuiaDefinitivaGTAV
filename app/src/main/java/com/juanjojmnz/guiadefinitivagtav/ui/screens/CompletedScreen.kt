package com.juanjojmnz.guiadefinitivagtav.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme


sealed interface HobbyItem {
    data class SubCategory(
        val title: String,
        val description: String? = null,
        val subDescription: String? = null,
        val items: List<String>? = null
    ) : HobbyItem

    data class SingleRequirement(
        val title: String
    ) : HobbyItem
}


data class CompletionCategory(
    val title: String,
    val generalDescription: String? = null,
    val items: List<HobbyItem> = emptyList(),
    val description: String? = null,
    val requirements: List<String> = emptyList()
)


val gtaVCompletionRequirements = listOf(
    CompletionCategory(
        title = "Misiones Principales",
        description = "Completa las 69 misiones principales de la historia.",
        requirements = listOf()
    ),
    CompletionCategory(
        title = "Misiones de Extraños y Locos",
        description = "Completar 24 de las 63 misiones de Extraños y locos. Estas 24 misiones son:",
        requirements = listOf(
            "Pedir favores", "Otro favor", "Otro favor más", "Y dale con los favores", "Un último favor",
            "Cambios", "Paparazzi", "Paparazzi - El video porno", "Paparazzi - Socios",
            "Paparazzi - Alteza", "Paparazzi - Colapso", "Paparazzi - La cruda realidad",
            "Una joven estrella en Vinewood", "Movimiento verde - Franklin", "Movimiento verde - La recogida",
            "Movimiento verde - Para el arrastre", "Movimiento verde - La fumada",
            "Ejercizando demonios - Franklin", "Pasado de rosca", "La última frontera",
            "Gestión de riesgos", "Riesgo de liquidez", "Riesgo dirigido", "Riesgo no calculado"
        )
    ),
    CompletionCategory(
        title = "Aficiones y Pasatiempos",
        generalDescription = "Completa los siguientes pasatiempos.",
        items = listOf(
            HobbyItem.SubCategory(
                title = "Galería de tiro",
                description = "Gana 3 medallas en cada una de las siguientes categorías de armas:",
                items = listOf(
                    "Armas de mano.", "Subfusiles.", "Fusiles de asalto.",
                    "Escopetas.", "Ametralladoras ligeras.", "Armamento pesado."
                )
            ),
            HobbyItem.SubCategory(
                title = "Carreras urbanas",
                description = "Gana una medalla en cada una de las siguientes carreras:",
                items = listOf(
                    "Los Santos Sur", "Circuito urbano", "Aeropuerto",
                    "Autopista", "Canales de Vespucci"
                )
            ),
            HobbyItem.SingleRequirement(title = "Gana un partido de tenis"),
            HobbyItem.SingleRequirement(title = "Juega 9 hoyos de golf y acaba en par o bajo par"),
            HobbyItem.SingleRequirement(title = "Gana a los dardos"),
            HobbyItem.SingleRequirement(title = "Disfruta de un baile privado en el club de striptease"),
            HobbyItem.SubCategory(
                title = "Triatlones",
                description = "Queda primero, segundo o tercero en las siguientes pruebas:",
                items = listOf(
                    "Canales de Vespucci", "Alamo Sea", "Coyote Cross Country"
                )
            ),
            HobbyItem.SubCategory(
                title = "Carreras todoterreno",
                description = "Queda primero, segundo o tercero en las siguientes carreras:",
                items = listOf(
                    "Barrancos del cañón", "Carrera del risco", "Espiral hacia la mina",
                    "Sendero del valle", "Zambullida junto al lago", "Ecológica"
                )
            ),
            HobbyItem.SubCategory(
                title = "Escuela de vuelo",
                description = "Gana una medalla (bronce, plata u oro) en cada una de las siguientes pruebas:",
                items = listOf(
                    "Despegar de la pista", "Aterrizaje en pista", "Vuelo invertido", "Vuelo a cuchillo",
                    "Vuelo bajo", "Apuentizar", "Rizar el rizo", "Circuito en helicóptero",
                    "Carrera con helicóptero", "Paracaidismo", "Zona de salto", "Gánate tus alas"
                )
            ),
            HobbyItem.SubCategory(
                title = "Carreras marítimas",
                description = "Queda primero, segundo o tercero en cada una de las siguientes carreras:",
                items = listOf(
                    "Costa este", "Costa noroeste", "Raton Canyon", "Los Santos"
                )
            ),
            HobbyItem.SubCategory(
                title = "Saltos en paracaídas",
                description = "Completa TODOS los saltos. Todos los de helicóptero cuentan como uno, al igual que todos los de base.",
                items = listOf(
                    "Completa los 6 saltos BASE.",
                    "Completa los 7 saltos desde helicóptero."
                )
            )
        )
    ),
    CompletionCategory(
        title = "Eventos Aleatorios",
        description = "Completa 14 de los 57 (en old-gen) o 60 (en next-gen) eventos aleatorios.",
        requirements = listOf()
    ),
    CompletionCategory(
        title = "Varios",
        description = "Lista de otros requisitos para el 100%:",
        items = listOf(
            HobbyItem.SingleRequirement(title = "Recoge las 50 partes de la nave espacial."),
            HobbyItem.SingleRequirement(title = "Recoge los 50 fragmentos de la carta."),
            HobbyItem.SingleRequirement(title = "Completa al menos 25 de los 50 vuelos bajo el puente."),
            HobbyItem.SingleRequirement(title = "Completa al menos 8 de los 15 vuelos a cuchillo."),
            HobbyItem.SingleRequirement(title = "Completa al menos 25 de los 50 saltos acrobáticos."),
            HobbyItem.SingleRequirement(title = "Atraca al menos una de las 19 tiendas."),
            HobbyItem.SingleRequirement(title = "Adquiere al menos 5 de las 15 propiedades (ni el Vanilla Unicorn ni los garajes cuentan)."),
            HobbyItem.SingleRequirement(title = "Compra un vehículo en un sitio web."),
            HobbyItem.SingleRequirement(title = "Pasea a Chop y lánzale la pelota."),
            HobbyItem.SingleRequirement(title = "Consigue una cita sexual."),
            HobbyItem.SingleRequirement(title = "Recibe el servicio de una prostituta."),
            HobbyItem.SingleRequirement(title = "Ve al cine"),
            HobbyItem.SubCategory(
                title = "Actividades con los otros personajes",
                description = "Llama a alguno de los otros personajes jugables y haz las siguientes actividades:",
                items = listOf(
                    "Ir a un bar.", "Ir al cine", "Ir al club de striptease", "Jugar a los dardos"
                )
            )
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedScreen(navController: NavController, screenTitle: String) {
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
                    text = "Para alcanzar el 100% en Grand Theft Auto V, necesitas completar" +
                            " una variedad de tareas a lo largo del juego. Aquí tienes un desglose:",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(gtaVCompletionRequirements) { category ->
                RequirementCategoryCard(category = category)
            }
        }
    }
}

@Composable
fun RequirementCategoryCard(category: CompletionCategory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = category.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )


            category.generalDescription?.let { desc ->
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = if (category.items.isNotEmpty()) 8.dp else 0.dp)
                )
            }


            if (category.items.isNotEmpty()) {

                category.items.forEachIndexed { index, hobbyItem ->
                    when (hobbyItem) {
                        is HobbyItem.SubCategory -> SubCategoryItemView(subCategory = hobbyItem)
                        is HobbyItem.SingleRequirement -> SingleRequirementView(requirement = hobbyItem)
                    }
                    if (index < category.items.size - 1) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            } else {

                category.description?.let { desc ->
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )
                }
                if (category.description != null && category.requirements.isNotEmpty()) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }
                category.requirements.forEach { requirement ->
                    Text(
                        text = "• $requirement",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SubCategoryItemView(subCategory: HobbyItem.SubCategory) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = subCategory.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        subCategory.description?.let { desc ->
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp, bottom = 2.dp)
            )
        }

        subCategory.subDescription?.let { subDesc ->
            Text(
                text = subDesc,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
            )
        }

        subCategory.items?.forEach { item ->
            Text(
                text = "• $item",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, bottom = 2.dp)
            )
        }
    }
}

@Composable
fun SingleRequirementView(requirement: HobbyItem.SingleRequirement) {
    Text(
        text = requirement.title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun CompletedScreenPreview() {
    GuiaDefinitivaGTAVTheme {
        CompletedScreen(
            navController = rememberNavController(),
            screenTitle = "Requisitos 100%"
        )
    }
}
