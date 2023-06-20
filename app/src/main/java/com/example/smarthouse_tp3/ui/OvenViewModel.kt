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
                deviceIcon = R.drawable.device_oven_off,
                switchState = uiState.value.state?.status == "on",
                deviceIconColor = if (uiState.value.state?.status == "on") {
                    Color.Red
                } else {
                    Color.Black
                }
            )
        }
    }

    override fun changeSwitchState() {
        super.changeSwitchState()
        if (uiState.value.switchState) {
            changeDeviceIconColor(Color.Red)
            uiState.value.id?.let { executeAction(it, "turnOn", arrayOf()) }
            updateUiState(status = "on")
        } else {
            changeDeviceIconColor(Color.Black)
            uiState.value.id?.let { executeAction(it, "turnOff", arrayOf()) }
            updateUiState(status = "on")
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
        val state = uiState.value.state
        if (state != null) {
            if ((state.temperature?.toInt() ?: 230) < 230) {
                updateUiState(temperature = state.temperature?.let { it + 5L })
                uiState.value.id?.let {
                    executeAction(
                        it, "setTemperature",
                        arrayOf(uiState.value.state?.temperature.toString())
                    )
                }
            }
        }
    }

    fun decreaseTemperature() {
        val state = uiState.value.state
        if (state != null) {
            if ((uiState.value.state?.temperature?.toInt() ?: 90) > 90) {
                updateUiState(temperature = state.temperature?.let { it - 5L })
                uiState.value.id?.let {
                    executeAction(
                        it, "setTemperature",
                        arrayOf(uiState.value.state?.temperature.toString())
                    )
                }
            }
        }
    }

    fun iterateFanMode() {
        val state = uiState.value.state
        if (state != null) {
            updateUiState(convection = state.convection?.let { OvenFanMode.getNextFromString(it).stringValue })
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
            updateUiState(grill = state.grill?.let { OvenGrillMode.getNextFromString(it).stringValue })
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
            updateUiState(heat = state.heat?.let { OvenHeatMode.getNextFromString(it).stringValue })
        }
        uiState.value.id?.let {
            executeAction(
                it, "setHeat",
                arrayOf(uiState.value.state?.heat.toString())
            )
        }
    }


    private fun updateUiState(
        switchState : Boolean = uiState.value.switchState,
        status      : String? = uiState.value.state?.status,
        temperature : Long? = uiState.value.state?.temperature,
        heat        : String? =  uiState.value.state?.heat,
        grill       : String? =  uiState.value.state?.grill,
        convection  : String? =  uiState.value.state?.convection

    ) {
        _uiState.update { currentState ->
            currentState.copy(
                switchState = switchState,
                state = NetworkDeviceState(
                    status = status,
                    temperature = temperature,
                    heat = heat,
                    grill = grill,
                    convection = convection
                )
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
        fun getIndexFromString(value: String): Int {
            return OvenFanMode.values().indexOfFirst { it.stringValue == value }
        }
    }

}

enum class OvenGrillMode(val index: Int, val stringValue: String) {
    FULL(0, "full"),
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

        fun getIndexFromString(value: String): Int {
            return OvenGrillMode.values().indexOfFirst { it.stringValue == value }
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

        fun getIndexFromString(value: String): Int {
            return OvenHeatMode.values().indexOfFirst { it.stringValue == value }
        }
    }
}
