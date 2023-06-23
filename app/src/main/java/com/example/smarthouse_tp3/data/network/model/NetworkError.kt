package com.example.smarthouse_tp3.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkError(
    @SerializedName("code") var code: String? = null,
    @SerializedName("description") var description: String? = null

)

