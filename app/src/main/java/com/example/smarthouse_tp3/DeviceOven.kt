package com.example.smarthouse_tp3

class DeviceOven(name: String) : Device(name) {
    //    var temperature = mutableStateOf(1)
    //    var grillMode = mutableStateof()

    override fun getIcon(): Int {
        return if (switchState.value){
            R.drawable.device_oven_on
        } else {
            R.drawable.device_oven_off
        }
    }

}