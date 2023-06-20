package com.example.smarthouse_tp3.ui

import android.util.Log
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

open class DeviceViewModel(private var deviceId : String) : ViewModel() {

    protected val _uiState = MutableStateFlow(DeviceUiState())
    val uiState: StateFlow<DeviceUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    open fun getSmallIconsList(): List<Int> {
        return emptyList()
    }
    fun dismissMessage() {
        _uiState.update { it.copy(message = null) }
    }

    // fun refresh() {
    //     uiState.value.device?.id?.let { fetchDevice(it) }
    // }

    fun isLoading() : Boolean {
        return uiState.value.isLoading
    }

    fun fetchDevice() {
        fetchDevice(deviceId)
    }

    open fun fetchDevice(deviceId: String) {
        //fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService.getDevice(deviceId = deviceId)
            }.onSuccess { response ->
                _uiState.update { it.copy(
                    isLoading = false,
                    id = response.body()?.device?.id,
                    name = response.body()?.device?.name,
                    type = response.body()?.device?.type,
                    state = response.body()?.device?.state,
                    room = response.body()?.device?.room,
                    meta = response.body()?.device?.meta
                ) }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        message = e.message,
                        isLoading = false
                    )
                }
            }
        }
        Log.d("MYDEVICE", uiState.value.toString())
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
        _uiState.update { currentState->
                currentState.copy(
                  switchState = !uiState.value.switchState
                ) }
    }


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

    fun getDeviceId(): String? {
        return uiState.value.id
    }

    fun copy(): DeviceViewModel {
        val copiedViewModel = DeviceViewModel(deviceId)

        // Copy the UI state
        val originalUiState = this._uiState.value
        copiedViewModel._uiState.value = originalUiState.copy()

        // Copy other properties
        // (Add any additional properties you want to copy)
        copiedViewModel.fetchJob = this.fetchJob

        return copiedViewModel
    }
}

