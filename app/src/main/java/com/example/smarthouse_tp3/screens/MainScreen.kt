package com.example.smarthouse_tp3.screens

import com.example.smarthouse_tp3.R


sealed class MainScreen(val title: String, val icon: Int, val route: String) {
    object DevicesScreen : MainScreen("Devices", R.drawable.screen_devices_icon, "Devices")
    object RoutinesScreen : MainScreen("Routines", R.drawable.screen_routines_icon, "Routines")
    object PlacesScreen : MainScreen("Places", R.drawable.screen_places_icon, "Places")
    object FavoritesScreen : MainScreen("Favorites", R.drawable.screen_favorites_icon, "Favorites")
}


