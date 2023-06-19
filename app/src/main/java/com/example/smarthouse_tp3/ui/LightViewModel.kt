package com.example.smarthouse_tp3.ui

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceState
import kotlinx.coroutines.flow.update

class LightViewModel : DeviceViewModel() {
    override fun fetchDevice(deviceId: String) {
        super.fetchDevice(deviceId)
        _uiState.update { it.copy(
            deviceIcon = R.drawable.device_lightbulb_on,
            switchState = uiState.value.state?.status == "on",
            deviceIconColor = if (uiState.value.state?.status == "on") {
                Color.Yellow
            } else {
                Color.Black
            }
        ) }
    }

    override fun getSmallIconsList(): List<Int> {
        return emptyList()
    }

    fun changeColor(hexCode: String) {
        if (hexCode == uiState.value.state!!.color) {
            return
        }
        uiState.value.id?.let { executeAction(it, "setColor", arrayOf(hexCode)) }
        _uiState.value.state?.color = hexCode

        val device = uiState.value
        _uiState.update { currentState ->
            currentState.copy(
                state = NetworkDeviceState(
                    status = device.state?.status,
                    color = hexCode,
                    brightness = device.state?.brightness
                )
            )
        }


    }

    fun changeBrightness(percent: String) {
        if (percent == uiState.value.state!!.brightness) {
            return
        }
        uiState.value.id?.let { executeAction(it, "setBrightness", arrayOf(percent)) }
        val device = uiState.value
        _uiState.update { currentState ->
            currentState.copy(
                state = NetworkDeviceState(
                    status = device.state?.status,
                    color = device.state?.color,
                    brightness = percent
                )
            )
        }
    }

    override fun changeSwitchState() {
        super.changeSwitchState()
        if (uiState.value.switchState) {
            changeDeviceIconColor(Color.Yellow)
            uiState.value.id?.let { executeAction(it, "turnOn", arrayOf()) }
        } else {
            changeDeviceIconColor(Color.Black)
            uiState.value.id?.let { executeAction(it, "turnOff", arrayOf()) }
        }
    }
}