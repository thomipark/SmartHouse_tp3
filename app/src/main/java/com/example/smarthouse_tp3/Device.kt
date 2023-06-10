package com.example.smarthouse_tp3


import androidx.compose.runtime.mutableStateOf

abstract class Device(name: String ) {
    var switchState = mutableStateOf(true)
    private var name: String

    init{
        this.name = name
    }

    /**
     * Returns the icon of the device depending its state On or Off
     */
    abstract fun getIcon() : Int

    fun getName(): String{
        return name
    }

    fun getSwitchState(): Boolean{
        return switchState.value
    }

    fun changeSwitchState() {
        switchState.value = !switchState.value
    }

    /**
     * Nose como declarar switch state sin definirla, asi que dejo esta funcion
     * para darle valor inicial cuando la lea de la api. Por ahora esta funcion
     * no tiene uso en Device.kt
     */
    fun setSwitchState(state: Boolean){
        switchState.value = state
    }

}