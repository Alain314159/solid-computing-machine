package com.cerdita.app.presentation.ui/screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cerdita.app.presentation.ui.components.common.CerditaButton
import com.cerdita.app.presentation.ui.components.common.CerditaCard
import com.cerdita.app.presentation.ui.components.settings.SettingsItemWithNavigation
import com.cerdita.app.presentation.ui.components.settings.SettingsItemWithSwitch
import com.cerdita.app.presentation.ui.components.settings.SettingsSectionDivider
import com.cerdita.app.presentation.ui.components.settings.SettingsSectionTitle
import com.cerdita.app.presentation.viewmodel.SettingsViewModel

/**
 * Pantalla de configuración de privacidad
 * 
 * Opciones:
 * - Bloqueo con biometría
 * - Bloqueo con PIN
 * - Ocultar contenido en notificaciones
 * - Ocultar contenido en pantalla de bloqueo
 * - Exportar datos encriptados
 * - Borrar todos los datos
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacySettingsScreen(
    onNavigateBack: () -> Unit,
    onShowLockScreen: () -> Unit,
    onExportData: () -> Unit,
    onClearData: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.settingsState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Privacidad") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Bloqueo de App
            CerditaCard {
                SettingsSectionTitle(title = "Bloqueo de Seguridad")
                
                SettingsItemWithSwitch(
                    icon = Icons.Default.Fingerprint,
                    title = "Bloqueo biométrico",
                    description = "Usar huella o rostro para desbloquear",
                    checked = uiState.biometricLockEnabled,
                    onCheckedChange = { viewModel.setBiometricLockEnabled(it) }
                )

                SettingsSectionDivider()

                SettingsItemWithSwitch(
                    icon = Icons.Default.Lock,
                    title = "Bloqueo con PIN",
                    description = "Usar PIN de 4 dígitos para desbloquear",
                    checked = uiState.pinLockEnabled,
                    onCheckedChange = { viewModel.setPinLockEnabled(it) }
                )

                if (uiState.biometricLockEnabled || uiState.pinLockEnabled) {
                    SettingsSectionDivider()
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    CerditaButton(
                        onClick = onShowLockScreen,
                        text = "🔐 Configurar bloqueo",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Notificaciones
            CerditaCard {
                SettingsSectionTitle(title = "Notificaciones")
                
                SettingsItemWithSwitch(
                    icon = Icons.Default.Notifications,
                    title = "Ocultar en pantalla de bloqueo",
                    description = "No mostrar contenido en pantalla bloqueada",
                    checked = uiState.hideNotificationsOnLockScreen,
                    onCheckedChange = { viewModel.setHideNotificationsOnLockScreen(it) }
                )

                SettingsSectionDivider()

                SettingsItemWithSwitch(
                    icon = Icons.Default.Notifications,
                    title = "Ocultar contenido",
                    description = "Mostrar solo 'Nueva notificación'",
                    checked = uiState.hideNotificationContent,
                    onCheckedChange = { viewModel.setHideNotificationContent(it) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Datos
            CerditaCard {
                SettingsSectionTitle(title = "Gestión de Datos")
                
                SettingsItemWithNavigation(
                    icon = Icons.Default.Lock,
                    title = "Exportar datos encriptados",
                    description = "Crear backup seguro de tus datos",
                    onClick = onExportData
                )

                SettingsSectionDivider()

                SettingsItemWithNavigation(
                    icon = Icons.Default.Lock,
                    title = "Borrar todos los datos",
                    description = "Eliminar cuenta y datos locales",
                    onClick = onClearData
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Advertencia
            CerditaCard(
                backgroundColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f),
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            ) {
                Text(
                    text = "⚠️ Importante",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Al borrar todos los datos, perderás tu sesión y mensajes. Esta acción no se puede deshacer.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.8f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
