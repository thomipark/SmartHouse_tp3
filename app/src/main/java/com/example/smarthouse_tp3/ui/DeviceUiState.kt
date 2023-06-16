package com.example.smarthouse_tp3.ui

import com.example.smarthouse_tp3.data.network.model.NetworkDevice

data class DeviceUiState(
    val device      : NetworkDevice?   = null,
    val isLoading   : Boolean               = false,
    val message     : String            ?   = null
)

val DeviceUiState.hasError : Boolean get() = message != null