package com.example.smarthouse_tp3

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smarthouse_tp3.advanced_devices.DeviceConfigScreen
import com.example.smarthouse_tp3.screens.DeviceScreen
import com.example.smarthouse_tp3.screens.FavoritesScreen
import com.example.smarthouse_tp3.screens.MainScreen
import com.example.smarthouse_tp3.screens.PlacesScreen
import com.example.smarthouse_tp3.screens.RoutinesScreen
import com.example.smarthouse_tp3.screens.deviceViewModelMaker
import com.example.smarthouse_tp3.ui.DeviceMap
import com.example.smarthouse_tp3.ui.DevicesViewModel
import com.example.smarthouse_tp3.ui.NavigationViewModel
import com.example.smarthouse_tp3.ui.RoomsViewModel
import com.example.smarthouse_tp3.ui.RoutinesViewModel
import kotlinx.coroutines.delay

@Composable
fun MyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = stringResource(id = R.string.device_screen),
    navigationViewModel: NavigationViewModel = viewModel(),
    deviceId: String? = null
) {
    val deviceScreen = stringResource(id = R.string.device_screen)
    val placesScreen = stringResource(id = R.string.places_screen)
    val routinesScreen = stringResource(id = R.string.routines_screen)
    val favouritesScreen = stringResource(id = R.string.favorites_screen)
    val deviceConfigurationScreen = stringResource(id = R.string.device_configuration_screen)
    val routineConfigurationScreen = stringResource(id = R.string.routine_configuration_screen)

    val devicesViewModel: DevicesViewModel = viewModel()
    val routinesViewModel: RoutinesViewModel = viewModel()


    NavHost(
        navController = navController, startDestination = startDestination, modifier = modifier
    ) {
        val bottomPadding = Modifier.padding(0.dp, 0.dp, 0.dp, 56.dp)

        //MAIN SCREENS
        composable(routinesScreen) {
            RoutinesScreen(
                modifier = bottomPadding,
                navigationViewModel = navigationViewModel,
                routinesViewModel = routinesViewModel
            ) { navController.navigate(routineConfigurationScreen) }
        }

        composable(deviceScreen) {
            LaunchedEffect(Unit) {
                devicesViewModel.fetchDevices()
            }

            val devicesUiState by devicesViewModel.uiState.collectAsState()
            devicesUiState.devices?.devices?.forEach {
                val myDevice =
                    it.id?.let { item -> deviceViewModelMaker(id = item, typeName = it.type?.name) }
                LaunchedEffect(Unit) {
                    delay(100)
                    myDevice?.fetchDevice()
                }
                if (myDevice != null) {
                    DeviceMap.map.getOrPut(it.id.toString()) {
                        myDevice.fetchDevice()
                    }
                }

            }
            DeviceScreen(
                navigationViewModel = navigationViewModel,
                modifier = bottomPadding,
                devicesViewModel = devicesViewModel,
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

            LaunchedEffect(Unit) {
                devicesViewModel.fetchDevices()
            }
            val devicesUiState by devicesViewModel.uiState.collectAsState()
            devicesUiState.devices?.devices?.forEach {
                val myDevice =
                    it.id?.let { item -> deviceViewModelMaker(id = item, typeName = it.type?.name) }
                LaunchedEffect(Unit) {
                    delay(100)
                    myDevice?.fetchDevice()
                }
                if (myDevice != null) {
                    DeviceMap.map.getOrPut(it.id.toString()) {
                        myDevice.fetchDevice()
                    }
                }

            }
            val roomsViewModel: RoomsViewModel = viewModel()
            roomsViewModel.fetchRooms()
            PlacesScreen(
                navigationViewModel = navigationViewModel,
                modifier = bottomPadding,
                devicesViewModel = devicesViewModel,
                roomsViewModel = roomsViewModel
            ) { navController.navigate(deviceConfigurationScreen) }
        }


        //SCREEEN DE DEVICE CONFIG
        composable(deviceConfigurationScreen) {
            DeviceConfigScreen(navigationViewModel = navigationViewModel)
        }

        //SCREEN DE ADVANCED ROUTINES
        composable(routineConfigurationScreen) {
            RoutineConfigScreen(
                navigationViewModel = navigationViewModel,
                routinesViewModel = routinesViewModel,
                devicesViewModel = devicesViewModel
            )
        }
    }

    DisposableEffect(deviceId) {
        if (deviceId != null) {
            navController.navigate(deviceScreen)
        }
        onDispose { /* Cleanup logic if needed */ }
    }
}

@Composable
fun TopBar(
    navController: NavController, navigationViewModel: NavigationViewModel = viewModel()
) {
    val currentRoute = navController.currentDestination?.route ?: ""
    val showBackIcon =
        currentRoute == stringResource(id = R.string.device_configuration_screen) || currentRoute == stringResource(
            id = R.string.routine_configuration_screen
        )


    val navigationUiState by navigationViewModel.uiState.collectAsState()
    val showIcons = currentRoute == stringResource(id = R.string.device_configuration_screen)

    navigationUiState.selectedDeviceViewModel?.let { navigationViewModel.updateNotification(it.getNotification()) }
    val notificationState = remember { mutableStateOf(navigationUiState.notification) }
    navigationUiState.selectedDeviceViewModel?.let { navigationViewModel.updateFavourite(it.getFavourite()) }
    val favouriteState = remember { mutableStateOf(navigationUiState.favourite) }

    val notificationContentDescription = if (notificationState.value) {
        "Notification On"
    } else {
        "Notification Off"
    }

    val favouriteContentDescription = if (favouriteState.value) {
        "Favourite On"
    } else {
        "Favourite Off"
    }


    val routeToIconMap = mapOf(
        stringResource(id = R.string.device_screen) to R.drawable.screen_devices_icon,
        stringResource(id = R.string.favorites_screen) to R.drawable.screen_favorites_icon,
        stringResource(id = R.string.places_screen) to R.drawable.screen_places_icon,
        stringResource(id = R.string.routines_screen) to R.drawable.screen_routines_icon,
    )


    if (showBackIcon) {
        TopAppBar(title = { /* Title content */ }, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    painter = painterResource(R.drawable.backarrow), contentDescription = "Back"
                )
            }
        }, actions = {
            if (showIcons) {
                Row {
                    IconButton(onClick = {
                        navigationViewModel.setFavourite()
                        navigationUiState.selectedDeviceViewModel?.setFavourite()

                        if (navigationUiState.favourite) {
                            navigationViewModel.removeDevicesFavoriteList()
                        } else {
                            navigationViewModel.addDevicesFavoriteList()
                        }
                    }) {
                        Icon(
                            painter = if (navigationUiState.favourite) {
                                painterResource(R.drawable.favourites_icon_on)
                            } else {
                                painterResource(R.drawable.favourites_icon_off)
                            },
                            contentDescription = favouriteContentDescription,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(40.dp)
                        )
                    }
                }
                Row {
                    IconButton(onClick = {
                        navigationViewModel.setNotification()
                        navigationUiState.selectedDeviceViewModel?.setNotification()

                        if (navigationUiState.notification) {
                            navigationViewModel.removeDevicesNotificationList()
                        } else {
                            navigationViewModel.addDevicesNotificationList()
                        }
                    }) {
                        Icon(
                            painter = if (navigationUiState.notification) {
                                painterResource(R.drawable.notifications_on)
                            } else {
                                painterResource(R.drawable.notifications_off)
                            },
                            contentDescription = notificationContentDescription,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(40.dp)
                        )
                    }
                }

            }
        }, backgroundColor = MaterialTheme.colors.primary
        )
    } else {
        Box(
            modifier = Modifier
                .height(56.dp)
                .background(MaterialTheme.colors.primary)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            routeToIconMap[currentRoute]?.let { iconRes ->
                val icon = ImageVector.vectorResource(iconRes)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier.padding(end = 8.dp), contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = currentRoute,
                            tint = MaterialTheme.colors.secondary
                        )
                    }

                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = currentRoute,
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.surface
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        MainScreen.DevicesScreen,
        MainScreen.PlacesScreen,
        MainScreen.RoutinesScreen,
        MainScreen.FavoritesScreen
    )

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isTablet = configuration.smallestScreenWidthDp >= 600

    val contentColor = MaterialTheme.colors.surface
    val selectedColor = MaterialTheme.colors.secondary
    val unselectedColor = contentColor.copy(alpha = LocalContentAlpha.current)

    val iconSize = if (isTablet) 30.dp else 25.dp

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = contentColor
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEachIndexed { index, item ->
            val route = stringResource(id = item.route)
            val isSelected = currentRoute == stringResource(id = item.route)

            BottomNavigationItem(
                selectedContentColor = selectedColor,
                unselectedContentColor = unselectedColor,
                icon = {
                    val tint = if (isSelected) {
                        selectedColor
                    } else {
                        unselectedColor
                    }
                    Icon(
                        imageVector = ImageVector.vectorResource(id = item.icon),
                        contentDescription = stringResource(item.title),
                        modifier = Modifier.size(iconSize),
                        tint = tint
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                alwaysShowLabel = true,
                selected = isSelected,
                onClick = {
                    navController.navigate(route) {
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}


