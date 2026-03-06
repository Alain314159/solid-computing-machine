package com.cerdita.app.presentation.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object Chat : Screen("chat")
    object Calendar : Screen("calendar")
    object Settings : Screen("settings")
    object Profile : Screen("profile")
}
