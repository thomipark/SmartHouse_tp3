package com.example.smarthouse_tp3.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkDeviceType(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("powerUsage") var powerUsage: Int? = null
)