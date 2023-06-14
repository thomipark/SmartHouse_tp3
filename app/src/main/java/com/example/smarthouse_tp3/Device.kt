package com.example.smarthouse_tp3


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

abstract class Device(name: String ) {
    private var switchState = mutableStateOf(false)
    private var name: String
    private var deviceIconColor = mutableStateOf(Color.Black)
    abstract var deviceIcon : Int
    abstract var deviceType: Type

    init{
        this.name = name
    }

    /**
     * Returns the icon of the device depending its state On or Off
     */
    // abstract fun getIcon() : Int

    fun getIcon() :Int {
        return deviceIcon
    }

    fun getDeviceIconColor() : MutableState<Color> {
        return deviceIconColor
    }
    fun changeDeviceIconColor(newColor : Color) {
        deviceIconColor.value = newColor
    }

    fun getType() : Type{
        return deviceType
    }

    fun getName(): String{
        return name
    }

    fun getSwitchState(): Boolean{
        return switchState.value
    }

    open fun changeSwitchState() {
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