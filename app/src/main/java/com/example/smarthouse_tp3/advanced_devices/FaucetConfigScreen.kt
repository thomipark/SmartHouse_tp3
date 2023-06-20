package com.example.smarthouse_tp3.advanced_devices

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.ui.FaucetUnits
import com.example.smarthouse_tp3.ui.FaucetViewModel

@Composable
fun FaucetConfigScreen(
    viewModel: FaucetViewModel
) {

    val uiState by viewModel.uiState.collectAsState()
    val state = uiState.state
    var sliderValue = rememberSaveable { mutableStateOf(0F) }
    var volumeUnits = FaucetUnits.values().map { it.stringValue }


    if (state != null) {
        if (state.status.toString() == "closed") {
            sliderValue = rememberSaveable { mutableStateOf(0F) }
        } else {
            //sliderValue.value = state.dispensedQuantity!!
        }
    }

    val selectedUnit = remember { mutableStateOf(FaucetUnits.getFaucetUnitValues()[FaucetUnits.KILOLITERS.index]) }
    val formattedValue = if (state?.status.toString() == "opened") {
        (state?.dispensedQuantity ?: 0F).toInt()
    } else {
        (sliderValue.value / 100f).times(100).toInt()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = if (state?.status.toString() == "opened") {
                        "Dispensed: $formattedValue ${state?.unit}"
                    } else {
                        "To Dispense: $formattedValue ${selectedUnit.value}"
                    },
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
                (if (state?.status.toString() == "closed"){
                    sliderValue.value
                } else{
                    uiState.id?.let { viewModel.fetchDevice(it) }
                    state?.dispensedQuantity
                })?.let {
                    Slider(
                        value = it,
                        onValueChange = { newValue ->
                            sliderValue.value = newValue
                        },
                        valueRange = 0f..100f,
                        modifier = Modifier.width(300.dp)
                    )
                }
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
                modifier = Modifier
                    .clickable { isDropdownExpanded.value = true }
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
                onClick = {
                          viewModel.dispense(sliderValue.value,selectedUnit.value)
                },
                modifier = Modifier.size(width = 200.dp, height = 48.dp)
            ) {
                Text(
                    text = "Dispense",
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
            Box(
                modifier = Modifier
                    .size(width = 200.dp, height = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { viewModel.close() },
                    modifier = Modifier.fillMaxSize(),
                    enabled = state?.status.toString() != "closed",
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (state?.status.toString() == "closed") Color.Gray else Color.White
                    )
                ) {
                    Text(
                        text = "Stop",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FaucetConfigScreenPreview() {

    val viewModel: FaucetViewModel = viewModel()
    viewModel.fetchDevice("1f39e8a19e7da6ba")

    FaucetConfigScreen (viewModel = viewModel)

}
