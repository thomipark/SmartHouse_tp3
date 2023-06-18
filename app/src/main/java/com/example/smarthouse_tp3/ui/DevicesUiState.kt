package com.example.smarthouse_tp3.ui

import com.example.smarthouse_tp3.data.network.model.NetworkDeviceList

data class DevicesUiState(
    val devices: NetworkDeviceList? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

val DevicesUiState.hasError: Boolean get() = message != null