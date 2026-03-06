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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import com.cerdita.app.util.ImageUtils
import java.io.File

@Composable
fun ImagePicker(
    onImageSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showImagePickerDialog by remember { mutableStateOf(false) }
    var currentPhotoUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && currentPhotoUri != null) {
            onImageSelected(currentPhotoUri!!)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImageSelected(it) }
    }

    fun createImageFile(): File {
        val storageDir = File(context.cacheDir, "camera_photos")
        storageDir.mkdirs()
        return File.createTempFile(
            "cerdita_${System.currentTimeMillis()}",
            ".jpg",
            storageDir
        )
    }

    Box(modifier = modifier) {
        IconButton(
            onClick = { showImagePickerDialog = true }
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = "Seleccionar imagen",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        if (showImagePickerDialog) {
            ImagePickerDialog(
                onTakePhoto = {
                    val photoFile = createImageFile()
                    currentPhotoUri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.provider",
                        photoFile
                    )
                    cameraLauncher.launch(currentPhotoUri)
                    showImagePickerDialog = false
                },
                onChooseFromGallery = {
                    galleryLauncher.launch("image/*")
                    showImagePickerDialog = false
                },
                onDismiss = { showImagePickerDialog = false }
            )
        }
    }
}

@Composable
private fun ImagePickerDialog(
    onTakePhoto: () -> Unit,
    onChooseFromGallery: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Seleccionar imagen",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTakePhoto() }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Tomar foto",
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
                        imageVector = Icons.Default.PhotoLibrary,
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
