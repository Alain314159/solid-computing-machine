package com.cerdita.app.presentation.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun VideoPicker(
    onVideoSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    var showVideoPickerDialog by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo()
    ) { uri: Uri? ->
        uri?.let { onVideoSelected(it) }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onVideoSelected(it) }
    }

    Box(modifier = modifier) {
        IconButton(
            onClick = { showVideoPickerDialog = true }
        ) {
            Icon(
                imageVector = Icons.Default.Videocam,
                contentDescription = "Seleccionar video",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        if (showVideoPickerDialog) {
            VideoPickerDialog(
                onRecordVideo = {
                    cameraLauncher.launch("video/*")
                    showVideoPickerDialog = false
                },
                onChooseFromGallery = {
                    galleryLauncher.launch("video/*")
                    showVideoPickerDialog = false
                },
                onDismiss = { showVideoPickerDialog = false }
            )
        }
    }
}

@Composable
private fun VideoPickerDialog(
    onRecordVideo: () -> Unit,
    onChooseFromGallery: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Seleccionar video",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onRecordVideo() }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Videocam,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Grabar video",
                        fontSize = 18.sp
                    )
                }

                Divider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onChooseFromGallery() }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.VideoLibrary,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Elegir de la galería",
                        fontSize = 18.sp
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
