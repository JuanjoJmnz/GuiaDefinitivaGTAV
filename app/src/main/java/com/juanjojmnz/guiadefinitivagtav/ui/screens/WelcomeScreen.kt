package com.juanjojmnz.guiadefinitivagtav.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juanjojmnz.guiadefinitivagtav.R
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GTAOrange
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Color

@Composable
fun WelcomeScreen(onEnterClicked: () -> Unit) {

    val welcomeImages = remember {
        listOf(
            R.drawable.gta_entry_background,
            R.drawable.gta_entry_background_,
            R.drawable.gta_entry_background_1,
            R.drawable.gta_entry_background_2,
            R.drawable.gta_entry_background_3,
            R.drawable.gta_entry_background_4,
            R.drawable.gta_entry_background_5,
            R.drawable.gta_entry_background_6,
            R.drawable.gta_entry_background_7,
            R.drawable.gta_entry_background_8,
            R.drawable.gta_entry_background_9
        )
    }
    val randomImageRes = remember { welcomeImages.random() }


    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "pulse_alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onEnterClicked() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = randomImageRes),
            contentDescription = "Imagen de bienvenida de GTA V",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(contentAlignment = Alignment.Center) {
                // Texto del BORDE (detrás)
                Text(
                    text = "Guía Definitiva para GTA V",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        drawStyle = Stroke(
                            width = 20f,
                            join = StrokeJoin.Round
                        )
                    )
                )
                // Texto del RELLENO (encima)
                Text(
                    text = "Guía Definitiva para GTA V",
                    color = GTAOrange,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "Pulsa para entrar",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(
                        drawStyle = Stroke(
                            width = 20f,
                            join = StrokeJoin.Round
                        )
                    )
                )
                Text(
                    text = "Pulsa para entrar",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.alpha(pulseAlpha)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    GuiaDefinitivaGTAVTheme {
        WelcomeScreen(onEnterClicked = {})
    }
}
    