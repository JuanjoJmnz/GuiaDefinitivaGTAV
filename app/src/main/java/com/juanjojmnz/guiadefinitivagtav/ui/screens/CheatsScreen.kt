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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.juanjojmnz.guiadefinitivagtav.R
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class TrucoInfo(
    val id: String,
    val efecto: String,
    val comandoConsola: String? = null,
    val comandoPS: String? = null,
    val comandoXbox: String? = null,
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
        title = "Trucos de vehículos.",
        generalDescription = "Algunos como el del Dodo, el Kraken o el Duke O'Death necesitan haber completado sus respectivos eventos aleatorios.",
        items = listOf(
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "bmx",
                    efecto = "Aparece una BMX",
                    comandoConsola = "BANDIT",
                    codigoTelefono = "1-999-226-248",
                    comandoXbox = "Izquierda, Izquierda, Derecha, Derecha, Izquierda, Derecha, X, B, Y, RB, RT",
                    comandoPS = "Izquierda, Izquierda, Derecha, Derecha, Izquierda, Derecha, Cuadrado, Círculo, Triángulo, R1, R2"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "buzzard",
                    efecto = "Aparece un helicóptero Buzzard",
                    comandoConsola = "BUZZOFF",
                    codigoTelefono = "1-999-289-9633",
                    comandoPS = "Círculo, Círculo, L1, Círculo, Círculo, Círculo, L1, L2, R1, Triángulo, Círculo, Triángulo",
                    comandoXbox = "B, B, LB, B, B, B, LB, LT, RB, Y, B, Y"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "caddy",
                    efecto = "Aparece un coche de golf",
                    comandoConsola = "HOLEIN1",
                    codigoTelefono = "1-999-4653-46-1",
                    comandoPS = "Círculo, L1, Izquierda, R1, L2, X, R1, L1, Círculo, X",
                    comandoXbox = "B, LB, Izquierda, RB, LT, A, RB, LB, B, A"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "comet",
                    efecto = "Aparece un coche deportivo Comet",
                    comandoConsola = "COMET",
                    codigoTelefono = "1-999-266-38",
                    comandoPS = "R1, Círculo, R2, Derecha, L1, L2, X, X, Cuadrado, R1",
                    comandoXbox = "RB, B, RT, Derecha, LB, LT, A, A, X, RB"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "duster",
                    efecto = "Aparece una avioneta fumigadora",
                    comandoConsola = "FLYSPRAY",
                    codigoTelefono = "1-999-359-77729",
                    comandoPS = "Derecha, Izquierda, R1, R1, R1, Izquierda, Triángulo, Triángulo, X, Círculo, L1, L1",
                    comandoXbox = "Derecha, Izquierda, RB, RB, RB, Izquierda, Y, Y, A, B, LB, LB"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "limusina",
                    efecto = "Aparece una limusina",
                    comandoConsola = "VINEWOOD",
                    codigoTelefono = "1-999-846-39663",
                    comandoPS = "R2, Derecha, L2, Izquierda, Izquierda, R1, L1, Círculo, Derecha",
                    comandoXbox = "RT, Derecha, LT, Izquierda, Izquierda, RB, LB, B, Derecha"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "parachute",
                    efecto = "Consigues un paracaídas",
                    comandoConsola = "SKYDIVE",
                    codigoTelefono = "1-999-759-3483",
                    comandoPS = "Izquierda, Derecha, L1, L2, R1, R2, R2, Izquierda, Izquierda, Derecha, L1",
                    comandoXbox = "Izquierda, Derecha, LB, LT, RB, RT, RT, Izquierda, Izquierda, Derecha, LB"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "pcj",
                    efecto = "Aparece una moto PCJ-600",
                    comandoConsola = "ROCKET",
                    codigoTelefono = "1-999-762-538",
                    comandoXbox = "RB, Derecha, Izquierda, Derecha, RT, Izquierda, Derecha, X, Derecha, LT, LB, LB",
                    comandoPS = "R1, Derecha, Izquierda, Derecha, R2, Izquierda, Derecha, Cuadrado, Derecha, L2, L1, L1"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "rapid",
                    efecto = "Aparece un RAPID GT",
                    comandoConsola = "RAPIDGT",
                    codigoTelefono = "1-999-727-4348",
                    comandoXbox = "RT, LB, B, Derecha, LB, RB, Derecha, Izquierda, B, RT",
                    comandoPS = "R2, L1, Círculo, Derecha, L1, R1, Derecha, Izquierda, Círculo, R2"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "sanchez",
                    efecto = "Aparece una moto Sánchez",
                    comandoConsola = "OFFROAD",
                    codigoTelefono = "1-999-633-7623",
                    comandoXbox = "B, A, LB, B, B, LB, B, RB, RT, LT, LB, LB",
                    comandoPS = "Círculo, X, L1, Círculo, Círculo, L1, Círculo, R1, R2, L2, L1, L1"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "mallard",
                    efecto = "Aparece un avión de acrobacias",
                    comandoConsola = "BARNSTORM",
                    codigoTelefono = "1-999-2276-78676",
                    comandoXbox = "B, Derecha, LB, LT, Izquierda, RB, LB, LB, Izquierda, Izquierda, A, Y",
                    comandoPS = "Círculo, Derecha, L1, L2, Izquierda, R1, L1, L1, Izquierda, Izquierda, X, Triángulo"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "trashmaster",
                    efecto = "Aparece un Trashmaster",
                    comandoConsola = "TRASHED",
                    codigoTelefono = "1-999-872-7433",
                    comandoXbox = "B, RB, B, RB, Izquierda, Izquierda, RB, LB, B, Derecha",
                    comandoPS = "Círculo, R1, Círculo, R1, Izquierda, Izquierda, R1, L1, Círculo, Derecha"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "dodo",
                    efecto = "Aparece un Hidroavión Dodo",
                    comandoConsola = "EXTINCT",
                    codigoTelefono = "1-999-398-4628",
                    comandoXbox = "",
                    comandoPS = ""
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "kraken",
                    efecto = "Aparece un submarino Kraken",
                    comandoConsola = "BUBBLES",
                    codigoTelefono = "1-999-282-2537",
                    comandoXbox = "",
                    comandoPS = ""
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "duke",
                    efecto = "Aparece el coche antibalas Duke O'Death",
                    comandoConsola = "DEATHCAR",
                    codigoTelefono = "1-999-3328-4227",
                    comandoXbox = "",
                    comandoPS = ""
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
                    codigoTelefono = "1-999-887-853",
                    comandoPS = "Círculo, L1, Triángulo, R2, X, Cuadrado, Círculo, Derecha, Cuadrado, L1, L1, L1",
                    comandoXbox = "B, LB, Y, RT, A, X, B, Derecha, X, LB, LB, LB"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "invencibilidad",
                    efecto = "Invencibilidad (5 minutos)",
                    comandoConsola = "PAINKILLER",
                    codigoTelefono = "1-999-724-654-5537",
                    comandoPS = "Derecha, X, Derecha, Izquierda, Derecha, R1, Derecha, Izquierda, X, Triángulo",
                    comandoXbox = "Derecha, A, Derecha, Izquierda, Derecha, RB, Derecha, Izquierda, A, Y"
                )
            )
        )
    ),
    TrucosCategory(
        title = "Trucos de Armas y Munición",
        items = listOf(
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "balas_expl",
                    efecto = "Balas explosivas",
                    comandoConsola = "HIGHEX",
                    codigoTelefono = "1-999-444-439",
                    comandoPS = "Derecha, Cuadrado, X, Izquierda, R1, R2, Izquierda, Derecha, Derecha, L1, L1, L1",
                    comandoXbox = "Derecha, X, A, Izquierda, RB, RT, Izquierda, Derecha, Derecha, LB, LB, LB"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "balas_fue",
                    efecto = "Balas de fuego",
                    comandoConsola = "INCENDIARY",
                    codigoTelefono = "1-999-462-363-4279",
                    comandoPS = "L1, R1, Cuadrado, R1, Izquierda, R2, R1, Izquierda, Cuadrado, Derecha, L1, L1",
                    comandoXbox = "LB, RB, X, RB, Izquierda, RT, RB, Izquierda, X, Derecha, LB, LB"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "all_guns",
                    efecto = "Todas las armas y munición",
                    comandoConsola = "TOOLUP",
                    codigoTelefono = "1-999-8665-87",
                    comandoPS = "Triangulo, R2, Izquierda, L1, X, Derecha, Triangulo, Abajo, Cuadrado, L1, L1, L1",
                    comandoXbox = "Y, RT, Izquierda, LB, A, Derecha, Y, Abajo, X, LB, LB, LB"
                )
            ),
        )
    ),
    TrucosCategory(
        title = "Trucos de Habilidades y Poderes Especiales",
        items = listOf(
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "puñetazo_expl",
                    efecto = "Puñetazos explosivos",
                    comandoConsola = "HOTHANDS",
                    codigoTelefono = "1-999-4684-2637",
                    comandoPS = "Derecha, Izquierda, X, Triangulo, R1, Circulo, Circulo, Circulo, L2",
                    comandoXbox = "Derecha, Izquierda, A, Y, RB, B, B, B, LT"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "hab_esp",
                    efecto = "Recargar habilidad especial",
                    comandoConsola = "POWERUP",
                    codigoTelefono = "1-999-769-3787",
                    comandoPS = "x, x, Cuadrado, R1, L1, X, Derecha, Izquierda, X",
                    comandoXbox = "A, A, X, RB, LB, A, Derecha, Izquierda, A"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "super_salto",
                    efecto = "Súper salto",
                    comandoConsola = "HOPTOIT",
                    codigoTelefono = "1-999-467-86-48",
                    comandoPS = "Izquierda, Izquierda, Triangulo, Triangulo, Derecha, Derecha, Izquierda, Derecha, Cuadrado, R1, R2",
                    comandoXbox = "Izquierda, Izquierda, Y, Y, Derecha, Derecha, Izquierda, Derecha, X, RB, RT"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "correr_rap",
                    efecto = "Correr más rápido",
                    comandoConsola = "CATCHME",
                    codigoTelefono = "1-999-228-8463",
                    comandoPS = "Triangulo, Izquierda, Derecha, Derecha, L2, L1, Cuadrado",
                    comandoXbox = "Y, Izquierda, Derecha, Derecha, LT, LB, X"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "nadar_rap",
                    efecto = "Nadar más rápido",
                    comandoConsola = "GOTGILLS",
                    codigoTelefono = "1-999-468-44557",
                    comandoPS = "Izquierda, Izquierda, L1, Derecha, Derecha, R2, Izquierda, L2, Derecha",
                    comandoXbox = "Izquierda, Izquierda, LB, Derecha, Derecha, RT, Izquierda, LT, Derecha"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "timpo_lento",
                    efecto = "Apuntar ralentizando el tiempo (Aplicable 3 veces. Se desactiva a la 4ª)",
                    comandoConsola = "DEADEYE",
                    codigoTelefono = "1-999-332-3393",
                    comandoPS = "Cuadrado, L2, R1, Triangulo, Izquierda, Cuadrado, L2, Derecha, X",
                    comandoXbox = "X, LT, RB, Y, Izquierda, X, LT, Derecha, A"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "skyfall",
                    efecto = "Caída libre desde el cielo",
                    comandoConsola = "SKYFALL",
                    codigoTelefono = "1-999-759-3255",
                    comandoPS = "L1, L2, R1, R2, Izquierda, Derecha, Izquierda, Derecha, L1, L2, R1, R2, Izquierda, Derecha, Izquierda, Derecha",
                    comandoXbox = "LB, LT, RB, RT, Izquierda, Derecha, Izquierda, Derecha, LB, LT, RB, RT, Izquierda, Derecha, Izquierda, Derecha"
                )
            ),
        )
    ),
    TrucosCategory(
        title = "Trucos para el nivel de búsqueda policial",
        items = listOf(
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "mas_poli",
                    efecto = "Aumentar nivel de búsqueda",
                    comandoConsola = "FUGITIVE",
                    codigoTelefono = "1-999-3844-8483",
                    comandoPS = "R1, R1, Circulo, R2, Izquierda, Derecha, Izquierda, Derecha, Izquierda, Derecha",
                    comandoXbox = "RB, RB, B, RT, Izquierda, Derecha, Izquierda, Derecha, Izquierda, Derecha"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "menos_poli",
                    efecto = "Disminuir nivel de búsqueda",
                    comandoConsola = "LAWYERUP",
                    codigoTelefono = "1-99-5299-3787",
                    comandoPS = "R1, R1, Circulo, R2, Derecha, Izquierda, Derecha, Izquierda, Derecha, Izquierda",
                    comandoXbox = "RB, RB, B, RT, Derecha, Izquierda, Derecha, Izquierda, Derecha, Izquierda"
                )
            )
        )
    ),
    TrucosCategory(
        title = "Trucos del entorno, clima y varios",
        items = listOf(
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "borracho",
                    efecto = "Modo borracho",
                    comandoConsola = "LIQUOR",
                    codigoTelefono = "1-999-547-867",
                    comandoPS = "Triangulo, Derecha, Derecha, Izquierda, Derecha, Cuadrado, Circulo, Izquierda",
                    comandoXbox = "Y, Derecha, Derecha, Izquierda, Derecha, X, B, Izquierda"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "slowmotion",
                    efecto = "Cámara lenta (Puede usarse hasta 4 veces. La 5ª lo desactiva)",
                    comandoConsola = "SLOWMO",
                    codigoTelefono = "1-999-756-966",
                    comandoPS = "Triangulo, Izquierda, Derecha, Derecha, Cuadrado, R2, R1",
                    comandoXbox = "Y, Izquierda, Derecha, Derecha, X, RT, RB"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "explosion",
                    efecto = "Crea una explosión y nos da un teléfono negro",
                    comandoConsola = "No hay.",
                    codigoTelefono = "1-999-367-3767",
                    comandoPS = "",
                    comandoXbox = ""
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "clima",
                    efecto = "Cambiar clima/tiempo",
                    comandoConsola = "MAKEITRAIN",
                    codigoTelefono = "1-999-625-384-7246",
                    comandoPS = "R2, X, L1, L1, L2, L2, L2, Cuadrado",
                    comandoXbox = "RT, A, LB, LB, LT, LT, LT, X"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "lunar",
                    efecto = "Baja gravedad",
                    comandoConsola = "FLOATER",
                    codigoTelefono = "1-999-356-2837",
                    comandoPS = "Izquierda, Izquierda, L1, R1, L1, Derecha, Izquierda, L1, Izquierda",
                    comandoXbox = "Izquierda, Izquierda, LB, RB, LB, Derecha, Izquierda, LB, Izquierda"
                )
            ),
            TrucoListItem.TrucoEntry(
                TrucoInfo(
                    id = "coches_slip",
                    efecto = "Coches resbaladizos",
                    comandoConsola = "SNOWDAY",
                    codigoTelefono = "1-999-766-9329",
                    comandoPS = "Triangulo, R1, R1, Izquierda, R1, L1, R2, L1",
                    comandoXbox = "Y, RB, RB, Izquierda, RB, LB, RT, LB"
                )
            )
        )
    )
)

