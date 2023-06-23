package com.example.smarthouse_tp3.ui

import androidx.compose.ui.graphics.Color
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceState
import kotlinx.coroutines.flow.update

class AirConditionerViewModel(deviceId: String) : DeviceViewModel(deviceId = deviceId) {
    override fun fetchDevice(deviceId: String): AirConditionerViewModel {
        super.fetchDevice(deviceId)
        _uiState.update {
            it.copy(
                deviceIcon = R.drawable.device_air_conditioner_on,
                switchState = uiState.value.state?.status == "on",
                deviceIconColor = if (uiState.value.state?.status == "on") {
                    Color.Blue
                } else {
                    Color.Black
                }
            )
        }
        return this
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

    override fun changeSwitchState() {
        super.changeSwitchState()
        if (uiState.value.switchState) {
            changeDeviceIconColor(Color.Blue)
            uiState.value.id?.let { executeAction(it, "turnOn", arrayOf()) }
            updateUiState(status = "on")
        } else {
            changeDeviceIconColor(Color.Black)
            uiState.value.id?.let { executeAction(it, "turnOff", arrayOf()) }
            updateUiState(status = "off")
        }
    }

    fun increaseTemperature() {
        if ((uiState.value.state?.temperature?.toInt() ?: 38) < 38) {
            updateUiState(temperature = uiState.value.state?.temperature?.plus(1))
            uiState.value.id?.let {
                executeAction(
                    it, "setTemperature",
                    arrayOf(uiState.value.state?.temperature.toString())
                )
            }
        }
    }

    fun decreaseTemperature() {
        if ((uiState.value.state?.temperature?.toInt() ?: 18) > 18) {
            updateUiState(temperature = uiState.value.state?.temperature?.minus(1))
            uiState.value.id?.let {
                executeAction(
                    it, "setTemperature",
                    arrayOf(uiState.value.state?.temperature.toString())
                )
            }
        }
    }

    fun updateUiState(
        status: String? = uiState.value.state?.status,
        temperature: Long? = uiState.value.state?.temperature,
        mode: String? = uiState.value.state?.mode,
        verticalSwing: String? = uiState.value.state?.verticalSwing,
        horizontalSwing: String? = uiState.value.state?.horizontalSwing,
        fanSpeed: String? = uiState.value.state?.fanSpeed
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                state = NetworkDeviceState(
                    status = status,
                    temperature = temperature,
                    mode = mode,
                    verticalSwing = verticalSwing,
                    horizontalSwing = horizontalSwing,
                    fanSpeed = fanSpeed
                )
            )
        }
    }


    fun iterateMode() {
        val state = uiState.value.state
        if (state != null) {
            updateUiState(mode = state.mode?.let { AirConditionerMode.getNextFromString(it).stringValue })
        }
        uiState.value.id?.let {
            executeAction(
                it, "setMode",
                arrayOf(uiState.value.state?.mode.toString())
            )
        }
    }

    fun getMode(): AirConditionerMode? {
        return uiState.value.state?.mode?.let { AirConditionerMode.fromString(it) }
    }

    fun iterateFanSpeed() {
        val state = uiState.value.state
        if (state != null) {
            updateUiState(fanSpeed = state.fanSpeed?.let {
                AirConditionerFanSpeed.getNextFromString(
                    it
                ).stringValue
            })
        }
        uiState.value.id?.let {
            executeAction(
                it, "setFanSpeed",
                arrayOf(uiState.value.state?.fanSpeed.toString())
            )
        }
    }

    fun getFanSpeed(): AirConditionerFanSpeed? {
        return uiState.value.state?.fanSpeed?.let { AirConditionerFanSpeed.fromString(it) }
    }

    fun increaseVerticalFanDirection() {
        val state = uiState.value.state
        if (state != null) {
            updateUiState(
                verticalSwing = state.verticalSwing?.let {
                    AirConditionerVerticalFanDirection.getNextFromString(it).stringValue
                }
            )
        }
        uiState.value.id?.let {
            executeAction(
                it, "setVerticalSwing",
                arrayOf(uiState.value.state?.verticalSwing.toString())
            )
        }
    }

    fun decreaseVerticalFanDirection() {
        val state = uiState.value.state
        if (state != null) {
            updateUiState(
                verticalSwing = state.verticalSwing?.let {
                    AirConditionerVerticalFanDirection.getPrevFromString(it).stringValue
                }
            )
        }
        uiState.value.id?.let {
            executeAction(
                it, "setVerticalSwing",
                arrayOf(uiState.value.state?.verticalSwing.toString())
            )
        }
    }


    fun increaseHorizontalFanDirection() {
        val state = uiState.value.state
        if (state != null) {
            updateUiState(
                horizontalSwing = state.horizontalSwing?.let {
                    AirConditionerHorizontalFanDirection.getNextFromString(it).stringValue
                }
            )
        }
        uiState.value.id?.let {
            executeAction(
                it, "setHorizontalSwing",
                arrayOf(uiState.value.state?.horizontalSwing.toString())
            )
        }
    }

    fun decreaseHorizontalFanDirection() {
        val state = uiState.value.state
        if (state != null) {
            updateUiState(
                horizontalSwing = state.horizontalSwing?.let {
                    AirConditionerHorizontalFanDirection.getPrevFromString(it).stringValue
                }
            )
        }
        uiState.value.id?.let {
            executeAction(
                it, "setHorizontalSwing",
                arrayOf(uiState.value.state?.horizontalSwing.toString())
            )
        }
    }
}


