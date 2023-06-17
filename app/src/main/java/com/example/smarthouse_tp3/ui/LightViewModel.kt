package com.example.smarthouse_tp3.ui

import androidx.compose.ui.graphics.Color
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceState
import kotlinx.coroutines.flow.update

class LightViewModel : DeviceViewModel() {
    override fun fetchDevice(deviceId : String) {
        super.fetchDevice(deviceId)
        _uiState.update { it.copy(
            deviceIcon = R.drawable.device_lightbulb_on
        ) }
    }

    override fun getSmallIconsList(): List<Int> {
        TODO("Not yet implemented")
    }

    fun changeColor(hexCode : String) {
        uiState.value.id?.let { executeAction(it, "setColor", arrayOf(hexCode)) }
        _uiState.value.state?.color = hexCode

        val device = uiState.value
        if (device != null) {
            _uiState.update { currentState->
                currentState.copy(
                    state = NetworkDeviceState(
                        status = device.state?.status,
                        color = hexCode,
                        brightness = device.state?.brightness
                    )
                )
            }
        }


    }

    fun changeBrightness(percent : Long) {
        uiState.value.id?.let { executeAction(it, "setBrightness", arrayOf(percent.toString())) }
        // _uiState.value.device?.state?.brightness = percent.toString().toLong()
        val device = uiState.value
        if (device != null) {
            _uiState.update { currentState->
                currentState.copy(
                    state = NetworkDeviceState(
                        status = device.state?.status,
                        color = device.state?.color,
                        brightness = percent
                    )
                )
            }
        }
    }

    override fun changeSwitchState() {
        super.changeSwitchState()
        if (uiState.value.switchState) {
            changeDeviceIconColor(Color.Yellow)
        } else {
            changeDeviceIconColor(Color.Black)
        }
    }
}