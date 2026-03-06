package com.cerdita.app.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RomanticEffect(
    effectType: RomanticEffectType,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    when (effectType) {
        RomanticEffectType.HEARTS -> {
            HeartsEffect(scale = scale, alpha = alpha, modifier = modifier)
        }
        RomanticEffectType.STARS -> {
            StarsEffect(scale = scale, alpha = alpha, modifier = modifier)
        }
        RomanticEffectType.FLOWERS -> {
            FlowersEffect(scale = scale, alpha = alpha, modifier = modifier)
        }
    }
}

@Composable
private fun HeartsEffect(
    scale: Float,
    alpha: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        repeat(5) { i ->
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .scale(scale)
                    .offset(
                        x = ((i * 60) - 120).dp,
                        y = ((i * 40) - 80).dp
                    ),
                shape = MaterialTheme.shapes.small,
                color = Color(0xFFFF69B4).copy(alpha = alpha)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    androidx.compose.material3.Text(
                        text = "💕",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun StarsEffect(
    scale: Float,
    alpha: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        repeat(5) { i ->
            Surface(
                modifier = Modifier
                    .size(30.dp)
                    .scale(scale)
                    .offset(
                        x = ((i * 70) - 140).dp,
                        y = ((i * 50) - 100).dp
                    ),
                shape = MaterialTheme.shapes.small,
                color = Color(0xFFFFD700).copy(alpha = alpha)
            ) {
                androidx.compose.material3.Text(
                    text = "⭐",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun FlowersEffect(
    scale: Float,
    alpha: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        repeat(5) { i ->
            Surface(
                modifier = Modifier
                    .size(35.dp)
                    .scale(scale)
                    .offset(
                        x = ((i * 65) - 130).dp,
                        y = ((i * 45) - 90).dp
                    ),
                shape = MaterialTheme.shapes.small,
                color = Color(0xFFFFB7C5).copy(alpha = alpha)
            ) {
                androidx.compose.material3.Text(
                    text = "🌸",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

enum class RomanticEffectType {
    HEARTS,
    STARS,
    FLOWERS
}