enum class AirConditionerMode(val index: Int, val stringValue: String) {
    HEAT(0, "heat"),
    COOL(1, "cool"),
    FAN(2, "fan");

    companion object {
        fun fromIndex(value: Int): AirConditionerMode {
            return values().find { it.index == value } ?: HEAT
        }

        fun fromString(value: String): AirConditionerMode {
            return values().find { it.stringValue == value } ?: HEAT
        }

        fun getNextFromString(value: String): AirConditionerMode {
            val currentMode = fromString(value)
            val currentIndex = currentMode.index
            val nextIndex = (currentIndex + 1) % values().size
            return fromIndex(nextIndex)
        }
    }
}

enum class AirConditionerFanSpeed(val index: Int, val stringValue: String) {
    AUTO(0, "auto"),
    FIRST(1, "25"),
    SECOND(2, "50"),
    THIRD(3, "75"),
    FOURTH(4, "100");

    companion object {
        fun fromIndex(value: Int): AirConditionerFanSpeed {
            return values().find { it.index == value } ?: AUTO
        }

        fun fromString(value: String): AirConditionerFanSpeed {
            return values().find { it.stringValue == value } ?: AUTO
        }

        fun getNextFromString(value: String): AirConditionerFanSpeed {
            val currentFanSpeed = fromString(value)
            val currentIndex = currentFanSpeed.index
            val nextIndex = (currentIndex + 1) % values().size
            return fromIndex(nextIndex)
        }
    }
}

fun getPrevIndex(currentIndex: Int, size: Int): Int {
    return (currentIndex - 1 + size) % size
}

enum class AirConditionerVerticalFanDirection(val index: Int, val stringValue: String) {
    AUTO(0, "auto"),
    FIRST(1, "22"),
    SECOND(2, "45"),
    THIRD(3, "67"),
    FOURTH(4, "90");

    companion object {
        fun fromIndex(value: Int): AirConditionerVerticalFanDirection {
            return values().find { it.index == value } ?: AUTO
        }

        fun fromString(value: String): AirConditionerVerticalFanDirection {
            return values().find { it.stringValue == value } ?: AUTO
        }

        fun getNextFromString(value: String): AirConditionerVerticalFanDirection {
            val currentVerticalFanDirection = fromString(value)
            val currentIndex = currentVerticalFanDirection.index
            val nextIndex = (currentIndex + 1) % values().size
            return fromIndex(nextIndex)
        }

        fun getPrevFromString(value: String): AirConditionerVerticalFanDirection {
            val currentVerticalFanDirection = fromString(value)
            val currentIndex = currentVerticalFanDirection.index
            val prevIndex = getPrevIndex(currentIndex, values().size)
            return fromIndex(prevIndex)
        }
    }
}

enum class AirConditionerHorizontalFanDirection(val index: Int, val stringValue: String) {
    AUTO(0, "auto"),
    FIRST(1, "-90"),
    SECOND(2, "-45"),
    THIRD(3, "0"),
    FOURTH(4, "45"),
    FIFTH(5, "90");

    companion object {
        fun fromIndex(value: Int): AirConditionerHorizontalFanDirection {
            return values().find { it.index == value } ?: AUTO
        }

        fun fromString(value: String): AirConditionerHorizontalFanDirection {
            return values().find { it.stringValue == value } ?: AUTO
        }

        fun getNextFromString(value: String): AirConditionerHorizontalFanDirection {
            val currentHorizontalFanDirection = fromString(value)
            val currentIndex = currentHorizontalFanDirection.index
            val nextIndex = (currentIndex + 1) % values().size
            return fromIndex(nextIndex)
        }

        fun getPrevFromString(value: String): AirConditionerHorizontalFanDirection {
            val currentVerticalFanDirection = fromString(value)
            val currentIndex = currentVerticalFanDirection.index
            val prevIndex = getPrevIndex(currentIndex, values().size)
            return fromIndex(prevIndex)
        }
    }
}
