package com.cerdita.app.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

enum class ChatBackgroundType {
    PIGS_SLEEPING,
    KOALAS_TREES,
    FLOWER_GARDEN,
    SKY_CLOUDS,
    STARRY_NIGHT,
    RAINBOW,
    FLOATING_HEARTS,
    PETS_TOGETHER,
    CUSTOM
}

@Composable
fun ChatBackground(
    backgroundType: ChatBackgroundType = ChatBackgroundType.PIGS_SLEEPING,
    customImageUri: String? = null,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                when (backgroundType) {
                    ChatBackgroundType.PIGS_SLEEPING -> Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFB6C1),
                            Color(0xFFFFF5F7),
                            Color(0xFFE6F3FF)
                        )
                    )
                    ChatBackgroundType.KOALAS_TREES -> Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF8FBC8F),
                            Color(0xFFF0F8F0),
                            Color(0xFF778899)
                        )
                    )
                    ChatBackgroundType.FLOWER_GARDEN -> Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFB7C5),
                            Color(0xFFF5F5DC),
                            Color(0xFF98FB98)
                        )
                    )
                    ChatBackgroundType.SKY_CLOUDS -> Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF87CEEB),
                            Color(0xFFF0F8FF),
                            Color(0xFFFFFFFF)
                        )
                    )
                    ChatBackgroundType.STARRY_NIGHT -> Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF191970),
                            Color(0xFF483D8B),
                            Color(0xFF6A5ACD)
                        )
                    )
                    ChatBackgroundType.RAINBOW -> Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFF0000),
                            Color(0xFFFF7F00),
                            Color(0xFFFFFF00),
                            Color(0xFF00FF00),
                            Color(0xFF0000FF),
                            Color(0xFF4B0082),
                            Color(0xFF9400D3)
                        )
                    )
                    ChatBackgroundType.FLOATING_HEARTS -> Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFF69B4),
                            Color(0xFFFFB6C1),
                            Color(0xFFFFC0CB)
                        )
                    )
                    ChatBackgroundType.PETS_TOGETHER -> Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFFB6C1),
                            Color(0xFF87CEEB),
                            Color(0xFF98FB98)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                    ChatBackgroundType.CUSTOM -> {
                        if (customImageUri != null) {
                            Brush.linearGradient(
                                colors = listOf(Color.White, Color.LightGray)
                            )
                        } else {
                            Brush.verticalGradient(
                                colors = listOf(Color.White, Color.LightGray)
                            )
                        }
                    }
                }
            )
    ) {
        // Animaciones de fondo según el tipo
        when (backgroundType) {
            ChatBackgroundType.FLOATING_HEARTS -> {
                FloatingHeartsAnimation(modifier = Modifier.alpha(alpha))
            }
            ChatBackgroundType.STARRY_NIGHT -> {
                StarsAnimation(modifier = Modifier.alpha(alpha))
            }
            ChatBackgroundType.SKY_CLOUDS -> {
                CloudsAnimation(modifier = Modifier.alpha(alpha))
            }
            else -> {}
        }

        // Imagen personalizada si existe
        if (backgroundType == ChatBackgroundType.CUSTOM && customImageUri != null) {
            AsyncImage(
                model = customImageUri,
                contentDescription = "Fondo personalizado",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.3f
            )
        }

        // Contenido del chat
        content()
    }
}

@Composable
private fun FloatingHeartsAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    
    repeat(10) { i ->
        val offsetY by infiniteTransition.animateFloat(
            initialValue = 100f + (i * 50f),
            targetValue = -100f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000 + (i * 500), easing = EaseInOut),
                repeatMode = RepeatMode.Restart
            )
        )
        
        val offsetX by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 100f,
            animationSpec = infiniteRepeatable(
                animation = tween(1500, easing = EaseInOut),
                repeatMode = RepeatMode.Reverse
            )
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .offset { androidx.compose.ui.unit.IntOffset(offsetX.toInt(), offsetY.toInt()) }
        ) {
            Text(
                text = "💕",
                color = Color(0xFFFF69B4).copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
private fun StarsAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    
    repeat(20) { i ->
        val alpha by infiniteTransition.animateFloat(
            initialValue = 0.2f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000 + (i * 200), easing = EaseInOut),
                repeatMode = RepeatMode.Reverse
            )
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    start = ((i * 50) % 400).dp,
                    top = ((i * 30) % 600).dp
                )
        ) {
            Text(
                text = "⭐",
                color = Color(0xFFFFD700).copy(alpha = alpha)
            )
        }
    }
}

@Composable
private fun CloudsAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    
    repeat(5) { i ->
        val offsetX by infiniteTransition.animateFloat(
            initialValue = -100f,
            targetValue = 400f,
            animationSpec = infiniteRepeatable(
                animation = tween(5000 + (i * 1000), easing = EaseInOut),
                repeatMode = RepeatMode.Restart
            )
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .offset { androidx.compose.ui.unit.IntOffset(offsetX.toInt(), (i * 100).toInt()) }
        ) {
            Text(
                text = "☁️",
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}
