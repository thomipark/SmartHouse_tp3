package com.example.smarthouse_tp3.ui

import com.example.smarthouse_tp3.Device
import com.example.smarthouse_tp3.DeviceLight

data class NavigationUiState(
    val selectedDevice: Device = DeviceLight("initial device")
)