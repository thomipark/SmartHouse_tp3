package com.example.smarthouse_tp3.ui

import com.example.smarthouse_tp3.Device
import com.example.smarthouse_tp3.Routine
import com.example.smarthouse_tp3.data.network.model.NetworkRoutine

data class NavigationUiState(
    val selectedDevice: Device? = null,
    val selectedRoutine: Routine? = null,
    val selectedDeviceViewModel: DeviceViewModel? = null,
    val selectedNetworkRoutine: NetworkRoutine? = null,
    val firstTime : Boolean = true
)