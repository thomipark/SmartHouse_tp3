package com.example.smarthouse_tp3.ui

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceState
import kotlinx.coroutines.flow.update

class FaucetViewModel(deviceId: String) : DeviceViewModel(deviceId = deviceId) {
    override fun fetchDevice(deviceId: String) {
        super.fetchDevice(deviceId)
        _uiState.update {
            it.copy(
                deviceIcon = R.drawable.device_sprinkler_on,
                switchState = uiState.value.state?.status == "opened",
                deviceIconColor = if (uiState.value.state?.status == "opened") {
                    Color.Blue
                } else {
                    Color.Black
                }
            )
        }
    }

    override fun changeSwitchState() {
        super.changeSwitchState()
        if (uiState.value.switchState) {
            changeDeviceIconColor(Color.Blue)
            uiState.value.id?.let { executeAction(it, "open", arrayOf()) }
            updateUiState(status = "opened")
        } else {
            changeDeviceIconColor(Color.Black)
            uiState.value.id?.let { executeAction(it, "close", arrayOf()) }
            updateUiState(status = "closed")
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

    private fun updateUiState(
        status: String? = uiState.value.state?.status
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                state = NetworkDeviceState(
                    status = status,
                )
            )
        }
    }


    fun dispense(quantity: Float, unit: String) {
        val state = uiState.value.state
        if (state != null) {
            updateUiState(status = "opened")
            uiState.value.id?.let {
                Log.d("Dispense", "Dispensing quantity: $quantity $unit")
                executeAction(
                    it, "dispense",
                    arrayOf(quantity.toString(), unit)
                )
            }
            //uiState.value.id?.let { fetchDevice(it) }
        }
    }


    private fun setAction(action: String) {
        val state = uiState.value.state
        val newStatus: String = when (action) {
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

enum class FaucetUnits(val index: Int, val stringValue: String) {
    KILOLITERS  (0, "kl"   ),
    HECTOLITERS (1, "hl"   ),
    DECALITERS  (2, "dal"  ),
    LITERS      (3, "l"    ),
    DECILITERS  (4, "dl"   ),
    CENTILITERS (5, "cl"   ),
    MILLILITERS (6, "ml"   );

    companion object {
        fun fromIndex(value: Int): FaucetUnits {
            return values().find { it.index == value } ?: KILOLITERS
        }

        fun getFaucetUnitValues(): List<String> {
            return FaucetUnits.values().map { it.stringValue }
        }
        fun fromString(value: String): FaucetUnits {
            return values().find { it.stringValue == value } ?: KILOLITERS
        }
    }

}
