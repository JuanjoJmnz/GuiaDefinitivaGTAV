package com.juanjojmnz.guiadefinitivagtav.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juanjojmnz.guiadefinitivagtav.R
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GTAOrange
import com.juanjojmnz.guiadefinitivagtav.ui.theme.GuiaDefinitivaGTAVTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

private const val IMAGE_DISPLAY_DURATION_MS = 7000L
private const val IMAGE_TRANSITION_DURATION_MS = 2000L
private val MAX_PAN_OFFSET_DP = 15.dp

private const val FOREGROUND_SCALE_FACTOR = 1.15f
private const val BACKGROUND_BASE_SCALE_FACTOR = 1.05f

data class ParallaxImageResources(
    @DrawableRes val foregroundResId: Int,
    @DrawableRes val backgroundResId: Int,
    val id: String
)

@Composable
fun WelcomeScreen(onEnterClicked: () -> Unit) {

    val allParallaxImages = remember {
        listOf(
            ParallaxImageResources(R.drawable.logo_app, R.drawable.logo_app, "set0"),
            ParallaxImageResources(R.drawable.gta_char_1_fg, R.drawable.gta_bg_1, "set1"),
            ParallaxImageResources(R.drawable.gta_char_2_fg, R.drawable.gta_bg_2, "set2"),
            ParallaxImageResources(R.drawable.gta_char_3_fg, R.drawable.gta_bg_3, "set3"),
            ParallaxImageResources(R.drawable.gta_char_4_fg, R.drawable.gta_bg_4, "set4"),
            ParallaxImageResources(R.drawable.gta_char_5_fg, R.drawable.gta_bg_5, "set5"),
            ParallaxImageResources(R.drawable.gta_char_6_fg, R.drawable.gta_bg_1, "set6"),
            ParallaxImageResources(R.drawable.gta_char_7_fg, R.drawable.gta_bg_2, "set7"),
            ParallaxImageResources(R.drawable.gta_char_8_fg, R.drawable.gta_bg_3, "set8"),
            ParallaxImageResources(R.drawable.gta_char_9_fg, R.drawable.gta_bg_4, "set9"),
            ParallaxImageResources(R.drawable.gta_char_10_fg, R.drawable.gta_bg_5, "set10"),
            ParallaxImageResources(R.drawable.gta_char_11_fg, R.drawable.gta_bg_1, "set11"),
            ParallaxImageResources(R.drawable.gta_char_12_fg, R.drawable.gta_bg_2, "set12"),
            ParallaxImageResources(R.drawable.gta_char_13_fg, R.drawable.gta_bg_3, "set13"),
            ParallaxImageResources(R.drawable.gta_char_14_fg, R.drawable.gta_bg_4, "set14"),
            ParallaxImageResources(R.drawable.gta_char_15_fg, R.drawable.gta_bg_5, "set15"),
            ParallaxImageResources(R.drawable.gta_char_16_fg, R.drawable.gta_bg_1, "set16"),
            ParallaxImageResources(R.drawable.gta_char_17_fg, R.drawable.gta_bg_2, "set17"),
            ParallaxImageResources(R.drawable.gta_char_18_fg, R.drawable.gta_bg_3, "set18"),
            ParallaxImageResources(R.drawable.gta_char_19_fg, R.drawable.gta_bg_4, "set19"),
            ParallaxImageResources(R.drawable.gta_char_20_fg, R.drawable.gta_bg_5, "set20"),
            ParallaxImageResources(R.drawable.gta_char_21_fg, R.drawable.gta_bg_1, "set21"),
            ParallaxImageResources(R.drawable.gta_char_22_fg, R.drawable.gta_bg_2, "set22"),
            ParallaxImageResources(R.drawable.gta_char_23_fg, R.drawable.gta_bg_3, "set23"),
            ParallaxImageResources(R.drawable.gta_char_24_fg, R.drawable.gta_bg_4, "set24"),
            ParallaxImageResources(R.drawable.gta_char_25_fg, R.drawable.gta_bg_5, "set25"),
            ParallaxImageResources(R.drawable.gta_char_26_fg, R.drawable.gta_bg_1, "set26"),
            ParallaxImageResources(R.drawable.gta_char_28_fg, R.drawable.gta_bg_3, "set27"),
            ParallaxImageResources(R.drawable.gta_char_29_fg, R.drawable.gta_bg_4, "set28"),
            ParallaxImageResources(R.drawable.gta_char_30_fg, R.drawable.gta_bg_5, "set29"),
            ParallaxImageResources(R.drawable.gta_char_31_fg, R.drawable.gta_bg_1, "set30"),
            ParallaxImageResources(R.drawable.gta_char_32_fg, R.drawable.gta_bg_2, "set31"),
            ParallaxImageResources(R.drawable.gta_char_33_fg, R.drawable.gta_bg_3, "set32"),
            ParallaxImageResources(R.drawable.gta_char_34_fg, R.drawable.gta_bg_4, "set33"),
            ParallaxImageResources(R.drawable.gta_char_35_fg, R.drawable.gta_bg_5, "set34")
        )
    }

    if (allParallaxImages.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No hay imágenes configuradas para la pantalla de bienvenida.")
        }
        return
    }

    var currentImageSet by remember { mutableStateOf(allParallaxImages.first()) }

    val remainingImageIndices = remember { mutableStateListOf<Int>() }


    LaunchedEffect(key1 = allParallaxImages) {
        if (allParallaxImages.isNotEmpty()) {
            if (remainingImageIndices.isEmpty()) {
                remainingImageIndices.addAll(allParallaxImages.indices.toList().shuffled())
            }
            val nextIndexToShow = remainingImageIndices.removeFirstOrNull() ?: allParallaxImages.indices.random()
            currentImageSet = allParallaxImages[nextIndexToShow]

            while (true) {
                delay(IMAGE_DISPLAY_DURATION_MS)
                if (remainingImageIndices.isEmpty()) {
                    remainingImageIndices.addAll(allParallaxImages.indices.toList().shuffled())
                    if (allParallaxImages.size > 1) {
                        var tempNextIndex = remainingImageIndices.first()
                        while (tempNextIndex == allParallaxImages.indexOf(currentImageSet) && remainingImageIndices.size > 1) {
                            remainingImageIndices.shuffle()
                            tempNextIndex = remainingImageIndices.first()
                        }
                    }
                }
                val nextImageIndexInOriginalList = remainingImageIndices.removeFirstOrNull()
                if (nextImageIndexInOriginalList != null) {
                    currentImageSet = allParallaxImages[nextImageIndexInOriginalList]
                } else if (allParallaxImages.isNotEmpty()) {
                    currentImageSet = allParallaxImages.random()
                }
            }
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse_transition_welcome_parallax")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "pulse_alpha_welcome_parallax"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onEnterClicked() },
        contentAlignment = Alignment.Center
    ) {
        Crossfade(
            targetState = currentImageSet.id,
            animationSpec = tween(durationMillis = IMAGE_TRANSITION_DURATION_MS.toInt()),
            label = "parallax_image_crossfade"
        ) { targetImageId ->
            val imageSetToDisplay = allParallaxImages.find { it.id == targetImageId }

            if (imageSetToDisplay != null) {
                ParallaxKenBurnsImage(
                    foregroundResId = imageSetToDisplay.foregroundResId,
                    backgroundResId = imageSetToDisplay.backgroundResId,
                    imageKey = imageSetToDisplay.id,
                    animationDurationMillis = IMAGE_DISPLAY_DURATION_MS
                )
            } else {
                Text("Error: Imagen no encontrada para id $targetImageId")
            }
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "Guía Definitiva para GTA V",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        drawStyle = Stroke(
                            miter = 10f,
                            width = 10f,
                            join = StrokeJoin.Round
                        )
                    )
                )
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
                            miter = 10f,
                            width = 8f,
                            join = StrokeJoin.Round
                        )
                    ),
                    modifier = Modifier.alpha(pulseAlpha * 0.7f)
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

