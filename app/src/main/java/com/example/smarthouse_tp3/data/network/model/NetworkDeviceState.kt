package com.example.smarthouse_tp3.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkDeviceState (
    @SerializedName("status"      ) var status      : String? = null,
)