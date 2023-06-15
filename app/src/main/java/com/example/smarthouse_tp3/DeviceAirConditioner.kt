package com.example.smarthouse_tp3

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color


class DeviceAirConditioner(name: String) : Device(name) {
    //    var temperature = mutableStateOf(1)
    //    var grillMode = mutableStateof()
    override var deviceType: Type = Type.AC
    override var deviceIcon : Int = R.drawable.device_air_conditioner_on
    private var temperature = mutableStateOf(21)
    private var mode = mutableStateOf(AirConditionerMode.FAN)
    private var fanSpeed = mutableStateOf(AirConditionerFanSpeed.AUTO)
    private var verticalFanDirection = mutableStateOf(AirConditionerVerticalFanDirection.AUTO)
    private var horizontalFanDirection = mutableStateOf(AirConditionerHorizontalFanDirection.AUTO)

    fun getTemperature() : MutableState<Int> {
        return temperature
    }
    fun increaseTemperature() {
        if (temperature.value < 38)
            temperature.value++
    }
    fun decreaseTemperature() {
        if (temperature.value > 18)
            temperature.value--
    }

    fun getMode() : MutableState<AirConditionerMode> {
        return mode
    }

    fun iterateMode() {
        mode.value = AirConditionerMode.fromIndex(mode.value.index+1)
    }

    fun getFanSpeed() : MutableState<AirConditionerFanSpeed> {
        return fanSpeed
    }

    fun iterateFanSpeed() {
        fanSpeed.value = AirConditionerFanSpeed.fromIndex(fanSpeed.value.index+1)
    }


    fun getVerticalFanDirection() : MutableState<AirConditionerVerticalFanDirection> {
        return verticalFanDirection
    }

    fun increaseVerticalFanDirection() {
        verticalFanDirection.value = AirConditionerVerticalFanDirection.fromIndex(verticalFanDirection.value.index+1)
    }

    fun decreaseVerticalFanDirection() {
        if(verticalFanDirection.value.index > 0) {
            verticalFanDirection.value =
                AirConditionerVerticalFanDirection.fromIndex(verticalFanDirection.value.index - 1)
        }
    }
    fun getHorizontalFanDirection() : MutableState<AirConditionerHorizontalFanDirection> {
        return horizontalFanDirection
    }

    fun increaseHorizontalFanDirection() {
        horizontalFanDirection.value = AirConditionerHorizontalFanDirection.fromIndex(horizontalFanDirection.value.index+1)
    }

    fun decreaseHorizontalFanDirection() {
        if (horizontalFanDirection.value.index > 0) {
            horizontalFanDirection.value =
                AirConditionerHorizontalFanDirection.fromIndex(horizontalFanDirection.value.index - 1)
        }
    }

    override fun changeSwitchState() {
        super.changeSwitchState()
        if (getSwitchState()) {
            changeDeviceIconColor(Color.Blue)
        }
        else {
            changeDeviceIconColor(Color.Black)
        }
    }

    override fun getSmallIconsList(): List<Int> {
        val iconsList = mutableListOf<Int>()
        iconsList.add(R.drawable.fan)
        iconsList.add(R.drawable.fan_speed_1)
        iconsList.add(R.drawable.fan_speed_2)


        return iconsList
    }

}

enum class AirConditionerMode(val index: Int, val stringValue: String) {
    HEAT(0, "HEAT"),
    COLD(1, "COLD"),
    FAN(2, "FAN");

    companion object {
        fun fromIndex(value: Int): AirConditionerMode {
            return values().find { it.index == value } ?: HEAT
        }
    }

}
enum class AirConditionerFanSpeed(val index: Int, val stringValue: String) {
    AUTO(0, "Automatic"),
    FIRST(1, "25%"),
    SECOND(2, "50%"),
    THIRD(3, "75%"),
    FOURTH(4, "100%");


    companion object {
        fun fromIndex(value: Int): AirConditionerFanSpeed{
            return values().find { it.index == value } ?: AUTO
        }
    }
}


enum class AirConditionerVerticalFanDirection(val index: Int, val stringValue: String) {
    AUTO(0, "Automatic"),
    FIRST(1, "22"),
    SECOND(2, "45"),
    THIRD(3, "67"),
    FOURTH(4, "90");


    companion object {
        fun fromIndex(value: Int): AirConditionerVerticalFanDirection{
            if (value <= 0) {
                return AUTO
            }
            return values().find { it.index == value } ?: fromIndex(value-1)
        }
    }
}

enum class AirConditionerHorizontalFanDirection(val index: Int, val stringValue: String) {
    AUTO(0, "Automatic"),
    FIRST(1, "-90"),
    SECOND(2, "-45"),
    THIRD(3, "0"),
    FOURTH(4, "45"),
    FIFTH(5, "90");


    companion object {
        fun fromIndex(value: Int): AirConditionerHorizontalFanDirection{
            if (value <= 0) {
                return AUTO
            }
            return values().find { it.index == value } ?: fromIndex(value-1)
        }
    }
}
