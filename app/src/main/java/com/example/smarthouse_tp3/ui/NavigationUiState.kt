package com.example.smarthouse_tp3.ui

import com.example.smarthouse_tp3.Device
import com.example.smarthouse_tp3.Routine

data class NavigationUiState(
    val selectedDevice: Device? = null,
    val selectedRoutine: Routine? = null,
    val selectedDeviceViewModel: DeviceViewModel? = null,
    val firstTime : Boolean = true
)