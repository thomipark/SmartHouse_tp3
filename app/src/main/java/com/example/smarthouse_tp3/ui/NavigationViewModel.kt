package com.example.smarthouse_tp3.ui

import androidx.lifecycle.ViewModel
import com.example.smarthouse_tp3.Device
import com.example.smarthouse_tp3.Routine
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
}