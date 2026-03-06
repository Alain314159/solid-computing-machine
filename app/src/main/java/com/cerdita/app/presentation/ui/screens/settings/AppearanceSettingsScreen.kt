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
import androidx.compose.material.icons.filled.Palette
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
import com.cerdita.app.presentation.ui.components.common.CerditaCard
import com.cerdita.app.presentation.ui.components.settings.SettingsItemWithSwitch
import com.cerdita.app.presentation.ui.components.settings.SettingsSectionDivider
import com.cerdita.app.presentation.ui.components.settings.SettingsSectionTitle
import com.cerdita.app.presentation.ui.components.ThemeSelector
import com.cerdita.app.presentation.viewmodel.SettingsViewModel

/**
 * Pantalla de configuración de apariencia
 * 
 * Opciones:
 * - Selector de tema (Cerdita, Koalita, Flores, Mix)
 * - Modo oscuro
 * - Tamaño de fuente
 * - Animaciones (activar/desactivar)
 * - Intensidad de efectos románticos
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceSettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.settingsState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Apariencia") },
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

            // Selector de Tema
            CerditaCard {
                Text(
                    text = "🎨 Tema",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(12.dp))
                ThemeSelector(
                    selectedTheme = uiState.currentTheme,
                    onThemeSelected = { viewModel.setTheme(it) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Configuración de Modo Oscuro
            CerditaCard {
                SettingsSectionTitle(title = "Modo Oscuro")
                SettingsItemWithSwitch(
                    icon = Icons.Default.Palette,
                    title = "Usar modo oscuro",
                    description = "Activar tema oscuro para la app",
                    checked = uiState.darkModeEnabled,
                    onCheckedChange = { viewModel.setDarkModeEnabled(it) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Configuración de Animaciones
            CerditaCard {
                SettingsSectionTitle(title = "Animaciones")
                SettingsItemWithSwitch(
                    icon = Icons.Default.Palette,
                    title = "Activar animaciones",
                    description = "Mostrar animaciones en la interfaz",
                    checked = uiState.animationsEnabled,
                    onCheckedChange = { viewModel.setAnimationsEnabled(it) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Configuración de Efectos Románticos
            CerditaCard {
                SettingsSectionTitle(title = "Efectos Románticos")
                SettingsItemWithSwitch(
                    icon = Icons.Default.Palette,
                    title = "Activar efectos",
                    description = "Mostrar corazones, estrellas, etc.",
                    checked = uiState.romanticEffectsEnabled,
                    onCheckedChange = { viewModel.setRomanticEffectsEnabled(it) }
                )

                SettingsSectionDivider()

                SettingsSectionTitle(title = "Intensidad de efectos")
                SettingsItemWithSwitch(
                    icon = Icons.Default.Palette,
                    title = "Efectos intensos",
                    description = "Más partículas y animaciones",
                    checked = uiState.intenseEffectsEnabled,
                    onCheckedChange = { viewModel.setIntenseEffectsEnabled(it) }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
