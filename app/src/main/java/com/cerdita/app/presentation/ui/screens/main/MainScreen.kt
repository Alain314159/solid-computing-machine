package com.cerdita.app.presentation.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cerdita.app.presentation.ui.navigation.Screen
import com.cerdita.app.presentation.ui.screens.calendar.CalendarScreen
import com.cerdita.app.presentation.ui.screens.chat.ChatScreen
import com.cerdita.app.presentation.ui.screens.settings.SettingsScreen

sealed class BottomNavItem(
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String
) {
    object Chat : BottomNavItem("chat", Icons.Default.Chat, "Chat")
    object Calendar : BottomNavItem("calendar", Icons.Default.CalendarToday, "Calendario")
    object Settings : BottomNavItem("settings", Icons.Default.Settings, "Ajustes")
}

@Composable
fun MainScreen(
    onLogout: () -> Unit
) {
    val bottomNavController = rememberNavController()
    var selectedTab by remember { mutableStateOf(0) }
    
    val navItems = listOf(
        BottomNavItem.Chat,
        BottomNavItem.Calendar,
        BottomNavItem.Settings
    )
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            bottomNavController.navigate(item.route) {
                                popUpTo(bottomNavController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Chat.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavItem.Chat.route) {
                ChatScreen()
            }
            composable(BottomNavItem.Calendar.route) {
                CalendarScreen()
            }
            composable(BottomNavItem.Settings.route) {
                SettingsScreen(onLogout = onLogout)
            }
        }
    }
}
