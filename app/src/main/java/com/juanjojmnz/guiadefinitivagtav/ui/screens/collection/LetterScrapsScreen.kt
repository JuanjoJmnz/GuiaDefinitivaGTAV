package com.juanjojmnz.guiadefinitivagtav.ui.screens.collection

import com.juanjojmnz.guiadefinitivagtav.data.model.LetterScrapItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.juanjojmnz.guiadefinitivagtav.R
import com.juanjojmnz.guiadefinitivagtav.ui.viewmodel.LetterScrapsViewModel

@Composable
fun LetterScrapsScreen(
    viewModel: LetterScrapsViewModel = viewModel()
) {
    val letterScraps by viewModel.letterScraps.collectAsState()

    val textoInformativo = "Reúne los 50 fragmentos de carta esparcidos por Los Santos y el condado de Blaine. " +
            "Una vez encontrados todos, se desbloqueará una misión de Extraños y Locos que revelará la confesión del asesino de Leonora Johnson."

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = textoInformativo,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic,
            lineHeight = 18.sp
        )

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

        if (letterScraps.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("Cargando fragmentos de carta...")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = letterScraps,
                    key = { scrap -> scrap.id }
                ) { scrap ->
                    LetterScrapCard(
                        scrap = scrap,
                        onFoundToggle = {
                            viewModel.toggleScrapFoundStatus(scrap.id)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LetterScrapCard(
    scrap: LetterScrapItem,
    onFoundToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = scrap.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                Checkbox(
                    checked = scrap.isFound,
                    onCheckedChange = { onFoundToggle() }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = scrap.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Mapa de Zona:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))

            AsyncImage(
                model = scrap.mapImageUrl,
                contentDescription = "Mapa de la zona para ${scrap.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .heightIn(max = 200.dp),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(id = R.drawable.icono_eventos_aleatorios),
                error = painterResource(id = R.drawable.icono_eventos_aleatorios)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Ubicación Exacta:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))

            AsyncImage(
                model = scrap.locationImageUrl,
                contentDescription = "Ubicación exacta para ${scrap.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .heightIn(max = 200.dp),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(id = R.drawable.icono_eventos_aleatorios),
                error = painterResource(id = R.drawable.icono_eventos_aleatorios)
            )
        }
    }
}