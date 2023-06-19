package com.example.smarthouse_tp3.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkDeviceState (
    @SerializedName("status"            ) var status:               String?             = null,
    @SerializedName("temperature"       ) var temperature:          Long?               = null,
    @SerializedName("heat"              ) var heat:                 String?             = null,
    @SerializedName("grill"             ) var grill:                String?             = null,
    @SerializedName("convection"        ) var convection:           String?             = null,
    @SerializedName("color"             ) var color:                String?             = null,
    @SerializedName("brightness"        ) var brightness:           String?               = null,
    @SerializedName("mode"              ) var mode:                 String?             = null,
    @SerializedName("batteryLevel"      ) var batteryLevel:         Long?               = null,
    @SerializedName("location"          ) var location:             NetworkDeviceRoom?  = NetworkDeviceRoom(),
    @SerializedName("verticalSwing"     ) var verticalSwing:        String?             = null,
    @SerializedName("horizontalSwing"   ) var horizontalSwing:      String?             = null,
    @SerializedName("fanSpeed"          ) var fanSpeed:             String?             = null,
    @SerializedName("quantity"          ) var quantity:             String?             = null,
    @SerializedName("unit"              ) var unit:                 String?             = null,
    @SerializedName("dispensedQuantity" ) var dispensedQuantity:    Float?              = 0F
)