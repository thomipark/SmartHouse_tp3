package com.example.smarthouse_tp3


 import androidx.compose.runtime.MutableState
 import androidx.compose.runtime.mutableStateOf
 import androidx.compose.ui.graphics.Color

 abstract class Device(name: String ) {
     private var switchState = mutableStateOf(false)
     private var name: String
     private var room: String = "Kitchen"
     private var deviceIconColor = mutableStateOf(Color.Black)
     abstract var deviceIcon : Int
     abstract var deviceType: Type


     abstract fun getSmallIconsList(): List<Int>

     init{
         this.name = name
     }

     /**
      * Returns the icon of the device, should be used in pair with getDeviceIconColor
      */
     fun getIcon() :Int {
         return deviceIcon
     }

     /**
      * Returns the color of the device when ON and OFF
      */
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
      * no tiene uso en NetworkDevice.kt
      */
     fun setSwitchState(state: Boolean){
         switchState.value = state
     }


     /**
      *  Returns a list of icons that have the traits of the device, should be
      *  used in smallTile when ON
      */

 }