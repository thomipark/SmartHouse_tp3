package com.example.smarthouse_tp3.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.smarthouse_tp3.Device
import com.example.smarthouse_tp3.MainActivity
import com.example.smarthouse_tp3.Routine
import com.example.smarthouse_tp3.data.network.model.NetworkRoutine
import com.example.smarthouse_tp3.notification.ServerEventReceiver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NavigationUiState())
    val uiState: StateFlow<NavigationUiState> = _uiState.asStateFlow()


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
        if (id != null) {
            NotificationList.list.add(id)
            Log.d("ServerEventReceiver add", id.toString())
            Log.d("ServerEventReceiver listNM", NotificationList.list.toString())
        }
    }

    fun removeDevicesNotificationList (){
        val id = _uiState.value.selectedDeviceViewModel?.getDeviceId()
        if (id != null) {
            NotificationList.list.remove(id)
            Log.d("ServerEventReceiver remove", id.toString())
            Log.d("ServerEventReceiver listNM", FavouritesList.list.toString())
        }
    }
    fun addDevicesFavoriteList() {
        val id = _uiState.value.selectedDeviceViewModel?.getDeviceId()
        if (id != null) {
            FavouritesList.list.add(id)

        }
    }

    fun removeDevicesFavoriteList() {
        val id = _uiState.value.selectedDeviceViewModel?.getDeviceId()
        if (id != null) {
            FavouritesList.list.remove(id)

        }
    }


    fun setNotification(){
        _uiState.update { currentState ->
            currentState.copy(
                notification = !uiState.value.notification
            )
        }
    }

    fun updateNotification(not : Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                notification = not
            )
        }
    }

    fun setFavourite(){
        _uiState.update { currentState ->
            currentState.copy(
                favourite = !uiState.value.favourite
            )
        }
    }

    fun updateFavourite(not : Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                favourite = not
            )
        }
    }

}