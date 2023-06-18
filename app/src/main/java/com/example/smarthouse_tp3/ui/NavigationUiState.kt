package com.example.smarthouse_tp3.ui

import com.example.smarthouse_tp3.Device
import com.example.smarthouse_tp3.DeviceLight
import com.example.smarthouse_tp3.Routine

data class NavigationUiState(
    val selectedDevice: Device? = null,
    val selectedRoutine: Routine? = null
)