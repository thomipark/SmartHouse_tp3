package com.example.smarthouse_tp3


class DeviceAirConditioner(name: String) : Device(name) {
    //    var temperature = mutableStateOf(1)
    //    var grillMode = mutableStateof()
    override var deviceType: Type = Type.AC

    override fun getIcon(): Int {
        return if (getSwitchState()){
            R.drawable.device_oven_on
        } else {
            R.drawable.device_oven_off
        }
    }



}