package com.example.smarthouse_tp3.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkMeta (
    @SerializedName("favorite"       ) var favorite : Boolean? = false,
)