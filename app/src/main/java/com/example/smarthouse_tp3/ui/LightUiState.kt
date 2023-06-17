package com.example.smarthouse_tp3.ui

import com.example.smarthouse_tp3.data.network.model.NetworkDevice

data class LightUiState (
    val id : String,
    val name : String,
    val status : String,
    val color : String,
    val brightness : Long
)
