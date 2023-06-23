package com.example.smarthouse_tp3

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.smarthouse_tp3.data.network.model.NetworkAction
import com.example.smarthouse_tp3.data.network.model.NetworkRoutine
import com.example.smarthouse_tp3.ui.DeviceViewModel
import com.example.smarthouse_tp3.ui.DevicesViewModel
import com.example.smarthouse_tp3.ui.NavigationViewModel
import com.example.smarthouse_tp3.ui.RoutinesViewModel
import kotlinx.coroutines.delay

@Composable
fun RoutineConfigScreen(
    navigationViewModel: NavigationViewModel,
    routinesViewModel: RoutinesViewModel,
    devicesViewModel: DevicesViewModel
) {
    val navigationUiState by navigationViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            navigationUiState.selectedNetworkRoutine?.let { RoutineTopBar(it) }
        },
        content = {
            it
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                navigationUiState.selectedNetworkRoutine?.let { it1 -> RoutineBody(it1,routinesViewModel) }
            }
        }
    )
}

@Composable
fun RoutineTopBar(networkRoutine: NetworkRoutine) {
    if (networkRoutine.actions.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Devices & Actions",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
            Divider(
                color = if(!MaterialTheme.colors.isLight) Color.LightGray else Color.Black,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}


@Composable
fun AdvancedRoutineDeviceTile(
    deviceRoutineNetwork: DeviceRoutineNetwork,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colors.primaryVariant
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                deviceRoutineNetwork.networkDevice.name?.let {
                    Text(
                        text = it,   //getDeviceName
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                if (deviceRoutineNetwork.networkActionList.isNotEmpty()) {      //getACTIONS
                    for (action in deviceRoutineNetwork.networkActionList) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(if (MaterialTheme.colors.isLight) Color.White else Color.Black)
                            )

                            action.actionName?.let {
                                Text(
                                    text = it,  //Action getName
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun RoutineBody(
    networkRoutine: NetworkRoutine,
    routinesViewModel: RoutinesViewModel,
    modifier: Modifier = Modifier
) {
    val deviceRoutineNetworkList = createDeviceRoutineNetworks(networkRoutine)
    
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        if (networkRoutine.actions.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 325.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No devices linked to routine",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        } else {
            items(items = deviceRoutineNetworkList) { item ->
                AdvancedRoutineDeviceTile(deviceRoutineNetwork = item)
            }
        }
    }

    if (networkRoutine.actions.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
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
            FloatingActionButton(
                onClick = {
                    networkRoutine.id?.let { routinesViewModel.executeRoutine(it) }
                    isClicked = !isClicked
                          },
                modifier = Modifier
                    .size(128.dp)
                    .padding(4.dp),
                backgroundColor = Color.White
            ) {
                val playIconSize = 128.dp // Adjust the size of the icon
                val playIcon = painterResource(R.drawable.screen_routines_icon)
                val playDescription = "Play"

                Icon(
                    painter = playIcon,
                    contentDescription = playDescription,
                    tint = tint,
                    modifier = Modifier.size(playIconSize)
                )
            }
        }
    }
}


/**
 * Creates a list of DeviceRoutineNetworks which contains a Device with its actions to execute
 */
fun createDeviceRoutineNetworks(networkRoutines: NetworkRoutine): List<DeviceRoutineNetwork> {
    val deviceActionMap = mutableMapOf<String,DeviceRoutineNetwork>()

    for (action in networkRoutines.actions) {
        action.device?.id?.let {
            action.device?.let { DeviceRoutineNetwork(it) }?.let { it1 ->
                deviceActionMap.putIfAbsent(
                    it,
                    it1
                )
            }
        }
        deviceActionMap[action.device?.id]?.addAction(action)
    }

    return deviceActionMap.values.toList()
}