class TrucosScreenViewModel : ViewModel() {
    private val _categoriasDeTrucos = MutableStateFlow<List<TrucosCategory>>(emptyList())
    val categoriasDeTrucos: StateFlow<List<TrucosCategory>> = _categoriasDeTrucos

    init {
        _categoriasDeTrucos.value = listaDeCategoriasDeTrucosGTA
    }
}


fun getPlayStationButtonIconRes(buttonName: String): Int? {
    return when (buttonName.uppercase()) {
        "IZQUIERDA" -> R.drawable.playstation_left_button
        "DERECHA" -> R.drawable.playstation_right_button
        "ARRIBA" -> R.drawable.playstation_up_button
        "ABAJO" -> R.drawable.playstation_down_button
        "X" -> R.drawable.playstation_button_x
        "CUADRADO" -> R.drawable.playstation_button_s
        "CÍRCULO", "CIRCULO" -> R.drawable.playstation_button_c
        "TRIÁNGULO", "TRIANGULO" -> R.drawable.playstation_button_t
        "L1" -> R.drawable.playstation_button_l1
        "R1" -> R.drawable.playstation_button_r1
        "L2" -> R.drawable.playstation_button_l2
        "R2" -> R.drawable.playstation_button_r2
        else -> {
            println("Icono de PS no encontrado para: $buttonName")
            null
        }
    }
}

