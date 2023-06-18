package com.example.smarthouse_tp3

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class DeviceFaucet(name: String) : Device(name) {
    override var deviceType: Type = Type.FAUCET
    override var deviceIcon: Int = R.drawable.device_sprinkler_on
    private var waterLevel: MutableState<Float> = mutableStateOf(0f)
    private var unit: MutableState<String> =
        mutableStateOf(getFaucetUnitValues()[FaucetUnits.KILOLITERS.index])

    override fun changeSwitchState() {
        super.changeSwitchState()
        if (getSwitchState()) {
            changeDeviceIconColor(Color.Blue)
        } else {
            changeDeviceIconColor(Color.Black)
        }
    }

    override fun getSmallIconsList(): List<Int> {
        TODO("Not yet implemented")
    }

    fun getWaterLevel(): MutableState<Float> {
        return waterLevel
    }

    fun changeWaterLevel(newWaterValue: Float) {
        waterLevel.value = newWaterValue
    }

    fun getFaucetUnitValues(): List<String> {
        return FaucetUnits.values().map { it.stringValue }
    }

    fun getUnit(): String {
        return unit.value
    }

    fun changeUnit(newUnit: String) {
        unit.value = newUnit
    }


}

enum class FaucetUnits(val index: Int, val stringValue: String) {
    KILOLITERS(0, "Kiloliters"),
    HECTOLITERS(1, "Hectoliters"),
    DECALITERS(2, "Decaliters"),
    LITERS(3, "Liters"),
    DECILITERS(4, "Deciliters"),
    CENTILITERS(5, "Centiliters"),
    MILLILITERS(6, "Milliliters");

    companion object {
        fun fromIndex(value: Int): FaucetUnits {
            return values().find { it.index == value } ?: KILOLITERS
        }
    }

}
