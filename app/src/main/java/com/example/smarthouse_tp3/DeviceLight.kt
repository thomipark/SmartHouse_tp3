package com.example.smarthouse_tp3


class DeviceLight(name: String) : Device(name) {
    //    var temperature = mutableStateOf(1)
    //    var grillMode = mutableStateof()
    override var deviceType: Type = Type.LIGHT
    var hexCode: String = "hola"

    override fun getIcon(): Int {
        return if (getSwitchState()){
            R.drawable.device_lightbulb_on
        } else {
            R.drawable.device_lightbulb_on
        }
    }

    fun changeColor() {
        hexCode = "hello"
    }
}