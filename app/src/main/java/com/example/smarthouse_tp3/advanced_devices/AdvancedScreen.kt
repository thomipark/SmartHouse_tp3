package com.example.smarthouse_tp3.advanced_devices

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthouse_tp3.Device
import com.example.smarthouse_tp3.DeviceAirConditioner
import com.example.smarthouse_tp3.DeviceLight
import com.example.smarthouse_tp3.DeviceOven
import com.example.smarthouse_tp3.Type
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun DeviceConfigScreen(device: Device) {
    Scaffold(
        topBar = {
            DeviceTopBar(device)
        },
        content = {it
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
            ){
                DeviceBody(device)
            }
        }
    )
}

@Composable
fun DeviceTopBar(device: Device) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(device.getIcon()),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .weight(1f),
                //colorFilter = ColorFilter.tint(color = Color.Black) // Para cambiar el color del icono
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = device.getName(),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = device.getSwitchState(),
                onCheckedChange = { device.changeSwitchState() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Green,
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
        }
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun DeviceBody(device: Device) {
    when (device.deviceType) {
        Type.OVEN -> OvenConfigScreen()
        Type.AC -> AirConditionerConfigScreen(device = device as DeviceAirConditioner)
        Type.LIGHT -> LightConfigScreen(device = device as DeviceLight)//, changeColor = { device.changeColor(it) }) // No se porque no anda esto
        Type.FAUCET -> FaucetConfigScreen()
        Type.VACUUM -> VacuumConfigScreen()
        // Agrega más casos según los diferentes tipos de dispositivos que tengas
    }
}

@Composable
fun OvenConfigScreen() {
    // Configuración específica para un horno
    // Agrega composables y lógica según las necesidades del horno
}

@Composable
fun AirConditionerConfigScreen(
    device : DeviceAirConditioner
) {
    Row {
        Column() {
            Text(
                text = "${device.getTempareature().value} °C"
            )
        }

        Column() {

        }
    }

}

@Composable
fun LightConfigScreen(
    device: DeviceLight
    //changeColor: (String) -> Unit
) {
    val controller = rememberColorPickerController()
    var color: Color = Color.Black
    var hexCode: String = "hola"
    var fromUser: Boolean = false
    Text(
        text = "HUE",
        fontSize = 24.sp,
        modifier = Modifier.padding(start = 16.dp)
    )
    HsvColorPicker(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(10.dp),
        controller = controller,
        onColorChanged = { colorEnvelope: ColorEnvelope ->
            color = colorEnvelope.color // ARGB color value.
            device.changeColor(colorEnvelope.hexCode) // Color hex code, which represents clor value.
            fromUser = colorEnvelope.fromUser // Represents this event is triggered by user or not.
        }
    )
    Text(
        text = "INTENSITY",
        fontSize = 24.sp,
        modifier = Modifier.padding(start = 16.dp)
    )
    AlphaSlider(
        modifier = Modifier
            .padding(10.dp)
            .height(35.dp),
        controller = controller,
    )
    Column (
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = device.getHexCode().value,
            fontSize = 24.sp
        )
        AlphaTile(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(80.dp)
                .clip(RoundedCornerShape(6.dp)),
            controller = controller
        )
    }
}

@Composable
fun FaucetConfigScreen() {
    // Configuración específica para un faucet
    // Agrega composables y lógica según las necesidades del faucet
}
@Composable
fun VacuumConfigScreen() {
    // Configuración específica para una vacuum
    // Agrega composables y lógica según las necesidades de la vacuum
}

@Preview
@Composable
fun DeviceTopBarPreview() {
    val device = DeviceAirConditioner(
        name = "My AC",
    )
    DeviceConfigScreen(device = device)
}
