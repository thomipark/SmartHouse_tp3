package com.example.smarthouse_tp3.ui

import androidx.compose.ui.graphics.Color
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceState
import kotlinx.coroutines.flow.update

class OvenViewModel : DeviceViewModel() {
    override fun fetchDevice(deviceId: String) {
        super.fetchDevice(deviceId)
        _uiState.update {
            it.copy(
                deviceIcon = R.drawable.device_oven_off
            )
        }
    }

    override fun changeSwitchState() {
        super.changeSwitchState()
        if (uiState.value.switchState) {
            changeDeviceIconColor(Color.Red)
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


    fun increaseTemperature() {
        if ((uiState.value.state?.temperature?.toInt() ?: 230) < 230) {
            _uiState.value.state?.temperature?.plus(5)
            uiState.value.id?.let {
                executeAction(
                    it, "setTemperature",
                    arrayOf(uiState.value.state?.temperature.toString())
                )
            }
        }
    }

    fun decreaseTemperature() {
        if ((uiState.value.state?.temperature?.toInt() ?: 180) > 180) {
            _uiState.value.state?.temperature?.minus(5)
            uiState.value.id?.let {
                executeAction(
                    it, "setTemperature",
                    arrayOf(uiState.value.state?.temperature.toString())
                )
            }
        }
    }

    fun iterateFanMode() {
        val state = uiState.value.state
        if (state != null) {
            _uiState.update { currentState ->
                currentState.copy(
                    state = NetworkDeviceState(
                        status = state.status,
                        temperature = state.temperature,
                        heat = state.heat,
                        grill = state.grill,
                        convection = state.convection?.let { OvenFanMode.getNextFromString(it).stringValue }
                    )
                )
            }
        }
        uiState.value.id?.let {
            executeAction(
                it, "setConvection",
                arrayOf(uiState.value.state?.convection.toString())
            )
        }
    }


    fun iterateGrillMode() {
        val state = uiState.value.state
        if (state != null) {
            _uiState.update { currentState ->
                currentState.copy(
                    state = NetworkDeviceState(
                        status = state.status,
                        temperature = state.temperature,
                        heat = state.heat,
                        grill = state.grill?.let { OvenGrillMode.getNextFromString(it).stringValue },
                        convection = state.convection
                    )
                )
            }
        }
        uiState.value.id?.let {
            executeAction(
                it, "setGrill",
                arrayOf(uiState.value.state?.grill.toString())
            )
        }
    }


    fun iterateHeatMode() {
        val state = uiState.value.state
        if (state != null) {
            _uiState.update { currentState ->
                currentState.copy(
                    state = NetworkDeviceState(
                        status = state.status,
                        temperature = state.temperature,
                        heat = state.heat?.let { OvenHeatMode.getNextFromString(it).stringValue },
                        grill = state.grill,
                        convection = state.convection
                    )
                )
            }
        }
        uiState.value.id?.let {
            executeAction(
                it, "setHeat",
                arrayOf(uiState.value.state?.heat.toString())
            )
        }
    }


}

enum class OvenFanMode(val index: Int, val stringValue: String) {
    NORMAL(0, "normal"),
    ECO(1, "eco"),
    OFF(2, "off");

    companion object {
        fun fromIndex(value: Int): OvenFanMode {
            return values().find { it.index == value } ?: NORMAL
        }

        fun fromString(value: String): OvenFanMode {
            return values().find { it.stringValue == value } ?: NORMAL
        }

        fun getNextFromString(value: String): OvenFanMode {
            val currentMode = fromString(value)
            val currentIndex = currentMode.index
            val nextIndex = (currentIndex + 1) % values().size
            return fromIndex(nextIndex)
        }
    }

}

enum class OvenGrillMode(val index: Int, val stringValue: String) {
    LARGE(0, "large"),
    ECO(1, "eco"),
    OFF(2, "off");

    companion object {
        fun fromIndex(value: Int): OvenGrillMode {
            return values().find { it.index == value } ?: OFF
        }

        fun fromString(value: String): OvenGrillMode {
            return values().find { it.stringValue == value } ?: OFF
        }

        fun getNextFromString(value: String): OvenGrillMode {
            val currentMode = fromString(value)
            val currentIndex = currentMode.index
            val nextIndex = (currentIndex + 1) % values().size
            return fromIndex(nextIndex)
        }
    }

}

enum class OvenHeatMode(val index: Int, val stringValue: String) {
    BOTTOM(0, "bottom"),
    TOP(1, "top"),
    CONVENTIONAL(2, "conventional");

    companion object {
        fun fromIndex(value: Int): OvenHeatMode {
            return values().find { it.index == value } ?: CONVENTIONAL
        }

        fun fromString(value: String): OvenHeatMode {
            return values().find { it.stringValue == value } ?: CONVENTIONAL
        }

        fun getNextFromString(value: String): OvenHeatMode {
            val currentMode = fromString(value)
            val currentIndex = currentMode.index
            val nextIndex = (currentIndex + 1) % values().size
            return fromIndex(nextIndex)
        }
    }
}
