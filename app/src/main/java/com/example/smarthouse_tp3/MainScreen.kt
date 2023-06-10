package com.example.smarthouse_tp3

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MainScreen(val title: String, val icon: ImageVector, val route: String) {
    object DevicesScreen: MainScreen("Devices", Icons.Filled.Home, "Devices")
    object RoutinesScreen: MainScreen("Routines", Icons.Filled.Favorite, "Routines")
    object PlacesScreen: MainScreen("Places", Icons.Filled.Face, "Places")
}