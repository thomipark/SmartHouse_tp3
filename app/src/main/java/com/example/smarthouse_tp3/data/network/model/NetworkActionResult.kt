package com.example.smarthouse_tp3.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkActionResult (
    @SerializedName("result" ) var error: NetworkError = NetworkError()
)

