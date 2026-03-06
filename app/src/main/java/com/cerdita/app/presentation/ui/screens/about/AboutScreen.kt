package com.cerdita.app.presentation.ui.screens.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cerdita.app.BuildConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val versionName = remember { BuildConfig.VERSION_NAME }
    val versionCode = remember { BuildConfig.VERSION_CODE }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ℹ️ Acerca de",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Text(
                text = "🐷💕🐨",
                fontSize = 72.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Cerdita 💕",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "La app de chat romántico para parejas",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Versión $versionName ($versionCode)",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Información
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Información",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AboutInfoItem(
                        label = "Desarrollado con",
                        value = "Kotlin + Jetpack Compose"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    AboutInfoItem(
                        label = "Protocolo",
                        value = "Matrix (matrix.org)"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    AboutInfoItem(
                        label = "Notificaciones",
                        value = "Ntfy.sh"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    AboutInfoItem(
                        label = "Licencia",
                        value = "MIT"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Características
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Características",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    FeatureItem("💬 Chat de texto en tiempo real")
                    FeatureItem("💕 Efectos románticos (50+ palabras)")
                    FeatureItem("🎨 8 fondos de chat animados")
                    FeatureItem("🎙️ Notas de voz con visualizador")
                    FeatureItem("📅 Calendario de fechas especiales")
                    FeatureItem("🐷🤗🐨 Botón de abrazo animado")
                    FeatureItem("🎭 Stickers románticos (104+)")
                    FeatureItem("🔔 Notificaciones push con Ntfy")
                    FeatureItem("🔐 Autenticación biométrica")
                    FeatureItem("💾 Funcionamiento offline")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Enlaces
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(
                    onClick = {
                        openUrl(context, "https://github.com/Alain314159/solid-computing-machine")
                    }
                ) {
                    Text("GitHub")
                }

                TextButton(
                    onClick = {
                        openUrl(context, "https://matrix.org")
                    }
                ) {
                    Text("Matrix")
                }

                TextButton(
                    onClick = {
                        openUrl(context, "https://ntfy.sh")
                    }
                ) {
                    Text("Ntfy")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Hecho con 💕 para parejas enamoradas",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }
}

@Composable
private fun AboutInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun FeatureItem(feature: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = feature,
            fontSize = 14.sp
        )
    }
}

private fun openUrl(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (e: Exception) {
        // No hay navegador disponible
    }
}