@Composable
private fun ParallaxKenBurnsImage(
    @DrawableRes foregroundResId: Int,
    @DrawableRes backgroundResId: Int,
    imageKey: Any,
    animationDurationMillis: Long,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    val fgScale = remember(imageKey) { Animatable(1f) }
    val fgTranslationX = remember(imageKey) { Animatable(0f) }
    val fgTranslationY = remember(imageKey) { Animatable(0f) }

    val bgScale = remember(imageKey) { Animatable(1f) }
    val bgTranslationX = remember(imageKey) { Animatable(0f) }
    val bgTranslationY = remember(imageKey) { Animatable(0f) }

    LaunchedEffect(key1 = imageKey) {
        fgScale.snapTo(1f)
        fgTranslationX.snapTo(0f)
        fgTranslationY.snapTo(0f)
        bgScale.snapTo(1f)
        bgTranslationX.snapTo(0f)
        bgTranslationY.snapTo(0f)

        val maxPanOffsetPx = with(density) { MAX_PAN_OFFSET_DP.toPx() }

        val targetFgScale = FOREGROUND_SCALE_FACTOR + Random.nextFloat() * 0.1f
        val targetFgTranslationX = (Random.nextFloat() * 2f - 1f) * maxPanOffsetPx * 0.8f
        val targetFgTranslationY = (Random.nextFloat() * 2f - 1f) * maxPanOffsetPx * 0.8f

        launch {
            fgScale.animateTo(
                targetValue = targetFgScale,
                animationSpec = tween(durationMillis = animationDurationMillis.toInt(), easing = LinearEasing)
            )
        }
        launch {
            fgTranslationX.animateTo(
                targetValue = targetFgTranslationX,
                animationSpec = tween(durationMillis = animationDurationMillis.toInt(), easing = LinearEasing)
            )
        }
        launch {
            fgTranslationY.animateTo(
                targetValue = targetFgTranslationY,
                animationSpec = tween(durationMillis = animationDurationMillis.toInt(), easing = LinearEasing)
            )
        }

        val targetBgScale = BACKGROUND_BASE_SCALE_FACTOR + Random.nextFloat() * 0.1f
        val targetBgTranslationX = (Random.nextFloat() * 2f - 1f) * maxPanOffsetPx
        val targetBgTranslationY = (Random.nextFloat() * 2f - 1f) * maxPanOffsetPx

        launch {
            bgScale.animateTo(
                targetValue = targetBgScale,
                animationSpec = tween(durationMillis = animationDurationMillis.toInt(), easing = LinearEasing)
            )
        }
        launch {
            bgTranslationX.animateTo(
                targetValue = targetBgTranslationX,
                animationSpec = tween(durationMillis = animationDurationMillis.toInt(), easing = LinearEasing)
            )
        }
        launch {
            bgTranslationY.animateTo(
                targetValue = targetBgTranslationY,
                animationSpec = tween(durationMillis = animationDurationMillis.toInt(), easing = LinearEasing)
            )
        }
    }

    Box(modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = backgroundResId),
            contentDescription = "Imagen de fondo con efecto Ken Burns",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = bgScale.value,
                    scaleY = bgScale.value,
                    translationX = bgTranslationX.value,
                    translationY = bgTranslationY.value,
                    transformOrigin = TransformOrigin.Center
                ),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = foregroundResId),
            contentDescription = "Personaje en primer plano con efecto Ken Burns",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = fgScale.value,
                    scaleY = fgScale.value,
                    translationX = fgTranslationX.value,
                    translationY = fgTranslationY.value,
                    transformOrigin = TransformOrigin.Center
                ),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenRealPreview() {
    GuiaDefinitivaGTAVTheme {
        WelcomeScreen(onEnterClicked = {})
    }
}