package com.example.smarthouse_tp3.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthouse_tp3.data.network.RetrofitClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class DeviceViewModel : ViewModel() {

    protected val _uiState = MutableStateFlow(DeviceUiState())
    val uiState: StateFlow<DeviceUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    abstract fun getSmallIconsList(): List<Int>
    fun dismissMessage() {
        _uiState.update { it.copy(message = null) }
    }

    // fun refresh() {
    //     uiState.value.device?.id?.let { fetchDevice(it) }
    // }

    open fun fetchDevice(deviceId: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService.getDevice(deviceId = deviceId)
            }.onSuccess { response ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        id = response.body()?.device?.id,
                        name = response.body()?.device?.name,
                        type = response.body()?.device?.type,
                        state = response.body()?.device?.state,
                        room = response.body()?.device?.room,
                        meta = response.body()?.device?.meta
                    )
                }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        message = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun executeAction(deviceId: String, actionName: String, params: Array<String>) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService.executeAction(deviceId, actionName, params)
            }.onSuccess {
                uiState.value.id?.let { it1 -> fetchDevice(it1) }
            }
        }
        // .onFailure { e ->
        //     _uiState.update { it.copy(
        //         message = e.message,
        //         isLoading = false
        //     )
        //     }
        // }
        // }
    }

    open fun changeSwitchState() {
        _uiState.update { currentState ->
            currentState.copy(
                switchState = !uiState.value.switchState
            )
        }
    }

    /**
     * Nose como declarar switch state sin definirla, asi que dejo esta funcion
     * para darle valor inicial cuando la lea de la api. Por ahora esta funcion
     * no tiene uso en NetworkDevice.kt
     */
    fun setSwitchState(state: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                switchState = state
            )
        }

    }

    fun changeDeviceIconColor(newColor: Color) {
        _uiState.update { currentState ->
            currentState.copy(
                deviceIconColor = newColor
            )
        }
    }


}

