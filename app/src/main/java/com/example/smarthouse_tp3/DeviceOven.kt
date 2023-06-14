package com.example.smarthouse_tp3

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


class DeviceOven(name: String) : Device(name) {
    //    var temperature = mutableStateOf(1)
    //    var grillMode = mutableStateof()
    override var deviceType: Type = Type.OVEN
    private var temperature = mutableStateOf(200)

    override fun getIcon(): Int {
        return if (getSwitchState()){
            R.drawable.device_oven_on
        } else {
            R.drawable.device_oven_off
        }
    }

    fun getTemperature(): MutableState<Int> {
        return temperature
    }

    fun increaseTemperature() {
        temperature.value += 5
    }

    fun decreaseTemperature() {
        temperature.value -= 5
    }


}