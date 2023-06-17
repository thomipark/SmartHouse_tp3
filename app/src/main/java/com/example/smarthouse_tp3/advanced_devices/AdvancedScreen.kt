package com.example.smarthouse_tp3.advanced_devices

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
import com.example.smarthouse_tp3.ui.DeviceVacuum
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
import com.example.smarthouse_tp3.FaucetUnits

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
                colorFilter = ColorFilter.tint(color = device.getDeviceIconColor().value) // Para cambiar el color del icono
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
        Type.OVEN -> OvenConfigScreen(device as DeviceOven)
        Type.AC -> AirConditionerConfigScreen(device = device as DeviceAirConditioner)
        Type.LIGHT -> LightConfigScreen(device = device as DeviceLight)//, changeColor = { device.changeColor(it) }) // No se porque no anda esto
        Type.FAUCET -> FaucetConfigScreen(device as DeviceFaucet)
        Type.VACUUM -> VacuumConfigScreen(device = device as DeviceVacuum) }
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

@Composable
fun AirConditionerConfigScreen(
    device : DeviceAirConditioner
) {
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
        R.drawable.fan_auto,
        R.drawable.fan,
        R.drawable.fan_speed_1,
        R.drawable.fan_speed_2,
        R.drawable.fan_speed_3
    )


    val iconModeIds= listOf(
        R.drawable.snowflake,
        R.drawable.weather_sunny,
        R.drawable.fan
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
                    device.iterateMode()
                }
            ) {
                Icon(
                    painter = painterResource(iconModeIds[device.getMode().value.index]),
                    contentDescription = "Icon",
                    modifier = Modifier.size(100.dp)
                )
            }
            Text(
                text = "MODE",
                style = MaterialTheme.typography.body1
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    device.iterateFanSpeed()
                }
            ) {
                Icon(
                    painter = painterResource(iconfanIds[device.getFanSpeed().value.index]),
                    contentDescription = "Icon",
                    modifier = Modifier.size(100.dp)
                )
            }
            Text(
                text = "Fan Speed:\n${device.getFanSpeed().value.stringValue}",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
    }


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
                    device.increaseVerticalFanDirection()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_drop_up_24),
                    contentDescription = "Icon",
                    modifier = Modifier.size(100.dp)
                )
            }

            Text(
                text = device.getVerticalFanDirection().value.stringValue
            )

            IconButton(
                onClick = {
                    device.decreaseVerticalFanDirection()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_drop_down_24),
                    contentDescription = "Icon",
                    modifier = Modifier.size(100.dp)

                )
            }

        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row {
                IconButton(
                    onClick = {
                        device.decreaseHorizontalFanDirection()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_left_24),
                        contentDescription = "Icon",
                        modifier = Modifier.size(80.dp)
                    )
                }



                IconButton(
                    onClick = {
                        device.increaseHorizontalFanDirection()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_right_24),
                        contentDescription = "Icon",
                        modifier = Modifier.size(80.dp)

                    )
                }
            }
            Text(
                text = device.getHorizontalFanDirection().value.stringValue
            )

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
fun FaucetConfigScreen(device: DeviceFaucet) {
    var sliderValue = device.getWaterLevel().value
    val volumeUnits = device.getFaucetUnitValues()
    val selectedUnit = remember { mutableStateOf(device.getFaucetUnitValues()[FaucetUnits.KILOLITERS.index]) }
    val formattedValue = (sliderValue / 100f).times(100).toInt()

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "To Dispense: ${formattedValue}%",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Slider(
                value = sliderValue,
                onValueChange = { newValue ->
                    sliderValue = newValue
                    device.changeWaterLevel(sliderValue)
                },
                valueRange = 0f..100f,
                modifier = Modifier.width(300.dp)
            )
        }
    }

    val isDropdownExpanded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Button(
            onClick = { isDropdownExpanded.value = true },
            modifier = Modifier.clickable { isDropdownExpanded.value = true }
                .size(width = 200.dp, height = 48.dp)
        ) {
            Text(
                text = "Select Unit: ${selectedUnit.value}",
                fontWeight = FontWeight.Bold
            )
        }
        DropdownMenu(
            expanded = isDropdownExpanded.value,
            onDismissRequest = { isDropdownExpanded.value = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            volumeUnits.forEach { unit ->
                DropdownMenuItem(onClick = {
                    selectedUnit.value = unit
                    device.changeUnit(unit)
                    isDropdownExpanded.value = false
                }) {
                    Text(text = unit)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Button(
            onClick = { },
            modifier = Modifier.size(width = 200.dp, height = 48.dp)
        ) {
            Text(
                text = "Dispense",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun VacuumConfigScreen(
    device: DeviceVacuum
) {


    var showDropdown by remember { mutableStateOf(false) }
    val options = listOf("Kitchen", "Santi's Room", "Living Room")


    var mopColor : Color = Color.White
    var vacColor : Color = Color.White
    var dockColor : Color = Color.White

    if (device.getMode().value == VacuumMode.MOP) {
        mopColor = Color.LightGray
    }
    else if (device.getMode().value == VacuumMode.VACUUM) {
        vacColor = Color.LightGray
    }
    else {
        dockColor = Color.LightGray
    }

    val batteryLevel: Int = device.getBattery().value
    val batteryIcon: Int = device.getBatteryIcon()


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
            Image(
                painter = painterResource(batteryIcon),
                contentDescription = "Icon",
                modifier = Modifier.size(80.dp)
            )
            Column(
                modifier = Modifier.weight(0.8f),

                ) {
                Text(
                    text = "${batteryLevel}%",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(start = 30.dp, bottom = 8.dp),
                    fontSize = 80.sp,
                )
            }
        }
    }
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = vacColor,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp)
            ) {
                IconButton(
                    onClick = {
                        device.changeModeVacuum()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.vacuum_outline),
                        contentDescription = "Icon",
                        modifier = Modifier.size(100.dp)
                    )
                }

            }
            Text(
                text = "VACUUM",
                style = MaterialTheme.typography.body1
            )

        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = mopColor,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp)
            ) {
                IconButton(
                    onClick = {
                        device.changeModeMop()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_water_drop_24),
                        contentDescription = "Icon",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
            Text(
                text = "MOP",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
    }

    Row(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = dockColor,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {

                Button(
                    onClick = {
                        device.dock()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    elevation = null,
                    border = null
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_electric_bolt_24),
                        contentDescription = "Button Icon",
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "RETURN TO CHARGING STATION",
                        modifier = Modifier,
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }
    }


    Row(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {



        Button(
            onClick = {
                showDropdown = !showDropdown
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth(),
            elevation = null,
            border = null
        ) {



                Text(
                    text = device.getCurrentRoom().value,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.body1
                )


            Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_drop_down_24),
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier.size(20.dp)
                )
        }

        if (device.getMode().value != VacuumMode.CHARGING) {
            DropdownMenu(
                expanded = showDropdown,
                onDismissRequest = { showDropdown = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            device.changeCurrentRoom(option)
                            showDropdown = false
                        }
                    ) {
                        Text(text = option)
                    }
                }
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
    DeviceConfigScreen(device = device)
}
