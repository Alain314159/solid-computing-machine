package com.cerdita.app.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cerdita.app.data.local.database.entity.EventEntity
import com.cerdita.app.util.DateUtils

@Composable
fun EventCard(
    event: EventEntity,
    modifier: Modifier = Modifier,
    onDelete: (() -> Unit)? = null
) {
    val icon = when (event.type) {
        "birthday" -> "🎂"
        "anniversary" -> "💕"
        "event" -> "🎉"
        "unique" -> "🐷🤗🐨"
        else -> "📅"
    }
    
    val daysUntil = (event.date - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = event.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = DateUtils.formatTimestamp(event.date, "dd/MM/yyyy"),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                if (daysUntil >= 0) {
                    Text(
                        text = if (daysUntil == 0L) "¡Es hoy!" else "Faltan $daysUntil días",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (daysUntil <= 7) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
                    )
                }
            }
            
            onDelete?.let {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                        contentDescription = "Eliminar"
                    )
                }
            }
        }
    }
}
