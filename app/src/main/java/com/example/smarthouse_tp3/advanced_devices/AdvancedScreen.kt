package com.example.smarthouse_tp3.advanced_devices

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthouse_tp3.Device
import com.example.smarthouse_tp3.DeviceAirConditioner
import com.example.smarthouse_tp3.DeviceFaucet
import com.example.smarthouse_tp3.DeviceLight
import com.example.smarthouse_tp3.DeviceOven
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.Type
import com.example.smarthouse_tp3.ui.VacuumMode
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.DeviceVacuum
import com.example.smarthouse_tp3.ui.AirConditionerViewModel
import com.example.smarthouse_tp3.ui.DeviceViewModel
import com.example.smarthouse_tp3.ui.LightViewModel
import com.example.smarthouse_tp3.ui.VacuumViewModel

@Composable
fun DeviceConfigScreen(device : Device, deviceId : String? = "1fdadb82ef594f00") {

    val viewModel: LightViewModel = viewModel()
    if (deviceId != null) {
        viewModel.fetchDevice(deviceId)
    }

    Scaffold(
        topBar = {
            DeviceTopBar(viewModel)
        },
        content = {it
            DeviceBody(viewModel)
        }
    )
}

@Composable
fun DeviceTopBar(viewModel: DeviceViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            uiState.deviceIcon?.let { painterResource(it) }?.let {
                Image(
                    painter = it,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .weight(1f),
                    colorFilter = ColorFilter.tint(color = uiState.deviceIconColor) // Para cambiar el color del icono
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            uiState.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f)
                )
            }
            Switch(
                checked = uiState.switchState,
                onCheckedChange = { viewModel.changeSwitchState() },
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
fun DeviceBody(viewModel : DeviceViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val deviceType = uiState.type?.name?.let { Type.fromString(it) }
    Log.d("ERRORMIO1", uiState.toString())

    when (uiState.type?.name) {
        // Type.OVEN -> OvenConfigScreen(device as DeviceOven)
        "ac"        -> AirConditionerConfigScreen(viewModel = viewModel as AirConditionerViewModel)
        "lamp"      -> LightConfigScreen(viewModel = viewModel as LightViewModel)//, changeColor = { device.changeColor(it) }) // No se porque no anda esto
        "vacuum"    -> VacuumConfigScreen(viewModel = viewModel as VacuumViewModel)
        // Type.FAUCET -> FaucetConfigScreen(device as DeviceFaucet)
        // Type.VACUUM -> VacuumConfigScreen(device = device as DeviceVacuum) }
        else -> {
            Column() {
                Text(text = uiState.toString())
            }
        }
    }
}


@Composable
fun OvenConfigScreen(device: DeviceOven) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.8f),

                ) {
                Text(
                    text = "${device.getTemperature().value}°C",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(start = 30.dp, bottom = 8.dp),
                    fontSize = 80.sp,
                )
            }
            Column(
                modifier = Modifier.weight(0.2f)
            ) {
                IconButton(
                    onClick = { device.increaseTemperature() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Increase Temperature"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                IconButton(
                    onClick =
                    { device.decreaseTemperature() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.minus),
                        contentDescription = "Decrease Temperature"
                    )
                }
            }
        }
    }

    val iconfanIds = listOf(
        R.drawable.fan,
        R.drawable.fan_off
    )

    val iconGrillIds = listOf(
        R.drawable.grill,
        R.drawable.grill_outline
    )

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    device.iterateFanMode()
                }
            ) {
                Icon(
                    painter = painterResource(iconfanIds[device.getFanMode().value.index]),
                    contentDescription = "Icon",
                    modifier = Modifier.size(100.dp)
                )
            }
            Text(
                text = "Fan: ${device.getFanMode().value.stringValue}",
                style = MaterialTheme.typography.body1
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    device.iterateGrillMode()
                }
            ) {
                Icon(
                    painter = painterResource(iconGrillIds[device.getGrillMode().value.index]),
                    contentDescription = "Icon",
                    modifier = Modifier.size(100.dp)
                )
            }
            Text(
                text = "Grill: ${device.getGrillMode().value.stringValue}",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )

        }
    }
    val iconHeatIds = listOf(
        R.drawable.border_bottom_variant,
        R.drawable.border_top_variant,
        R.drawable.border_top_bottom_variant
    )

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    device.iterateHeatMode()
                }
            ) {
                Icon(
                    painter = painterResource(iconHeatIds[device.getHeatMode().value.index]),
                    contentDescription = "Icon",
                    modifier = Modifier.size(100.dp)
                )
            }
            Text(
                text = "Heat: ${device.getHeatMode().value.stringValue}",
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview
@Composable
fun DeviceTopBarPreview() {
    val device = DeviceLight(
        name = "My Light",
    )


    val ACid= "d495cc0b87d1e918"
    val lampId = "4d842b03d28e19bc"
    val vacId  = "985376562da43a64"

    DeviceConfigScreen(device = device, deviceId = vacId)
}
