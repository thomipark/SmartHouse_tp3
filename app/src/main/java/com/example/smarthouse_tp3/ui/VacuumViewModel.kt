package com.example.smarthouse_tp3.ui

import android.util.Log
import android.widget.ImageSwitcher
import androidx.compose.ui.graphics.Color
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceRoom
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceState
import kotlinx.coroutines.flow.update

class VacuumViewModel(deviceId : String) : DeviceViewModel(deviceId) {
    override fun fetchDevice(deviceId: String) {

        Log.d("MYDEVICEswitchVAC", uiState.value.toString())
        super.fetchDevice(deviceId)
        val status = uiState.value.state?.status
        _uiState.update {
            it.copy(
                deviceIcon = R.drawable.device_vacuum,
                switchState = !(status == "docked" || status == "inactive"),
                deviceIconColor = if (uiState.value.state?.status == "active") {
                    Color.Blue
                } else if (uiState.value.state?.status == "docked") {
                    Color.Green
                } else {
                    Color.Black
                }
            )
        }
    }

    override fun getSmallIconsList(): List<Int> {
        val iconsList = mutableListOf<Int>()
        iconsList.add(getBatteryIcon())

        return iconsList
    }

    fun getBatteryIcon() : Int {
        val batteryLevel = uiState.value.state?.batteryLevel
        if (uiState.value.state?.status == "docked") {
            return R.drawable.baseline_battery_charging_full_24
        } else if (batteryLevel != null) {
            if (batteryLevel < 5) {
                return R.drawable.baseline_battery_alert_24
            } else if (batteryLevel < 12.5) {
                return R.drawable.baseline_battery_0_bar_24
            } else if (batteryLevel < 25) {
                return R.drawable.baseline_battery_1_bar_24
            } else if (batteryLevel < 37.5) {
                return R.drawable.baseline_battery_2_bar_24
            } else if (batteryLevel < 50) {
                return R.drawable.baseline_battery_3_bar_24
            } else if (batteryLevel < 62.5) {
                return R.drawable.baseline_battery_4_bar_24
            } else if (batteryLevel < 75) {
                return R.drawable.baseline_battery_5_bar_24
            } else if (batteryLevel < 87.5) {
                return R.drawable.baseline_battery_6_bar_24
            } else {
                return R.drawable.baseline_battery_full_24
            }
        }
        return R.drawable.baseline_battery_full_24
    }


    override fun changeSwitchState() {
        if (!uiState.value.switchState && uiState.value.state?.batteryLevel!! <= 5) {
            return
        }
        super.changeSwitchState()
        if (uiState.value.switchState) {
            changeDeviceIconColor(Color.Blue)
            uiState.value.id?.let { executeAction(it, "start", arrayOf()) }
            updateUiState(status = "active")
        } else {
            changeDeviceIconColor(Color.Black)
            uiState.value.id?.let { executeAction(it, "pause", arrayOf()) }
            updateUiState(status = "inactive")
        }
    }


    private fun updateUiState(
        switchState : Boolean = uiState.value.switchState,
        deviceIconColor : Color = uiState.value.deviceIconColor,
        status: String? = uiState.value.state?.status,
        mode: String? = uiState.value.state?.mode,
        batteryLevel: Long? = uiState.value.state?.batteryLevel,
        location: NetworkDeviceRoom? = uiState.value.state?.location
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                switchState = switchState,
                deviceIconColor = deviceIconColor,
                state = NetworkDeviceState(
                    status = status,
                    mode = mode,
                    batteryLevel = batteryLevel,
                    location = location
                )
            )
        }
    }

    fun dock() {
        setAction("dock")
    }

    fun pause() {
        setAction("pause")
    }

    fun start() {
        setAction("start")
    }

    private fun setAction(action: String) {
        val state = uiState.value.state
        val newStatus: String = when (action) {
            "dock" -> {
                "docked"
            }

            "start" -> {
                "active"
            }

            else -> {
                "inactive"
            }
        }

        if (state != null && state.status != newStatus) {
            if (action == "dock") {
                updateUiState(status = newStatus, switchState = false)
                changeDeviceIconColor(Color.Green)
            } else {
                updateUiState(status = newStatus)
            }


            uiState.value.id?.let {
                executeAction(
                    it, action,
                    emptyArray()
                )
            }
        }
    }

    fun changeModeMop() {
        val state = uiState.value.state
        if (state != null && state.mode != "mop") {
            state.mode = VacuumMode.MOP.stringValue
            updateUiState(mode = state.mode)
            uiState.value.id?.let {
                executeAction(
                    it, "setMode",
                    arrayOf(uiState.value.state?.mode.toString())
                )
            }
        }
    }

    fun changeModeVacuum() {
        val state = uiState.value.state
        if (state != null && state.mode != "vacuum") {
            state.mode = VacuumMode.VACUUM.stringValue
            updateUiState(mode = state.mode)
            uiState.value.id?.let {
                executeAction(
                    it, "setMode",
                    arrayOf(uiState.value.state?.mode.toString())
                )
            }
        }
    }

    fun setLocation(room : NetworkDeviceRoom) {
        Log.d("MYROOM", room.toString())
        updateUiState(location = room)

        uiState.value.id?.let {
            executeAction(
                it, "setLocation",
                arrayOf(room.id!!)
            )
        }
    }
}


enum class VacuumMode(val index: Int, val stringValue: String) {
    VACUUM(0, "vacuum"),
    MOP(1, "mop"),
    DOCKED(2, "docked");

    companion object {
        fun fromIndex(value: Int): VacuumMode {
            return values().find { it.index == value } ?: VACUUM
        }
        fun fromString(value: String): VacuumMode {
            return values().find { it.stringValue == value } ?: VACUUM
        }
    }

}
