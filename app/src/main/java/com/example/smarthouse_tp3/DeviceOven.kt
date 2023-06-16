package com.example.smarthouse_tp3

import android.media.Image
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color


class DeviceOven(name: String) : Device(name) {
    override var deviceType: Type = Type.OVEN
    override var deviceIcon: Int = R.drawable.device_oven_on
    private var temperature = mutableStateOf(200)
    private var fanMode = mutableStateOf(OvenFanMode.ON)
    private var grillMode = mutableStateOf(OvenGrillMode.ON)
    private var heatMode = mutableStateOf(OvenHeatMode.BOTTOM)

    //override fun getIcon(): Int {
    //    return if (getSwitchState()){
    //        R.drawable.device_oven_on
    //    } else {
    //        R.drawable.device_oven_off
    //    }
    //}

    override fun getSmallIconsList(): List<Int> {
        val iconsList = mutableListOf<Int>()
        if (temperature.value > 100){
            iconsList.add(R.drawable.device_oven_on)
        }
        if (grillMode.value == OvenGrillMode.ON){
            iconsList.add(R.drawable.grill)
        }

        iconsList.add(R.drawable.device_lightbulb_on)
        return iconsList
    }

    fun getTemperature(): MutableState<Int> {
        return temperature
    }

    fun increaseTemperature() {
        temperature.value += 5
    }

    fun decreaseTemperature() {
        temperature.value -= 5
    }

    fun getFanMode() : MutableState<OvenFanMode> {
        return fanMode
    }

    fun iterateFanMode() {
        fanMode.value = OvenFanMode.fromIndex(fanMode.value.index+1)
    }

    fun getGrillMode() : MutableState<OvenGrillMode> {
        return grillMode
    }

    fun iterateGrillMode() {
        grillMode.value = OvenGrillMode.fromIndex(grillMode.value.index+1)
    }

    fun getHeatMode() : MutableState<OvenHeatMode> {
        return heatMode
    }

    fun iterateHeatMode() {
        heatMode.value = OvenHeatMode.fromIndex(heatMode.value.index+1)
    }

    override fun changeSwitchState() {
        super.changeSwitchState()
        if (getSwitchState()) {
            changeDeviceIconColor(Color.Red)
        }
        else {
            changeDeviceIconColor(Color.Black)
        }
    }

}

enum class OvenFanMode(val index: Int, val stringValue: String) {
    ON(0, "ON"),
    OFF(1, "OFF");

    companion object {
        fun fromIndex(value: Int): OvenFanMode {
            return values().find { it.index == value } ?: ON
        }
    }
}

enum class OvenGrillMode(val index: Int, val stringValue: String) {
    ON(0, "ON"),
    OFF(1, "OFF");

    companion object {
        fun fromIndex(value: Int): OvenGrillMode {
            return values().find { it.index == value } ?: ON
        }
    }
}

enum class OvenHeatMode(val index: Int, val stringValue: String) {
    BOTTOM(0, "BOTTOM"),
    TOP(1, "TOP"),
    BOTH(2, "BOTH");

    companion object {
        fun fromIndex(value: Int): OvenHeatMode {
            return values().find { it.index == value } ?: BOTTOM
        }
    }
}