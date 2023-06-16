package com.example.smarthouse_tp3.com.example.smarthouse_tp3

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class Routine(name: String) {
    private var switchState = mutableStateOf(true)
    private var name: String
    private var isPlaying = mutableStateOf(false)
    private var buttonColor = mutableStateOf(Color.Gray)

    init {
        this.name = name
    }

    fun getName(): String {
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
}
