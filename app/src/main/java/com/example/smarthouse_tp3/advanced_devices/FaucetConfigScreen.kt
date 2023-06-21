package com.example.smarthouse_tp3.advanced_devices

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthouse_tp3.ui.FaucetUnits
import com.example.smarthouse_tp3.ui.FaucetViewModel

@Composable
fun FaucetConfigScreen(
    viewModel: FaucetViewModel
) {

    val uiState by viewModel.uiState.collectAsState()
    val state = uiState.state
    var sliderValue = rememberSaveable { mutableStateOf(0F) }
    val volumeUnits = FaucetUnits.values().map { it.stringValue }
    val volumeUnitsExtended = FaucetUnits.values().associate { it ->
        it.stringValue to it.name.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
    val isDispensing = remember { mutableStateOf(false) }
    val isDropdownExpanded = remember { mutableStateOf(false) }

    if (state != null) {
        if (state.status.toString() == "closed") {
            sliderValue = rememberSaveable { mutableStateOf(0F) }
            isDispensing.value = false
        }
    }

    val selectedUnit =
        remember { mutableStateOf(FaucetUnits.getFaucetUnitValues()[FaucetUnits.LITERS.index]) }
    val formattedValue =
        if (state?.status.toString() == "opened") {
            (state?.dispensedQuantity ?: 0F).toInt()
        } else {
            (sliderValue.value / 100f).times(100).toInt()
        }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        if (state?.status.toString() == "closed") {
            Row(
                modifier = Modifier
                    .padding(top = 45.dp, start = 20.dp, end = 20.dp)
                    .height(120.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Card(
                        modifier = Modifier.width(160.dp),
                        border = BorderStroke(0.5.dp, Color.LightGray),
                        elevation = 0.dp
                    ) {
                        Text(
                            text = "Unit",
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .width(170.dp)
                            .height(100.dp),
                        shape = RoundedCornerShape(30),
                        elevation = 0.dp,
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Button(
                                onClick = { isDropdownExpanded.value = true },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.primaryVariant
                                ),
                                elevation = ButtonDefaults.elevation(5.dp),
                                modifier = Modifier
                                    .clickable { isDropdownExpanded.value = true }
                                    .size(width = 170.dp, height = 100.dp)
                            ) {
                                Text(
                                    text = "${volumeUnitsExtended[selectedUnit.value]}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                            DropdownMenu(
                                expanded = isDropdownExpanded.value,
                                onDismissRequest = { isDropdownExpanded.value = false },
                                offset = DpOffset(0.dp, (-24).dp),
                                modifier = Modifier
                                    .width(170.dp)
                                    .background(MaterialTheme.colors.primaryVariant)
                            ) {
                                volumeUnits.forEach { unit ->
                                    DropdownMenuItem(onClick = {
                                        selectedUnit.value = unit
                                        isDropdownExpanded.value = false
                                    }) {
                                        Text(
                                            text = "${volumeUnitsExtended[unit]} ($unit)",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center,
                                            color = if (selectedUnit.value == unit && MaterialTheme.colors.isLight) Color.Black else if (selectedUnit.value == unit && !MaterialTheme.colors.isLight) Color.White else if (MaterialTheme.colors.isLight) Color.White else Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 45.dp, start = 20.dp, end = 20.dp)
                    .height(120.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Card(
                        modifier = Modifier.width(160.dp),
                        border = BorderStroke(0.5.dp, Color.LightGray),
                        elevation = 0.dp
                    ) {
                        Text(
                            text = "Volume",
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)

                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    var textFieldValue by remember { mutableStateOf("") }
                    var isEnabled by remember { mutableStateOf(true) }

                    Card(
                        modifier = Modifier
                            .width(170.dp)
                            .height(70.dp),
                        shape = RoundedCornerShape(20),
                        border = BorderStroke(0.1.dp, Color.LightGray),
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                        ) {
                            TextField(
                                value = textFieldValue,
                                onValueChange = { newValue: String ->
                                    textFieldValue = newValue
                                    var inputValue = textFieldValue.toIntOrNull()

                                    if (inputValue != null && inputValue > 100) {
                                        inputValue = 100
                                    }

                                    if (inputValue == null || inputValue < 0) {
                                        inputValue = 0
                                    }
                                    sliderValue.value = inputValue.toString().toFloat()
                                },
                                textStyle = TextStyle(textAlign = TextAlign.Center),
                                modifier = Modifier.fillMaxSize(),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                enabled = isEnabled,
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        isEnabled = false
                                        textFieldValue = ""
                                    }
                                ),
                            )
                            isEnabled = true
                            Box(
                                modifier = Modifier
                                    .size(170.dp, 70.dp)
                                    .background(MaterialTheme.colors.primaryVariant)
                            )

                            Text(
                                text = "$formattedValue ${selectedUnit.value}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 35.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }


        //uiState.id?.let { viewModel.fetchDevice(it) }
        if (state?.status.toString() == "closed" || (state?.status.toString() == "opened" && isDispensing.value)) {
            if (state?.status.toString() == "opened") {
                Row(
                    modifier = Modifier
                        .padding(top = 45.dp, start = 20.dp, end = 20.dp)
                        .height(120.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.6f)
                            .padding(end = 12.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Card(
                            modifier = Modifier.width(190.dp),
                            border = BorderStroke(0.5.dp, Color.LightGray),
                            elevation = 0.dp
                        ) {
                            Text(
                                text = "Dispensed volume",
                                style = MaterialTheme.typography.h5,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.weight(0.4f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .width(150.dp)
                                .height(100.dp),
                            shape = RoundedCornerShape(10),
                            elevation = 1.dp,
                            border = BorderStroke(0.1.dp, Color.LightGray),
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                            ) {
                                Text(
                                    text = "$formattedValue ${selectedUnit.value}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 35.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }


            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val aux = sliderValue.value
                    //uiState.id?.let { viewModel.fetchDevice(it) }
                    (if (state?.status.toString() == "closed") {
                        sliderValue.value
                    } else {
                        uiState.id?.let { viewModel.fetchDevice(it) }
                        state?.dispensedQuantity
                    })?.let {
                        Box(
                            modifier = Modifier.width(270.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(40.dp)
                                    .offset(x = (-46).dp, y = 8.dp)
                            ) {
                                Text(
                                    text = "0 ${selectedUnit.value}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Slider(
                                value = it,
                                onValueChange = { newValue ->
                                    sliderValue.value = newValue
                                },
                                colors = SliderDefaults.colors(
                                    thumbColor = MaterialTheme.colors.primary,
                                    activeTrackColor = MaterialTheme.colors.primary,
                                    inactiveTrackColor = MaterialTheme.colors.primaryVariant
                                ),
                                valueRange = 0f..100f
                            )
                            Box(
                                modifier = Modifier
                                    .width(70.dp)
                                    .offset(x = (270).dp, y = 8.dp)
                            ) {
                                Text(
                                    text = "100 ${selectedUnit.value}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    if (state?.status.toString() != "closed" && isDispensing.value && aux != 0f && aux == state?.dispensedQuantity) {
                        viewModel.changeSwitchState()
                        isDispensing.value = false
                    }
                }
            }
        }

        if (state?.status.toString() == "opened" && !isDispensing.value) {
            Row(
                modifier = Modifier.padding(top = 100.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${uiState.name} is open and running",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        if (state?.status.toString() == "closed") {
            Row(
                modifier = Modifier.padding(vertical = 100.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 200.dp, height = 70.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            enabled = formattedValue != 0,
                            onClick = {
                                viewModel.dispense(
                                    sliderValue.value,
                                    selectedUnit.value
                                )
                                isDispensing.value = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primaryVariant,
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.elevation(5.dp),
                            modifier = Modifier.size(width = 250.dp, height = 100.dp)
                        ) {
                            Text(
                                text = "Dispense",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 30.sp,
                                textAlign = TextAlign.Center,
                                color = if(MaterialTheme.colors.isLight) Color.White else Color.Black,
                            )
                        }
                    }
                }
            }
        }

        if (state?.status.toString() != "closed") {
            Row(
                modifier = Modifier.padding(vertical = 100.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 200.dp, height = 70.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = { viewModel.changeSwitchState(); isDispensing.value = false},
                            modifier = Modifier.fillMaxSize(),
                            enabled = state?.status.toString() != "closed",
                            elevation = ButtonDefaults.elevation(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primaryVariant
                            )
                        ) {
                            Text(
                                text = "Close faucet",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun FaucetConfigScreenPreview() {

    val viewModel: FaucetViewModel = viewModel()
    viewModel.fetchDevice("1f39e8a19e7da6ba")

    FaucetConfigScreen(viewModel = viewModel)

}
*/