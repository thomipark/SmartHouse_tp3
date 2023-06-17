package com.example.smarthouse_tp3.ui

import androidx.lifecycle.ViewModel
import com.example.smarthouse_tp3.Device
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NavigationUiState())
    val uiState: StateFlow<NavigationUiState> = _uiState.asStateFlow()


    fun selectNewDevice(device : Device){
        _uiState.update { currentState ->
            currentState.copy(
                selectedDevice = device
            )
        }
    }
}