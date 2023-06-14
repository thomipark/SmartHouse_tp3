package com.example.smarthouse_tp3

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color


class DeviceLight(name: String) : Device(name) {
    override var deviceType: Type = Type.LIGHT
    private var hexCode = mutableStateOf("#FF0000")
    override var deviceIcon : Int = R.drawable.device_lightbulb_on

    fun changeColor(str : String) {
        hexCode.value = str

        // changeDeviceIconColor(Color(android.graphics.Color.parseColor(str)))
    }

    fun getHexCode() : MutableState<String> {
        return hexCode
    }

    override fun changeSwitchState() {
        super.changeSwitchState()
        if (getSwitchState()) {
        }
        else {
            changeDeviceIconColor(Color.Black)
        }
    }
}