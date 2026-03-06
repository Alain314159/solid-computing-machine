package com.cerdita.app.presentation.ui.components

import android.media.MediaRecorder
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.io.File
import java.io.IOException

@Composable
fun VoiceRecorder(
    onRecordingStart: () -> Unit,
    onRecordingStop: (File) -> Unit,
    modifier: Modifier = Modifier
) {
    var isRecording by remember { mutableStateOf(false) }
    var recordingTime by remember { mutableStateOf(0) }
    var outputFile by remember { mutableStateOf<File?>(null) }

    LaunchedEffect(isRecording) {
        if (isRecording) {
            recordingTime = 0
            while (isRecording && recordingTime < 300) { // Max 5 minutes
                delay(1000)
                recordingTime++
            }
            if (recordingTime >= 300) {
                isRecording = false
                outputFile?.let { onRecordingStop(it) }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isRecording) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Visualizador de onda
                AudioWaveform(
                    isRecording = isRecording,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Tiempo de grabación
                Text(
                    text = formatTime(recordingTime),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Botón de detener
                Button(
                    onClick = {
                        isRecording = false
                        outputFile?.let { onRecordingStop(it) }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Stop,
                        contentDescription = "Detener",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Detener")
                }
            }
        } else {
            // Botón de grabar
            ExtendedFloatingActionButton(
                onClick = {
                    val outputDir = File("/sdcard/Cerdita/voice_notes")
                    outputDir.mkdirs()
                    outputFile = File(outputDir, "voice_${System.currentTimeMillis()}.m4a")
                    
                    try {
                        val mediaRecorder = MediaRecorder().apply {
                            setAudioSource(MediaRecorder.AudioSource.MIC)
                            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                            setOutputFile(outputFile?.absolutePath)
                            prepare()
                            start()
                        }
                        isRecording = true
                        onRecordingStart()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Grabar",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Grabar nota de voz")
            }
        }
    }
}

@Composable
private fun AudioWaveform(
    isRecording: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    val amplitude by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = modifier) {
        val barWidth = size.width / 30
        val maxHeight = size.height * amplitude

        for (i in 0 until 30) {
            val barHeight = maxHeight * (0.3f + kotlin.random.Random.nextFloat() * 0.7f)
            val x = i * barWidth
            val y = (size.height - barHeight) / 2

            drawRoundRect(
                color = Color(0xFFFF69B4),
                topLeft = Offset(x, y),
                size = androidx.compose.ui.geometry.Size(barWidth - 2.dp.toPx(), barHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx())
            )
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", minutes, secs)
}
