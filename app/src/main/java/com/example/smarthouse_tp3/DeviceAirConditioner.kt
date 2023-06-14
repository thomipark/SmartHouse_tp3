package com.example.smarthouse_tp3

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


class DeviceAirConditioner(name: String) : Device(name) {
    //    var temperature = mutableStateOf(1)
    //    var grillMode = mutableStateof()
    override var deviceType: Type = Type.AC
    private var temperature = mutableStateOf(21)


    override fun getIcon(): Int {
        return if (getSwitchState()){
            R.drawable.device_air_conditioner_on
        } else {
            R.drawable.device_air_conditioner_on
        }
    }

    fun getTempareature() : MutableState<Int> {
        return temperature
    }

    fun incTemperature() {
        if (temperature.value < 38)
        temperature.value++
    }


    fun decTemperature() {
        if (temperature.value > 18)
            temperature.value--
    }
}