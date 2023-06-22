package com.example.smarthouse_tp3.screens


import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.Action
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.Routine
import com.example.smarthouse_tp3.RoutineDevice
import com.example.smarthouse_tp3.createDeviceRoutineNetworks
import com.example.smarthouse_tp3.data.network.model.NetworkRoutine
import com.example.smarthouse_tp3.ui.DevicesViewModel
import com.example.smarthouse_tp3.ui.NavigationViewModel
import com.example.smarthouse_tp3.ui.RoutinesViewModel
import com.example.smarthouse_tp3.ui.theme.SmartHouse_tp3Theme
import kotlinx.coroutines.delay

/***
 * Pantalla dedicada a Routines.
 */
@Composable
fun RoutinesScreen(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel,
    devicesViewModel: DevicesViewModel,
    routinesViewModel: RoutinesViewModel,
    onNavigateToConfigScreen: () -> Unit
) {

    routinesViewModel.fetchRoutines()
    Column(
        modifier = modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
    ) {
        SmallRoutineTilesRow(
            navigationViewModel = navigationViewModel,
            onNavigateToConfigScreen = onNavigateToConfigScreen,
            routinesViewModel = routinesViewModel,
            devicesViewModel = devicesViewModel
        )
    }
}

/**
 * Genera un small tile de routine horizontal.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SmallRoutineTile(
    modifier: Modifier = Modifier,
    routine: Routine = routine1,
    networkRoutine: NetworkRoutine,
    routinesViewModel: RoutinesViewModel,
    navigationViewModel: NavigationViewModel,
    onNavigateToConfigScreen: () -> Unit
) {

    Surface(
        shape = MaterialTheme.shapes.small, modifier = modifier
    ) {
        Card(modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.primaryVariant,
            onClick = {
                navigationViewModel.selectNewRoutine(routine)
                navigationViewModel.selectNewNetworkRoutine(networkRoutine)
                onNavigateToConfigScreen()
        }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(0.5f),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxSize()
                            .weight(0.45f),
                        contentAlignment = Alignment.Center
                    ) {
                        networkRoutine.name?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.h5,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis, // Display the text in a single line
                            )
                        }
                    }
                }
                playButton(networkRoutine = networkRoutine, routinesViewModel = routinesViewModel)
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SmallRoutineTileExtended(
    modifier: Modifier = Modifier,
    networkRoutine: NetworkRoutine,
    navigationViewModel: NavigationViewModel,
    routinesViewModel: RoutinesViewModel,
    onNavigateToConfigScreen: () -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium, modifier = modifier
    ) {
        Card(modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(128.dp),
            backgroundColor = MaterialTheme.colors.primaryVariant, onClick = {
            navigationViewModel.selectNewNetworkRoutine(networkRoutine)
            onNavigateToConfigScreen()
        }) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    networkRoutine.name?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis, // Display the text in a single line
                        )
                    }
                    val deviceRoutineNetworkList = createDeviceRoutineNetworks(networkRoutine)
                    var deviceNames = ""
                    deviceRoutineNetworkList.forEach { deviceNames += it.networkDevice.name + ", " }
                    deviceNames = deviceNames.dropLast(2) // Remove the last ", "

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp, 16.dp, 8.dp, 0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color.Black)
                                .padding(32.dp, 0.dp, 32.dp, 0.dp)
                        )
                        Text(
                            text = deviceNames,
                            style = MaterialTheme.typography.h6,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp) // Add padding to create space between the box and text
                        )
                    }
                }
                playButton(networkRoutine = networkRoutine, routinesViewModel = routinesViewModel)
            }
        }
    }
}

@Composable fun playButton(
    networkRoutine: NetworkRoutine,
    routinesViewModel: RoutinesViewModel
){
    var isClicked by remember { mutableStateOf(false) }

    val tint by animateColorAsState(
        if (isClicked) MaterialTheme.colors.secondary else Color.Black
    )
    LaunchedEffect(isClicked) {
        if (isClicked) {
            delay(150)
            isClicked = false
        }
    }

    IconButton(
        onClick = {
            isClicked = !isClicked
            networkRoutine.id?.let { routinesViewModel.executeRoutine(it) }
        },
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        val playIconSize = 40.dp // Adjust the size of the icon
        val playIcon = painterResource(R.drawable.screen_routines_icon)

        Icon(
            painter = playIcon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(playIconSize)
        )
    }
}


@Composable
fun SmallRoutineTilesRow(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel,
    routinesViewModel: RoutinesViewModel,
    devicesViewModel: DevicesViewModel,
    onNavigateToConfigScreen: () -> Unit
) {
    val isHorizontal = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val routinesUiState by routinesViewModel.uiState.collectAsState()


    if (isHorizontal) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            routinesUiState.networkRoutineList?.let { routine ->
                items(items = routine.routines) {
                    SmallRoutineTile(
                        navigationViewModel = navigationViewModel,
                        networkRoutine = it,
                        routinesViewModel = routinesViewModel,
                        onNavigateToConfigScreen = onNavigateToConfigScreen
                    )
                }
            }
        }
    } else {
        if (routinesUiState.networkRoutineList != null) {
            LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            routinesUiState.networkRoutineList?.let { routine ->
                items(items = routine.routines) {
                    SmallRoutineTile(
                        navigationViewModel = navigationViewModel,
                        networkRoutine = it,
                        routinesViewModel = routinesViewModel,
                        onNavigateToConfigScreen = onNavigateToConfigScreen
                    )
                }
            }
        } } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No routines added",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

    }
}


//-------- A PARTIR DE ACA ESTAN LAS PREVIEW ------------------
@Preview(showBackground = false)
@Composable
fun SmallRoutineTilePreview() {
    SmartHouse_tp3Theme {
        SmallRoutineTile(
            routine = Routine("Morning Routine"),
            modifier = Modifier.padding(8.dp),
            routinesViewModel = viewModel(),
            onNavigateToConfigScreen = {},
            networkRoutine = NetworkRoutine(),
            navigationViewModel = NavigationViewModel()
        )
    }
}

@Preview(showBackground = false)
@Composable
fun SmallRoutineTileExtendedPreview() {
    LocalConfiguration.current.orientation = Configuration.ORIENTATION_LANDSCAPE
    SmartHouse_tp3Theme {
        SmallRoutineTileExtended(
            networkRoutine = NetworkRoutine(),
            modifier = Modifier.padding(8.dp),
            onNavigateToConfigScreen = {},
            routinesViewModel = viewModel(),
            navigationViewModel = NavigationViewModel()
        )
    }
}
@Preview
@Composable
fun SmallRoutineTileRowPreview() {
    SmallRoutineTilesRow(
        navigationViewModel = NavigationViewModel(),
        onNavigateToConfigScreen = {},
        routinesViewModel = RoutinesViewModel(),
        devicesViewModel = DevicesViewModel()
    )
}


val routineDevice1Action1 = Action("turn on")
val routineDevice1Action2 = Action("set temperature to 220 C")
val routineDevice1 = RoutineDevice("Oven", listOf(routineDevice1Action1, routineDevice1Action2))

val routineDevice2Action1 = Action("turn on")
val routineDevice2Action2 = Action("set color to RED")
val routineDevice2 = RoutineDevice("Light", listOf(routineDevice2Action1, routineDevice2Action2))

val routine1 = Routine("Afternoon Routine", listOf(routineDevice1, routineDevice2))


val smallRoutinesTileData = listOf(
    routine1, routine1, routine1, routine1, routine1, routine1, routine1, routine1
)