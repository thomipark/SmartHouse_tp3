package com.example.smarthouse_tp3.ui

import com.example.smarthouse_tp3.data.network.model.NetworkRoutineList

data class RoutinesUiState (
    val networkRoutineList : NetworkRoutineList?   = null,
    val isLoading   : Boolean               = false,
    val message     : String            ?   = null
)

val RoutinesUiState.hasError : Boolean get() = message != null