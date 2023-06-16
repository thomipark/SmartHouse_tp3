package com.example.smarthouse_tp3.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkDevice (
    @SerializedName("id"    ) var id    : String? = null,
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("type"  ) var type  : NetworkDeviceType?   = NetworkDeviceType(),
    @SerializedName("state" ) var state : NetworkDeviceState?  = NetworkDeviceState(),
    @SerializedName("meta"  ) var meta  : NetworkMeta?   = NetworkMeta()
)

