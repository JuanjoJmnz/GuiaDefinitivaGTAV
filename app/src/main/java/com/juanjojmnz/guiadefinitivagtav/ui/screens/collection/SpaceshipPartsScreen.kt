package com.juanjojmnz.guiadefinitivagtav.ui.screens.collection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.juanjojmnz.guiadefinitivagtav.R
import com.juanjojmnz.guiadefinitivagtav.data.model.SpaceshipPartItem
import com.juanjojmnz.guiadefinitivagtav.ui.viewmodel.SpaceshipPartsViewModel

@Composable
fun SpaceshipPartsScreen(
    viewModel: SpaceshipPartsViewModel = viewModel()
) {
    val spaceshipParts by viewModel.spaceshipParts.collectAsState()

    if (spaceshipParts.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Cargando piezas de nave...")
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = spaceshipParts,
            key = { part -> part.id }
        ) { part ->
            SpaceshipPartCard(
                part = part,
                onFoundToggle = {
                    viewModel.togglePartFoundStatus(part.id)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpaceshipPartCard(
    part: SpaceshipPartItem,
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
                    text = part.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                Checkbox(
                    checked = part.isFound,
                    onCheckedChange = { onFoundToggle() }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = part.description,
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
                model = part.mapImageUrl,
                contentDescription = "Mapa de la zona para ${part.name}",
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
                model = part.locationImageUrl,
                contentDescription = "Ubicación exacta para ${part.name}",
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