fun getXboxButtonIconRes(buttonName: String): Int? {

    return when (buttonName.uppercase()) {
        "IZQUIERDA" -> R.drawable.xbox_d_pad_left
        "DERECHA" -> R.drawable.xbox_d_pad_right
        "ARRIBA" -> R.drawable.xbox_d_pad_up
        "ABAJO" -> R.drawable.xbox_d_pad_down
        "A" -> R.drawable.xbox_button_a
        "B" -> R.drawable.xbox_button_b
        "X" -> R.drawable.xbox_button_x
        "Y" -> R.drawable.xbox_button_y
        "LB" -> R.drawable.xbox_left_bumper
        "RB" -> R.drawable.xbox_right_bumper
        "LT" -> R.drawable.xbox_left_trigger
        "RT" -> R.drawable.xbox_right_trigger
        else -> {
            println("Icono de Xbox no encontrado para: $buttonName")
            null
        }
    }
}


@Composable
fun GameButtonIcon(
    iconResId: Int?,
    contentDescription: String,
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = Color.Unspecified
) {
    if (iconResId != null) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = contentDescription,
            modifier = modifier.size(size),
            tint = tint
        )
    }
}

@Composable
fun ComandoButtonsView(
    comando: String?,
    platform: String
) {
    if (comando.isNullOrBlank()) return

    val buttonNames = comando.split(",").map { it.trim() }

    Row(verticalAlignment = Alignment.CenterVertically) {
        buttonNames.forEachIndexed { index, buttonName ->
            val iconResId: Int? = when (platform) {
                "PS" -> getPlayStationButtonIconRes(buttonName)
                "XBOX" -> getXboxButtonIconRes(buttonName)
                else -> null
            }

            GameButtonIcon(
                iconResId = iconResId,
                contentDescription = buttonName,
            )

            if (index < buttonNames.size - 1) {
                Spacer(Modifier.width(3.dp))
            }
        }
    }
}

