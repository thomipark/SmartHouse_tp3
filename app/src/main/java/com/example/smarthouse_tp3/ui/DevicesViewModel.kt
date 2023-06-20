package com.example.smarthouse_tp3.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.data.network.RetrofitClient
import com.example.smarthouse_tp3.deviceViewModelMaker
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DevicesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DevicesUiState())
    val uiState: StateFlow<DevicesUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun dismissMessage() {
        _uiState.update { it.copy(message = null) }
    }

    fun fetchDevices() {
        Log.d("FetchingDevices", "FETCHING DEVICES")
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService.getAllDevices()
            }.onSuccess { response ->
                _uiState.update { it ->
                    it.copy(
                        devices = response.body(),
                        isLoading = false,
                        devicesId = response.body()?.devices?.map { it1 -> it1.id } as List<String>
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

    @Composable
    fun fetchDeviceById(deviceId: String) {
        // var deviceViewModel : DeviceViewModel by remember { mutableStateOf() }

        // LaunchedEffect(deviceId) {
        //     val device = uiState.value.deviceList.find { it.getDeviceId() == deviceId }
        //     if (device != null) {
        //         deviceViewModel = device
        //     } else {
        //         val newDeviceViewModel :DeviceViewModel = viewModel()
        //         newDeviceViewModel.fetchDevice(deviceId)
        //         deviceViewModel = newDeviceViewModel
        //     }
        // }

        // return deviceViewModel ?: throw IllegalStateException("DeviceViewModel is null")
    }

    fun fetchNameByID(id: String): String {
        return _uiState.value.devices?.devices?.find { it.id == id }?.name ?: "Unknown"
    }

    fun fetchTypeByID(id: String): String {
        return _uiState.value.devices?.devices?.find { it.id == id }?.type?.name ?: "Unknown"
    }
}