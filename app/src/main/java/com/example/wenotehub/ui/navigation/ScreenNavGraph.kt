package com.example.wenotehub.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenNavGraph(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object HomeScreen :
        ScreenNavGraph(
            route = "home_screen",
            title = "home",
            icon = Icons.Default.Home
        )

    object FinishedScreen :
        ScreenNavGraph(
            route = "finished_screen",
            title = "Finished",
            icon = Icons.Default.List
        )

    object SettingsScreen :
        ScreenNavGraph(
            route = "settings_screen",
            title = "Settings",
            icon = Icons.Default.Settings
        )

    object CreateScreen :
        ScreenNavGraph(
            route = "create_screen",
            title = "Create",
            icon = Icons.Default.Add
        )
}

