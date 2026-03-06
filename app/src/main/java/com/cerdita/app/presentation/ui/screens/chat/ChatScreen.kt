package com.cerdita.app.presentation.ui.screens.chat

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cerdita.app.presentation.ui.components.*
import com.cerdita.app.presentation.viewmodel.ChatViewModel
import com.cerdita.app.util.ImageUtils
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val messages by viewModel.messages.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var messageText by remember { mutableStateOf("") }
    var showAttachDialog by remember { mutableStateOf(false) }
    var showStickerPicker by remember { mutableStateOf(false) }

    // Auto-scroll al último mensaje
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            scope.launch {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    // Image picker
    val imagePicker = rememberImagePickerLauncher(
        onImageSelected = { uri ->
            // TODO: Compress and send image
            viewModel.sendMessage("📷 Imagen enviada")
        }
    )

    // Video picker
    val videoPicker = rememberVideoPickerLauncher(
        onVideoSelected = { uri ->
            // TODO: Send video
            viewModel.sendMessage("📹 Video enviado")
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        // Header
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Mi Amor 💕",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "en línea",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            actions = {
                HugButton(
                    onClick = {
                        viewModel.sendMessage("Te envío un abrazo 🐷🤗🐨")
                    }
                )
            }
        )

        // Lista de mensajes
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = listState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                items = messages,
                key = { it.messageId }
            ) { message ->
                val isFromMe = message.senderId == (viewModel::class.java.simpleName) // TODO: Comparar con userId real
                MessageBubble(
                    message = message,
                    isFromMe = isFromMe,
                    modifier = Modifier.animateItem()
                )
            }
        }

        // Indicador de escribiendo
        val typing by viewModel.typingIndicator.collectAsState()
        if (typing) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Escribiendo",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Italic
                )
                Spacer(modifier = Modifier.width(8.dp))
                Row {
                    repeat(3) { i ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .padding(2.dp)
                        ) {
                            Surface(
                                shape = MaterialTheme.shapes.small,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .animateContentSize()
                                )
                            }
                        }
                        if (i < 2) Spacer(modifier = Modifier.width(2.dp))
                    }
                }
            }
        }

        // Campo de entrada
        InputField(
            value = messageText,
            onValueChange = { messageText = it },
            onSendClick = {
                viewModel.sendMessage(messageText)
                messageText = ""
            },
            onTyping = { isTyping ->
                viewModel.onTyping(isTyping)
            },
            onAttachClick = { showAttachDialog = true },
            onEmojiClick = { /* TODO: Show emoji picker */ },
            onVoiceClick = { /* Handled by InputField */ }
        )

        // Sticker picker
        if (showStickerPicker) {
            StickerPicker(
                onStickerSelected = { sticker ->
                    viewModel.sendMessage("${sticker.emoji} Sticker: ${sticker.name}")
                    showStickerPicker = false
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    // Attach dialog
    if (showAttachDialog) {
        AttachDialog(
            onTakePhoto = {
                imagePicker.launch("image/*")
                showAttachDialog = false
            },
            onChooseImage = {
                imagePicker.launch("image/*")
                showAttachDialog = false
            },
            onRecordVideo = {
                videoPicker.launch("video/*")
                showAttachDialog = false
            },
            onChooseVideo = {
                videoPicker.launch("video/*")
                showAttachDialog = false
            },
            onChooseFile = {
                // TODO: File picker
                showAttachDialog = false
            },
            onDismiss = { showAttachDialog = false }
        )
    }
}

@Composable
private fun AttachDialog(
    onTakePhoto: () -> Unit,
    onChooseImage: () -> Unit,
    onRecordVideo: () -> Unit,
    onChooseVideo: () -> Unit,
    onChooseFile: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Adjuntar",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                AttachOption("📷 Tomar foto", onTakePhoto)
                AttachOption("🖼️ Elegir imagen", onChooseImage)
                AttachOption("📹 Grabar video", onRecordVideo)
                AttachOption("🎬 Elegir video", onChooseVideo)
                AttachOption("📁 Archivo", onChooseFile)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
private fun AttachOption(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// Helper functions for pickers
@Composable
private fun rememberImagePickerLauncher(onImageSelected: (Uri) -> Unit): androidx.activity.result.ActivityResultLauncher<String> {
    val context = LocalContext.current
    return (context as androidx.activity.ComponentActivity).registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onImageSelected(it) }
    }
}

@Composable
private fun rememberVideoPickerLauncher(onVideoSelected: (Uri) -> Unit): androidx.activity.result.ActivityResultLauncher<String> {
    val context = LocalContext.current
    return (context as androidx.activity.ComponentActivity).registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onVideoSelected(it) }
    }
}
