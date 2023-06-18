package com.example.smarthouse_tp3.ui

import androidx.compose.ui.graphics.Color
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceRoom
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceState
import kotlinx.coroutines.flow.update

class VacuumViewModel : DeviceViewModel() {
    override fun fetchDevice(deviceId: String) {
        super.fetchDevice(deviceId)
        _uiState.update {
            it.copy(
                deviceIcon = R.drawable.device_vacuum
            )
        }
    }

    override fun getSmallIconsList(): List<Int> {
        val iconsList = mutableListOf<Int>()
        iconsList.add(getBatteryIcon())

        return iconsList
    }

    fun getBatteryIcon(): Int {
        if (uiState.value.state?.mode == VacuumMode.CHARGING.stringValue) {
            return R.drawable.baseline_battery_charging_full_24
        } else if (uiState.value.state?.batteryLevel!! < 5) {
            return R.drawable.baseline_battery_alert_24
        } else if (uiState.value.state?.batteryLevel!! < 12.5) {
            return R.drawable.baseline_battery_0_bar_24
        } else if (uiState.value.state?.batteryLevel!! < 25) {
            return R.drawable.baseline_battery_1_bar_24
        } else if (uiState.value.state?.batteryLevel!! < 37.5) {
            return R.drawable.baseline_battery_2_bar_24
        } else if (uiState.value.state?.batteryLevel!! < 50) {
            return R.drawable.baseline_battery_3_bar_24
        } else if (uiState.value.state?.batteryLevel!! < 62.5) {
            return R.drawable.baseline_battery_4_bar_24
        } else if (uiState.value.state?.batteryLevel!! < 75) {
            return R.drawable.baseline_battery_5_bar_24
        } else if (uiState.value.state?.batteryLevel!! < 87.5) {
            return R.drawable.baseline_battery_6_bar_24
        } else {
            return R.drawable.baseline_battery_full_24
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


    fun updateUiState(
        status: String? = uiState.value.state?.status,
        mode: String? = uiState.value.state?.mode,
        batteryLevel: Long? = uiState.value.state?.batteryLevel,
        location: NetworkDeviceRoom? = uiState.value.state?.location
    ) {
        _uiState.update { currentState ->
            currentState.copy(
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

    fun changeModeMop() {
        val state = uiState.value.state
        if (state != null) {
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
        if (state != null) {
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

    fun setLocation(roomId: String) {
        val state = uiState.value.state
        if (state != null) {
            // updateUiState(location = RoomViewModel.getFromId())

            uiState.value.id?.let {
                executeAction(
                    it, "setLocation",
                    arrayOf(roomId)
                )
            }
        }
    }
}


enum class VacuumMode(val index: Int, val stringValue: String) {
    VACUUM(0, "vacuum"),
    MOP(1, "mop"),
    CHARGING(2, "");

    companion object {
        fun fromIndex(value: Int): VacuumMode {
            return values().find { it.index == value } ?: VACUUM
        }
    }

}
