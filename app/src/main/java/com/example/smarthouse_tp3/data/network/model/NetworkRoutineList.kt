package com.example.smarthouse_tp3.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkRoutineList(
    @SerializedName("result") var routines: List<NetworkRoutine> = listOf()
)