@Composable
fun TrucoEntryView(trucoInfo: TrucoInfo) {
    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp)) {
                }
                append(trucoInfo.efecto)
            },
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        trucoInfo.comandoConsola?.let {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 3.dp)) {
                Text(
                    "Comando en PC: ",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

        if (!trucoInfo.comandoPS.isNullOrBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 3.dp)) {
                Text(
                    "PS: ",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier.align(Alignment.CenterVertically).padding(end = 4.dp)
                )
                ComandoButtonsView(comando = trucoInfo.comandoPS, platform = "PS")
            }
        }

        if (!trucoInfo.comandoXbox.isNullOrBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 3.dp)) {
                Text(
                    "Xbox: ",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier.align(Alignment.CenterVertically).padding(end = 4.dp)
                )
                ComandoButtonsView(comando = trucoInfo.comandoXbox, platform = "XBOX")
            }
        }

        trucoInfo.codigoTelefono?.let {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 3.dp)) {
                Text(
                    "Teléfono: ",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

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
                style = MaterialTheme.typography.headlineSmall,
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
                        Spacer(modifier = Modifier.height(20.dp)) // Espacio entre trucos individuales
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheatsScreen(
    navController: NavController,
    screenTitle: String = "Trucos GTA V",
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

// --- PREVIEW ---
@Preview(showBackground = true, name = "Trucos Screen Light")
@Composable
fun TrucosScreenPreview() {
    GuiaDefinitivaGTAVTheme {
        CheatsScreen(navController = rememberNavController())
    }
}