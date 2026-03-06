package com.cerdita.app.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay

@Composable
fun HugButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isAnimating by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val scale = remember { Animatable(1f) }

    LaunchedEffect(isAnimating) {
        if (isAnimating) {
            // Animación de encogimiento
            scale.animateTo(
                targetValue = 0.8f,
                animationSpec = tween(150)
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.Stiffness.Low
                )
            )
            isAnimating = false
            showDialog = true
            
            // Mostrar diálogo de abrazo por 3 segundos
            delay(3000)
            showDialog = false
        }
    }

    Box(modifier = modifier) {
        IconButton(
            onClick = {
                isAnimating = true
                onClick()
            },
            modifier = Modifier
                .scale(scale.value)
                .size(56.dp)
        ) {
            Text(
                text = "🐷🤗🐨",
                fontSize = 28.sp
            )
        }

        if (showDialog) {
            HugAnimationDialog(
                onDismiss = { showDialog = false }
            )
        }
    }
}

@Composable
private fun HugAnimationDialog(
    onDismiss: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    val pigScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    val koalaScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(250)
        )
    )

    val heartAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        )
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Cerditas abrazándose
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🐷",
                            fontSize = 64.sp,
                            modifier = Modifier.scale(pigScale)
                        )
                        Text(
                            text = "🤗",
                            fontSize = 48.sp
                        )
                        Text(
                            text = "🐨",
                            fontSize = 64.sp,
                            modifier = Modifier.scale(koalaScale)
                        )
                    }

                    // Corazones flotando
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(5) { i ->
                            Text(
                                text = "💕",
                                fontSize = 32.sp,
                                modifier = Modifier.scale(0.8f + (i * 0.1f)),
                                color = Color(0xFFFF69B4).copy(alpha = heartAlpha)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mensaje
                    Text(
                        text = "¡Te envío un abrazo! 🐷🤗🐨",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Con mucho cariño 💕",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun HugAnimationFull(
    hugType: HugType = HugType.NORMAL,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    val colors = when (hugType) {
        HugType.NORMAL -> listOf(Color(0xFFFFB6C1), Color(0xFFFF69B4))
        HugType.ROMANTIC -> listOf(Color(0xFFFF1493), Color(0xFFFF69B4), Color(0xFFFFD700))
        HugType.FRIENDSHIP -> listOf(Color(0xFF87CEEB), Color(0xFFFFD700))
        HugType.GROUP -> listOf(Color(0xFFFF69B4), Color(0xFF87CEEB), Color(0xFF98FB98), Color(0xFFFFD700))
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors = colors)
            )
    ) {
        // Animación completa de abrazo según el tipo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = when (hugType) {
                    HugType.NORMAL -> "🐷🤗🐨"
                    HugType.ROMANTIC -> "💕🐷🤗🐨💕"
                    HugType.FRIENDSHIP -> "⭐🐷🤗🐨⭐"
                    HugType.GROUP -> "🐷🐷🤗🐨🐨"
                },
                fontSize = 72.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = when (hugType) {
                    HugType.NORMAL -> "¡Abrazo de oso!"
                    HugType.ROMANTIC -> "¡Abrazo lleno de amor!"
                    HugType.FRIENDSHIP -> "¡Abrazo de amistad!"
                    HugType.GROUP -> "¡Abrazo grupal!"
                },
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Efectos adicionales según el tipo
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val effects = when (hugType) {
                    HugType.NORMAL -> listOf("💕", "✨", "💖")
                    HugType.ROMANTIC -> listOf("💕", "💖", "💗", "💓", "💞")
                    HugType.FRIENDSHIP -> listOf("⭐", "✨", "🌟")
                    HugType.GROUP -> listOf("🌈", "💕", "⭐", "✨", "🎉")
                }

                effects.forEach { effect ->
                    Text(
                        text = effect,
                        fontSize = 32.sp
                    )
                }
            }
        }
    }
}

enum class HugType {
    NORMAL,
    ROMANTIC,
    FRIENDSHIP,
    GROUP
}
