package com.example.smarthouse_tp3.ui

import com.example.smarthouse_tp3.data.network.model.NetworkRoomList

data class RoomsUiState(
    val rooms: NetworkRoomList? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

val RoomsUiState.hasError: Boolean get() = message != null