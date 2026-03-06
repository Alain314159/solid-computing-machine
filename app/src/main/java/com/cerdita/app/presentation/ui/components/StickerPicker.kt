package com.cerdita.app.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class Sticker(
    val id: String,
    val packId: String,
    val emoji: String,
    val name: String
)

data class StickerPack(
    val id: String,
    val name: String,
    val emoji: String,
    val stickers: List<Sticker>
)

@Composable
fun StickerPicker(
    onStickerSelected: (Sticker) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedPack by remember { mutableStateOf<String?>(null) }
    
    val stickerPacks = remember {
        listOf(
            StickerPack(
                id = "pig",
                name = "🐷 Cerdita",
                emoji = "🐷",
                stickers = List(24) { i ->
                    Sticker("pig_$i", "pig", getStickerEmoji(i), "Sticker $i")
                }
            ),
            StickerPack(
                id = "koala",
                name = "🐨 Koalita",
                emoji = "🐨",
                stickers = List(24) { i ->
                    Sticker("koala_$i", "koala", getStickerEmoji(i), "Sticker $i")
                }
            ),
            StickerPack(
                id = "flowers",
                name = "🌸 Flores",
                emoji = "🌸",
                stickers = List(20) { i ->
                    Sticker("flower_$i", "flowers", getStickerEmoji(i), "Sticker $i")
                }
            ),
            StickerPack(
                id = "clouds",
                name = "☁️ Nubes",
                emoji = "☁️",
                stickers = List(16) { i ->
                    Sticker("cloud_$i", "clouds", getStickerEmoji(i), "Sticker $i")
                }
            ),
            StickerPack(
                id = "hearts",
                name = "💕 Corazones",
                emoji = "💕",
                stickers = List(20) { i ->
                    Sticker("heart_$i", "hearts", getStickerEmoji(i), "Sticker $i")
                }
            )
        )
    }

    Column(modifier = modifier) {
        // Selector de packs
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            items(stickerPacks) { pack ->
                FilterChip(
                    selected = selectedPack == pack.id,
                    onClick = { selectedPack = pack.id },
                    label = { Text("${pack.emoji} ${pack.name}") },
                    leadingIcon = if (selectedPack == pack.id) {
                        {
                            Icon(
                                androidx.compose.material.icons.Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    } else null
                )
            }
        }

        Divider()

        // Stickers del pack seleccionado
        val currentPack = stickerPacks.find { it.id == selectedPack } ?: stickerPacks.first()
        
        Text(
            text = currentPack.name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(currentPack.stickers) { sticker ->
                StickerItem(
                    sticker = sticker,
                    onClick = { onStickerSelected(sticker) }
                )
            }
        }
    }
}

@Composable
private fun StickerItem(
    sticker: Sticker,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(64.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = sticker.emoji,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

private fun getStickerEmoji(index: Int): String {
    val emojis = listOf("💕", "💖", "💗", "💓", "💞", "💟", "❤️", "🧡", "💛", "💚", "💙", "💜", "🤎", "🖤", "🤍")
    return emojis[index % emojis.size]
}
