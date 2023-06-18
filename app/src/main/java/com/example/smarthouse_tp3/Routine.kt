package com.example.smarthouse_tp3

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class Routine(name: String, devices: List<RoutineDevice> ?= null) {
    private var name: String
    private var routineDevices: List<RoutineDevice>
    private var switchState = mutableStateOf(true)
    private var isPlaying = mutableStateOf(false)
    private var buttonColor = mutableStateOf(Color.Gray)

    init {
        this.name = name
        this.routineDevices = devices ?: emptyList()
    }

    fun getRoutineName(): String {
        return name
    }

    fun isPlaying(): Boolean {
        return isPlaying.value
    }

    fun togglePlay() {
        isPlaying.value = !isPlaying.value
        buttonColor.value = if (isPlaying.value) Color.Green else Color.Gray
    }

    fun getButtonColor(): Color {
        return buttonColor.value
    }

    fun getRoutineDevices(): List<RoutineDevice> {
        return routineDevices
    }

    fun addRoutineDevice(routineDevice: RoutineDevice) {
        routineDevices = routineDevices + routineDevice
    }

    fun removeRoutineDevice(routineDevice: RoutineDevice) {
        routineDevices = routineDevices - routineDevice
    }
}

class RoutineDevice(name: String, actions: List<Action> ?= null) {
    private var name: String
    private var actions: List<Action>

    init {
        this.name = name
        this.actions = actions ?: emptyList()
    }

    fun getDeviceName(): String {
        return name
    }

    fun getActions(): List<Action> {
        return actions
    }

    fun addAction(action: Action) {
        actions = actions + action
    }

    fun removeAction(action: Action) {
        actions = actions - action
    }
}

class Action(name: String) {
    private var name: String

    init {
        this.name = name
    }

    fun getActionName(): String {
        return name
    }
}
