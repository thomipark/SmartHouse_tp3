package com.example.smarthouse_tp3.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkRoutine (
    @SerializedName("id"      ) var id      : String?            = null,
    @SerializedName("name"    ) var name    : String?            = null,
    @SerializedName("actions" ) var actions : List<NetworkAction> = listOf(),
    @SerializedName("meta"    ) var meta    : NetworkMeta?              = NetworkMeta()
)

