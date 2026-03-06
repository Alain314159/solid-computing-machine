package com.cerdita.app.presentation.ui.screens.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cerdita.app.presentation.ui.components.HugButton
import com.cerdita.app.presentation.ui.components.InputField
import com.cerdita.app.presentation.ui.components.MessageBubble
import com.cerdita.app.presentation.viewmodel.ChatViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var messageText by remember { mutableStateOf("") }

    // Auto-scroll al último mensaje
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            scope.launch {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

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
                        // TODO: Mostrar animación de abrazo
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
                val isFromMe = message.senderId == "me" // TODO: Comparar con userId real
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
            }
        )
    }
}
