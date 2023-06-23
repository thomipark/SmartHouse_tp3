package com.example.smarthouse_tp3.advanced_devices

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.smarthouse_tp3.R
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
                        .size(55.dp)
                        .weight(1f),
                    // Para cambiar el color del icono
                    colorFilter = ColorFilter.tint(color =
                    if(uiState.deviceIconColor == Color.Black && !MaterialTheme.colors.isLight)
                        Color.LightGray else uiState.deviceIconColor)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            uiState.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
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
            color = if(!MaterialTheme.colors.isLight) Color.LightGray else Color.Black,
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
            Column {
                // Text(text = uiState.toString())
                Text(
                    text = stringResource(R.string.device_type_error)
                )
            }
        }
    }
}

/*
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
*/