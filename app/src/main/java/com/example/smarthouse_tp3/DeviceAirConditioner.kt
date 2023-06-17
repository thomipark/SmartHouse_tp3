package com.example.smarthouse_tp3
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.example.smarthouse_tp3.ui.AirConditionerFanSpeed
import com.example.smarthouse_tp3.ui.AirConditionerHorizontalFanDirection
import com.example.smarthouse_tp3.ui.AirConditionerMode
import com.example.smarthouse_tp3.ui.AirConditionerVerticalFanDirection


class DeviceAirConditioner(name: String) : Device(name) {
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