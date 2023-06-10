package com.example.smarthouse_tp3

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "Devices"
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        // Main Screens Devices - Routines - Places
        composable("Devices") {
            DeviceScreen (
                onNavigateToRoutinesScreen = { navController.navigate("Routines") },
                onNavigateToPlacesScreen = { navController.navigate("Places") }
            )
        }
        
        composable("Routines"){
            RoutinesScreen(
                onNavigateToDevicesScreen = { navController.navigate("Devices") }
            )
        }

        composable("Places"){
            PlacesScreen(
                onNavigateToDevicesScreen = { navController.navigate("Devices") }
            )
        }
    }
}