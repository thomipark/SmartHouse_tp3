package com.example.smarthouse_tp3.advanced_devices

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthouse_tp3.Device
import com.example.smarthouse_tp3.DeviceOven
import com.example.smarthouse_tp3.Type

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DeviceConfigScreen(device: Device) {
    Scaffold(
        topBar = {
            DeviceTopBar(device)
        },
        content = {
            DeviceBody(device)
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
                    .weight(1f)
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
        Type.AC -> AirConditionerConfigScreen()
        Type.LIGHT -> LightConfigScreen()
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
fun AirConditionerConfigScreen() {
    // Configuración específica para un aire acondicionado
    // Agrega composables y lógica según las necesidades del aire acondicionado
}

@Composable
fun LightConfigScreen() {
    // Configuración específica para una luz
    // Agrega composables y lógica según las necesidades de la luz
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
    val device = DeviceOven(
        name = "My Device",
    )
    DeviceTopBar(device = device)
}
