package com.cerdita.app.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cerdita.app.data.local.database.entity.MessageEntity
import com.cerdita.app.util.DateUtils

@Composable
fun MessageBubble(
    message: MessageEntity,
    isFromMe: Boolean,
    modifier: Modifier = Modifier
) {
    val statusIcon = when (message.status) {
        "pending" -> "⏳"
        "sent" -> "📤"
        "delivered" -> "✅"
        "read" -> "👁️"
        else -> "⏳"
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = if (isFromMe) Alignment.End else Alignment.Start
    ) {
        Surface(
            modifier = Modifier.widthIn(max = 280.dp),
            shape = MaterialTheme.shapes.large,
            color = if (isFromMe) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
            tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = message.content,
                    color = if (isFromMe) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    fontSize = 16.sp
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = DateUtils.formatTimestamp(message.timestamp, "HH:mm"),
                        fontSize = 12.sp,
                        color = if (isFromMe) {
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        }
                    )
                    
                    if (isFromMe) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = statusIcon,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
