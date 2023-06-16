package com.example.smarthouse_tp3

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color


class DeviceVacuum(name: String) : Device(name) {
    //    var temperature = mutableStateOf(1)
    //    var grillMode = mutableStateof()
    override var deviceType: Type = Type.VACUUM
    override var deviceIcon : Int = R.drawable.device_vacuum
    private var battery = mutableStateOf(4)
    private var currentRoom = mutableStateOf("Kitchen")
    private var dockingRoom : String = "Living Room"

    private var mode = mutableStateOf(VacuumMode.VACUUM)

    fun getBattery() : MutableState<Int> {
        return battery
    }

    fun getMode() : MutableState<VacuumMode> {
        return mode
    }

    override fun changeSwitchState() {
        super.changeSwitchState()
        if (getSwitchState()) {
            changeDeviceIconColor(Color.Blue)
        }
        else {
            changeDeviceIconColor(Color.Black)
        }
    }

    override fun getSmallIconsList(): List<Int> {
        val iconsList = mutableListOf<Int>()
        iconsList.add(getBatteryIcon())

        return iconsList
    }

    fun getBatteryIcon() : Int {
        if (mode.value == VacuumMode.CHARGING) {
            return R.drawable.baseline_battery_charging_full_24
        } else if (battery.value < 5) {
            return R.drawable.baseline_battery_alert_24
        } else if (battery.value < 12.5) {
            return R.drawable.baseline_battery_0_bar_24
        } else if (battery.value < 25) {
            return R.drawable.baseline_battery_1_bar_24
        } else if (battery.value < 37.5) {
            return R.drawable.baseline_battery_2_bar_24
         } else if (battery.value < 50) {
            return R.drawable.baseline_battery_3_bar_24
        } else if (battery.value < 62.5) {
            return R.drawable.baseline_battery_4_bar_24
        } else if (battery.value < 75) {
            return R.drawable.baseline_battery_5_bar_24
        } else if (battery.value < 87.5) {
            return R.drawable.baseline_battery_6_bar_24
        } else {
            return R.drawable.baseline_battery_full_24
        }
    }

    fun dock() {
        mode.value = VacuumMode.CHARGING
        changeCurrentRoom(dockingRoom)
    }

    fun changeModeMop() {
        mode.value = VacuumMode.MOP
    }

    fun changeModeVacuum() {
        mode.value = VacuumMode.VACUUM
    }

    fun getCurrentRoom() :MutableState<String> {
        return currentRoom
    }

    fun changeCurrentRoom(newRoom : String) {
        currentRoom.value = newRoom
    }
}

enum class VacuumMode(val index: Int, val stringValue: String) {
    VACUUM(0, "VACUUM"),
    MOP(1, "MOP"),
    CHARGING(2, "CHARGING");

    companion object {
        fun fromIndex(value: Int): VacuumMode{
        return values().find { it.index == value } ?: VACUUM
        }
    }

}
