package com.example.smarthouse_tp3

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource


sealed class MainScreen(val title: String, val icon: Int, val route: String) {
    object DevicesScreen: MainScreen("Devices", R.drawable.screen_devices_icon, "Devices")
    object RoutinesScreen: MainScreen("Routines", R.drawable.screen_routines_icon, "Routines")
    object PlacesScreen: MainScreen("Places", R.drawable.screen_places_icon, "Places")
    object FavouritesScreen: MainScreen("Favourites", R.drawable.screen_favourites_icon, "Favourites")
}


