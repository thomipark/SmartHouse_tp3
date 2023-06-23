package com.example.smarthouse_tp3.screens

import com.example.smarthouse_tp3.R


sealed class MainScreen(val title: Int, val icon: Int, val route: Int) {
    object DevicesScreen : MainScreen(R.string.devices, R.drawable.screen_devices_icon,         R.string.devices)
    object RoutinesScreen : MainScreen(R.string.routines, R.drawable.screen_routines_icon,      R.string.routines)
    object PlacesScreen : MainScreen(R.string.places, R.drawable.screen_places_icon,            R.string.places)
    object FavoritesScreen : MainScreen(R.string.favorites, R.drawable.screen_favorites_icon,   R.string.favorites)
}


