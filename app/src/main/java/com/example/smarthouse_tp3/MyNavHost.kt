package com.example.smarthouse_tp3

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smarthouse_tp3.advanced_devices.DeviceConfigScreen
import com.example.smarthouse_tp3.ui.DevicesViewModel
import com.example.smarthouse_tp3.ui.NavigationViewModel
import com.example.smarthouse_tp3.ui.RoutinesViewModel

@Composable
fun MyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = stringResource(id = R.string.device_screen),
    navigationViewModel: NavigationViewModel = viewModel()
) {
    val deviceScreen = stringResource(id = R.string.device_screen)
    val placesScreen = stringResource(id = R.string.places_screen)
    val routinesScreen = stringResource(id = R.string.routines_screen)
    val favouritesScreen = stringResource(id = R.string.favorites_screen)
    val deviceConfigurationScreen = stringResource(id = R.string.device_configuration_screen)
    val routineConfigurationScreen = stringResource(id = R.string.routine_configuration_screen)

    val devicesViewModel: DevicesViewModel = viewModel()
    devicesViewModel.fetchDevices()

    val routinesViewModel: RoutinesViewModel = viewModel()


    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        val bottomPadding = Modifier.padding(0.dp, 0.dp, 0.dp, 56.dp)


        //MAIN SCREENS
        composable(routinesScreen) {
            RoutinesScreen(
                navigationViewModel = navigationViewModel,
                modifier = bottomPadding,
                devicesViewModel = devicesViewModel,
                routinesViewModel = routinesViewModel
            ) { navController.navigate(routineConfigurationScreen) }
        }

        composable(deviceScreen) {
            DeviceScreen(
                navigationViewModel = navigationViewModel,
                modifier = bottomPadding,
                devicesViewModel = devicesViewModel
            ) { navController.navigate(deviceConfigurationScreen) }
        }

        composable(favouritesScreen) {
            FavoritesScreen(
                navigationViewModel = navigationViewModel,
                modifier = bottomPadding,
                devicesViewModel = devicesViewModel
            ) { navController.navigate(deviceConfigurationScreen) }
        }

        composable(placesScreen) {
            PlacesScreen(
                navigationViewModel = navigationViewModel,
                modifier = bottomPadding,
                devicesViewModel = devicesViewModel
            ) { navController.navigate(deviceConfigurationScreen) }
        }


        //SCREEEN DE DEVICE CONFIG
        composable(deviceConfigurationScreen) {
            DeviceConfigScreen(navigationViewModel = navigationViewModel)
        }

        //SCREEN DE ADVANCED ROUTINES
        composable(routineConfigurationScreen) {
            RoutineConfigScreen(navigationViewModel = navigationViewModel, routinesViewModel = routinesViewModel, devicesViewModel = devicesViewModel)
        }
    }
}