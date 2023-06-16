package com.example.smarthouse_tp3.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkAction (
    @SerializedName("device"     ) var device     : Int?              = null,
    @SerializedName("actionName" ) var actionName : String?           = null,
    @SerializedName("params"     ) var params     : List<String> = listOf(),
    @SerializedName("meta"       ) var meta       : NetworkMeta?      = NetworkMeta()
)

