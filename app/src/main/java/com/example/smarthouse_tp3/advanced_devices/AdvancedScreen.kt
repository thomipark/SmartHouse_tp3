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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.DeviceVacuum
import com.example.smarthouse_tp3.ui.AirConditionerViewModel
import com.example.smarthouse_tp3.ui.DeviceViewModel
import com.example.smarthouse_tp3.ui.FaucetViewModel
import com.example.smarthouse_tp3.ui.LightViewModel
import com.example.smarthouse_tp3.ui.NavigationViewModel
import com.example.smarthouse_tp3.ui.OvenViewModel
import com.example.smarthouse_tp3.ui.VacuumViewModel

@Composable
fun DeviceConfigScreen(navigationViewModel : NavigationViewModel) {

    val navUiState by navigationViewModel.uiState.collectAsState()
    val viewModel = navUiState.selectedDeviceViewModel ?: return

    val uiState by viewModel.uiState.collectAsState()

    Log.d("MiLog", uiState.toString())

    // uiState.id?.let { viewModel.fetchDevice(it) }

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
            //Text(text = uiState.switchState.toString())
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

    when (uiState.type?.name) {
        "ac"        -> AirConditionerConfigScreen(viewModel = viewModel as AirConditionerViewModel)
        "lamp"      -> LightConfigScreen(viewModel = viewModel as LightViewModel)
        "vacuum"    -> VacuumConfigScreen(viewModel = viewModel as VacuumViewModel)
        "oven"      -> OvenConfigScreen(viewModel = viewModel as OvenViewModel)
        "faucet"    -> FaucetConfigScreen(viewModel = viewModel as FaucetViewModel)
        else -> {
            Column() {
                // Text(text = uiState.toString())
                Text(
                    text = stringResource(R.string.device_type_error)
                )
            }
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

    // DeviceConfigScreen(device = device, deviceId = lampId)
}
