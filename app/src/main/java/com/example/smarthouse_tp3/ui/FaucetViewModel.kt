package com.example.smarthouse_tp3.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceRoom
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceState
import kotlinx.coroutines.flow.update

class FaucetViewModel: DeviceViewModel() {
    override fun fetchDevice(deviceId: String) {
        super.fetchDevice(deviceId)
        _uiState.update {
            it.copy(
                deviceIcon = R.drawable.device_sprinkler_on
            )
        }
    }

    override fun changeSwitchState() {
        super.changeSwitchState()
        if (uiState.value.switchState) {
            changeDeviceIconColor(Color.Blue)
        } else {
            changeDeviceIconColor(Color.Black)
        }
    }

    override fun getSmallIconsList(): List<Int> {
        val iconsList = mutableListOf<Int>()
        // if (uiState.value.state?.temperature > 100){
        //     iconsList.add(R.drawable.device_oven_on)
        // }
        // if (uiState.value.state?.grill == OvenGrillMode.ON.toString()){
        //     iconsList.add(R.drawable.grill)
        // }

        // iconsList.add(R.drawable.device_lightbulb_on)
        return iconsList
    }

    fun open() {
        setAction("open")
    }

    fun close() {
        setAction("close")
    }

    fun updateUiState(
        status: String? = uiState.value.state?.status
    ) {
        _uiState.update { currentState->
            currentState.copy(
                state = NetworkDeviceState(
                    status = status,
                )
            )
        }
    }


    fun dispense(quantity : Float, unit : String) {
        val state = uiState.value.state
         if (state != null) {
             updateUiState(status = "opened")

             uiState.value.id?.let {
                 executeAction(
                     it, "dispense",
                     arrayOf(quantity.toString(), unit)
                 )
             }
             uiState.value.id?.let { fetchDevice(it) }
         }
    }


    private fun setAction(action : String) {
        val state = uiState.value.state
        val newStatus : String = when (action) {
            "open" -> {
                "opened"
            }
            else -> {
                "closed"
            }
        }

        if (state != null) {
            updateUiState(status = newStatus)

            uiState.value.id?.let {
                executeAction(
                    it, action,
                    emptyArray()
                )
            }
        }
    }
}

