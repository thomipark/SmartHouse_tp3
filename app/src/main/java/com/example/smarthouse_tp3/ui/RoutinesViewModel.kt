package com.example.smarthouse_tp3.ui

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.RoutineConfigScreen
import com.example.smarthouse_tp3.data.network.RetrofitClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoutinesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RoutinesUiState())
    val uiState: StateFlow<RoutinesUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun dismissMessage() {
        _uiState.update { it.copy(message = null) }
    }

    fun fetchRoutines() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService.getAllRoutines()
            }.onSuccess { response ->
                _uiState.update {
                    it.copy(
                        networkRoutineList = response.body(),
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
    }

    fun executeRoutine(routineId : String){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService.executeRoutine(routineId)
            }.onSuccess {

            }
        }
    }
}

@Preview
@Composable
fun RoutinePreview() {
    val routineViewModel : RoutinesViewModel = viewModel()
    routineViewModel.fetchRoutines()
    val uiState by routineViewModel.uiState.collectAsState()

    Text(
        text = uiState.toString()
    )
}

