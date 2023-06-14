package com.example.smarthouse_tp3

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


class DeviceLight(name: String) : Device(name) {
    override var deviceType: Type = Type.LIGHT
    private var hexCode = mutableStateOf("#000000")

    override fun getIcon(): Int {
        return if (getSwitchState()){
            R.drawable.device_lightbulb_on
        } else {
            R.drawable.device_lightbulb_off
        }
    }

    fun changeColor(str : String) {
        hexCode.value = str
    }

    fun getHexCode() : MutableState<String> {
        return hexCode
    }
}