package com.example.smarthouse_tp3.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthouse_tp3.data.network.RetrofitClient
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceRoom
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoomsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RoomsUiState())
    val uiState: StateFlow<RoomsUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun dismissMessage() {
        _uiState.update { it.copy(message = null) }
    }

    fun fetchRooms() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService.getAllRooms()
            }.onSuccess { response ->
                _uiState.update {
                    it.copy(
                        rooms = response.body(),
                        isLoading = false
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
        Log.d("MYROOM", uiState.value.toString())
    }

    fun getRoomFromName(roomName: String): NetworkDeviceRoom {
        return uiState.value.rooms?.rooms?.find { it.name == roomName }
            ?: return NetworkDeviceRoom()
    }
}