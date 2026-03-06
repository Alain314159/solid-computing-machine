package com.cerdita.app.presentation.ui.components.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

/**
 * Indicador de carga principal de Cerdita
 * 
 * @param modifier Modificador para el indicador
 * @param color Color del indicador
 * @param strokeWidth Grosor del círculo
 * @param size Tamaño del indicador
 */
@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary,
    strokeWidth: androidx.compose.ui.unit.Dp = 4.dp,
    size: androidx.compose.ui.unit.Dp = 48.dp
) {
    CircularProgressIndicator(
        modifier = modifier.size(size),
        color = color,
        strokeWidth = strokeWidth,
        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
    )
}

/**
 * Indicador de carga con texto
 * 
 * @param loadingText Texto que se muestra debajo del indicador
 * @param modifier Modificador para el contenedor
 * @param color Color del indicador
 */
@Composable
fun LoadingIndicatorWithText(
    loadingText: String = "Cargando...",
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoadingIndicator(
            color = color,
            size = 48.dp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = loadingText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Indicador de carga en forma de diálogo
 * 
 * @param loadingText Texto que se muestra debajo del indicador
 * @param onDismissRequest Callback cuando se dismiss el diálogo
 * @param dismissOnBackPress Si se puede dismiss con el botón atrás
 * @param dismissOnClickOutside Si se puede dismiss tocando fuera
 */
@Composable
fun LoadingDialog(
    loadingText: String = "Cargando...",
    onDismissRequest: () -> Unit,
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .size(160.dp)
        ) {
            LoadingIndicator(
                size = 56.dp,
                strokeWidth = 5.dp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = loadingText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
    }
}

/**
 * Indicador de carga animado personalizado (giratorio)
 */
@Composable
fun AnimatedLoadingIndicator(
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary,
    size: androidx.compose.ui.unit.Dp = 48.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading_rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "loading_rotation_value"
    )

    LoadingIndicator(
        modifier = modifier.rotate(rotation),
        color = color,
        size = size
    )
}
