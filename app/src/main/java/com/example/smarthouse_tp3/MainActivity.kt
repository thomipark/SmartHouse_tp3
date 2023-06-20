package com.example.smarthouse_tp3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smarthouse_tp3.ui.NavigationViewModel
import com.example.smarthouse_tp3.ui.theme.SmartHouse_tp3Theme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHouse_tp3Theme {
                val navController = rememberNavController()
                var showBottomBar by rememberSaveable { mutableStateOf(true) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val navigationViewModel: NavigationViewModel = viewModel()
                val deviceConfigScreen = stringResource(id = R.string.device_configuration_screen)
                val routineConfigScreen = stringResource(id = R.string.routine_configuration_screen)

                showBottomBar = when (navBackStackEntry?.destination?.route) {
                    deviceConfigScreen -> false
                    routineConfigScreen -> false
                    else -> true // in all other cases, show the bottom bar
                }

                Scaffold(
                    bottomBar = { if (showBottomBar) BottomBar(navController = navController) },
                    topBar = {
                        TopBar(
                            navController = navController,
                            navigationViewModel = navigationViewModel
                        )
                    }
                ) {
                    MyNavHost(
                        navController = navController,
                        navigationViewModel = navigationViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavController
) {
    val items = listOf(
        MainScreen.DevicesScreen,
        MainScreen.PlacesScreen,
        MainScreen.RoutinesScreen,
        MainScreen.FavoritesScreen
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = MaterialTheme.colors.surface,
                icon = {
                    val tint = if (currentRoute == item.route) {
                        MaterialTheme.colors.secondary
                    } else {
                        LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                    }
                    Icon(
                        imageVector = ImageVector.vectorResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(25.dp),
                        tint = tint
                    )
                },
                label = { Text(text = item.title) },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
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


@Composable
fun TopBar(
    navController: NavController,
    navigationViewModel: NavigationViewModel = viewModel()
) {
    val currentRoute = navController.currentDestination?.route ?: ""
    val hideBackIcon =
        currentRoute == stringResource(id = R.string.device_screen) || currentRoute == stringResource(
            id = R.string.favorites_screen
        ) || currentRoute == stringResource(id = R.string.places_screen) || currentRoute == stringResource(
            id = R.string.routines_screen
        )
    val navigationUiState by navigationViewModel.uiState.collectAsState()

    val routeToIconMap = mapOf(
        stringResource(id = R.string.device_screen) to R.drawable.screen_devices_icon,
        stringResource(id = R.string.favorites_screen) to R.drawable.screen_favorites_icon,
        stringResource(id = R.string.places_screen) to R.drawable.screen_places_icon,
        stringResource(id = R.string.routines_screen) to R.drawable.screen_routines_icon,
    )


    if (!hideBackIcon) {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.primary,
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            title = {
                when (currentRoute) {
                    stringResource(id = R.string.routine_configuration_screen) -> {
                        navigationUiState.selectedRoutine?.let {
                            Text(
                                text = it.getRoutineName(),
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    stringResource(id = R.string.device_configuration_screen) -> {
                        navigationUiState.selectedDevice?.let {
                            Text(
                                text = it.getName(),
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            },
        )
    } else {
        Box(
            modifier = Modifier
                .height(56.dp)
                .background(MaterialTheme.colors.primary)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            routeToIconMap[currentRoute]?.let { iconRes ->
                val icon = ImageVector.vectorResource(iconRes)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        contentAlignment = Alignment.Center
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

/* --------------------- LAS PREVIEW EMPIEZAN ACA ------------------*/



@Preview
@Composable
fun BottomBarPreview() {
    val navController = rememberNavController()
    BottomBar(navController = navController)
}
