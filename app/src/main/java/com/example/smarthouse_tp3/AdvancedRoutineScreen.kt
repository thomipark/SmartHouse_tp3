package com.example.smarthouse_tp3

import android.provider.Settings.Global.getString
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.data.network.model.NetworkRoutine
import com.example.smarthouse_tp3.screens.fadingEdges
import com.example.smarthouse_tp3.ui.DevicesViewModel
import com.example.smarthouse_tp3.ui.NavigationViewModel
import com.example.smarthouse_tp3.ui.RoomsViewModel
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
                navigationUiState.selectedNetworkRoutine?.let { it1 ->
                    RoutineBody(
                        it1,
                        routinesViewModel
                    )
                }
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
                    text = stringResource(id = R.string.device_and_actions),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
            Divider(
                color = if (!MaterialTheme.colors.isLight) Color.LightGray else Color.Black,
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

    val actionMap: MutableMap<String, Int> = HashMap()
    actionMap["open"] = R.string.action_open
    actionMap["close"] = R.string.action_close
    actionMap["dispense"] = R.string.action_dispense
    actionMap["setColor"] = R.string.action_setColor
    actionMap["setBrightness"] = R.string.action_setBrightness
    actionMap["turnOn"] = R.string.action_turnOn
    actionMap["turnOff"] = R.string.action_turnOff
    actionMap["setTemperature"] = R.string.action_setTemperature
    actionMap["setHeat"] = R.string.action_setHeat
    actionMap["setGrill"] = R.string.action_setGrill
    actionMap["setConvection"] = R.string.action_setConvection
    actionMap["setTemperature"] = R.string.action_setTemperature
    actionMap["setMode"] = R.string.action_setMode
    actionMap["setFanSpeed"] = R.string.action_setFanSpeed
    actionMap["setVerticalSwing"] = R.string.action_setVerticalSwing
    actionMap["setHorizontalSwing"] = R.string.action_setHorizontalSwing
    actionMap["setLocation"] = R.string.action_setLocation
    actionMap["pause"] = R.string.action_pause
    actionMap["dock"] = R.string.action_dock
    actionMap["start"] = R.string.action_start

    val roomsViewModel: RoomsViewModel = viewModel()

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

                            action.actionName?.let { actionName ->
                                val actionResId = actionMap[actionName]
                                val actionText =
                                    actionResId?.let { stringResource(it) } ?: actionName

                                val additionalText = when (actionResId) {
                                    R.string.action_setTemperature -> " ${
                                        action.params.toString().replace(
                                            "[\\[\\]]".toRegex(), ""
                                        )
                                    }°C"

                                    R.string.action_setHeat -> " ${
                                        action.params.toString().replace(
                                            "[\\[\\]]".toRegex(), ""
                                        )
                                    }"

                                    R.string.action_setConvection -> " ${
                                        action.params.toString().replace(
                                            "[\\[\\]]".toRegex(), ""
                                        )
                                    }"

                                    R.string.action_setGrill -> " ${
                                        action.params.toString().replace(
                                            "[\\[\\]]".toRegex(), ""
                                        )
                                    }"

                                    R.string.action_dispense -> " ${
                                        action.params.toString().replace(
                                            "[\\[\\]]".toRegex(), ""
                                        )
                                    }"

                                    R.string.action_setMode -> " ${
                                        action.params.toString().replace(
                                            "[\\[\\]]".toRegex(), ""
                                        )
                                    }"

                                    R.string.action_setBrightness -> " ${
                                        action.params.toString().substring(1)
                                            .replace("\\..*".toRegex(), "")
                                    }%"

                                    R.string.action_setColor -> " "

                                    R.string.action_setHorizontalSwing -> " ${
                                        if (action.params.toString().replace(
                                                "[\\[\\]]".toRegex(), ""
                                            ) != "null"
                                        ) action.params.toString().replace(
                                            "[\\[\\]]".toRegex(), ""
                                        ) + "°" else "auto"
                                    }"

                                    R.string.action_setVerticalSwing -> " ${
                                        if (action.params.toString().replace(
                                                "[\\[\\]]".toRegex(), ""
                                            ) != "null"
                                        ) action.params.toString().replace(
                                            "[\\[\\]]".toRegex(), ""
                                        ) + "°" else "auto"
                                    }"

                                    R.string.action_setLocation -> {
                                        val roomId = roomsViewModel.getRoomFromId(
                                            action.params.toString().replace(
                                                "[\\[\\]]".toRegex(), ""
                                            )
                                        ).name.toString()

                                        if (roomId != "null" && roomId != "")
                                            " to $roomId" else ""
                                    }

                                    else -> ""
                                }

                                Text(
                                    text = actionText + additionalText,
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                                if (actionResId == R.string.action_setColor) {
                                    val colorString = "#FF${
                                        action.params.toString().replace("[\\[\\]]".toRegex(), "")
                                    }"

                                    Card(
                                        modifier = Modifier
                                            .size(18.dp)
                                            .offset(5.dp, 0.9.dp),
                                        backgroundColor = Color(
                                            android.graphics.Color.parseColor(
                                                colorString
                                            )
                                        ),
                                        border = BorderStroke(0.3.dp, Color.Black),
                                    ) {}
                                }
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
                        text = stringResource(id = R.string.no_devices_linked_to_routine),
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
    val deviceActionMap = mutableMapOf<String, DeviceRoutineNetwork>()

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
