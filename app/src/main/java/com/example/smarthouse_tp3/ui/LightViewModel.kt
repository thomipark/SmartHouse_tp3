package com.example.smarthouse_tp3.ui

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceState
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class LightViewModel(deviceId: String) : DeviceViewModel(deviceId) {
    override fun fetchDevice(deviceId: String): LightViewModel {
        super.fetchDevice(deviceId)
        _uiState.update {
            it.copy(
                deviceIcon = R.drawable.device_lightbulb_on,
                switchState = uiState.value.state?.status == "on",
                deviceIconColor = if (uiState.value.state?.status == "on") {
                    Color.Yellow
                } else {
                    Color.Black
                }
            )
        }
        return this
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
        updateUiState(color = hexCode)
    }

    fun changeBrightness(percent: String) {
        if (percent == uiState.value.state!!.brightness) {
            return
        }
        uiState.value.id?.let { executeAction(it, "setBrightness", arrayOf(percent)) }
        updateUiState(brightness = percent)
    }

    override fun changeSwitchState() {
        super.changeSwitchState()
        if (uiState.value.switchState) {
            changeDeviceIconColor(Color.Yellow)
            uiState.value.id?.let { executeAction(it, "turnOn", arrayOf()) }
            updateUiState(status = "on")
        } else {
            changeDeviceIconColor(Color.Black)
            uiState.value.id?.let { executeAction(it, "turnOff", arrayOf()) }
            updateUiState(status = "off")
        }
    }

    private fun updateUiState(
        switchState: Boolean = uiState.value.switchState,
        status: String? = uiState.value.state?.status,
        color: String? = uiState.value.state?.color,
        brightness: String? = uiState.value.state?.brightness
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                switchState = switchState,
                state = NetworkDeviceState(
                    status = status,
                    color = color,
                    brightness = brightness
                )
            )
        }
    }
}