package com.example.smarthouse_tp3.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkRoomList (
    @SerializedName("result" ) var rooms : List<NetworkDeviceRoom> = listOf()
)
