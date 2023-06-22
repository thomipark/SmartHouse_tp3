package com.example.smarthouse_tp3.ui

import androidx.compose.ui.graphics.Color
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceRoom
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceState
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceType
import com.example.smarthouse_tp3.data.network.model.NetworkMeta

data class DeviceUiState(
    //val device      : NetworkDevice?    = null,
    val favourite: Boolean = false,
    val notification: Boolean = false,
    val isLoading: Boolean = false,
    val message: String? = null,
    val switchState: Boolean = false,
    val id: String? = null,
    val name: String? = null,
    val type: NetworkDeviceType? = NetworkDeviceType(),
    val state: NetworkDeviceState? = NetworkDeviceState(),
    val deviceIconColor: Color = Color.Black,
    val deviceIcon: Int? = null,
    val room: NetworkDeviceRoom? = NetworkDeviceRoom(),
    val meta: NetworkMeta? = NetworkMeta(),
)

val DeviceUiState.hasError: Boolean get() = message != null