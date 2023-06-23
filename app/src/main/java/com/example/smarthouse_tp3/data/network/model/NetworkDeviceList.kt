package com.example.smarthouse_tp3.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkDeviceList(
    @SerializedName("result") var devices: List<NetworkDevice> = listOf()
)