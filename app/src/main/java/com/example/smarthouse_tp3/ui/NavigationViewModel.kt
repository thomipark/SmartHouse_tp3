package com.example.smarthouse_tp3.ui

import androidx.lifecycle.ViewModel
import com.example.smarthouse_tp3.Device
import com.example.smarthouse_tp3.Routine
import com.example.smarthouse_tp3.data.network.model.NetworkRoutine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NavigationUiState())
    val uiState: StateFlow<NavigationUiState> = _uiState.asStateFlow()


    fun selectNewDevice(device: Device) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedDevice = device
            )
        }
    }

    fun selectNewRoutine(routine: Routine) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedRoutine = routine
            )
        }
    }

    fun selectNewDeviceViewModel(deviceVM: DeviceViewModel) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedDeviceViewModel = deviceVM
            )
        }
    }

    fun selectNewNetworkRoutine(networkRoutine: NetworkRoutine) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedNetworkRoutine = networkRoutine
            )
        }
    }

    fun addDevicesNotificationList (){
        val id = _uiState.value.selectedDeviceViewModel?.getDeviceId()
        _uiState.update { currentState ->
            val updatedList = currentState.devicesNotificationList.toMutableList()
            if (id != null) {
                updatedList.add(id)
            }
            currentState.copy(devicesNotificationList = updatedList)
        }
    }

    fun removeDevicesNotificationList (){
        val id = _uiState.value.selectedDeviceViewModel?.getDeviceId()
        _uiState.update { currentState ->
            val updatedList = currentState.devicesNotificationList.toMutableList()
            if (id != null) {
                updatedList.remove(id)
            }
            currentState.copy(devicesNotificationList = updatedList)
        }
    }

    fun containsNotification (id : String?) : Boolean {
        if (id == null){
            return false
        }
        return uiState.value.devicesNotificationList.contains(id)
    }
}