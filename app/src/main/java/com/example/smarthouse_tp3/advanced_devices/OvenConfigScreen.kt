package com.example.smarthouse_tp3.advanced_devices

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.DeviceOven
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.ui.OvenFanMode
import com.example.smarthouse_tp3.ui.OvenGrillMode
import com.example.smarthouse_tp3.ui.OvenHeatMode
import com.example.smarthouse_tp3.ui.OvenViewModel

@Composable
fun OvenConfigScreen(
    viewModel: OvenViewModel
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
                        text = "${uiState.state?.temperature?.toString()}°C",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(start = 30.dp, bottom = 8.dp),
                        fontSize = 80.sp,
                    )
                }
                Column(
                    modifier = Modifier.weight(0.2f)
                ) {
                    IconButton(
                        onClick = { viewModel.increaseTemperature() },
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
                        { viewModel.decreaseTemperature() },
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


        val iconFanIds = listOf(
            R.drawable.fan,
            R.drawable.fan_eco,
            R.drawable.fan_off
        )

        val iconGrillIds = listOf(
            R.drawable.grill,
            R.drawable.grill_eco,
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
                        viewModel.iterateFanMode()
                    }
                ) {
                    val fanMode = OvenFanMode.fromString(uiState.state?.convection.toString())
                    Icon(
                        painter = painterResource(iconFanIds[fanMode.index]),
                        contentDescription = "Icon",
                        modifier = Modifier.size(100.dp)
                    )
                }
                Text(
                    text = "Fan: ${uiState.state?.convection.toString()}",
                    style = MaterialTheme.typography.body1
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        viewModel.iterateGrillMode()
                    }
                ) {
                    val GrillMode = OvenGrillMode.fromString(uiState.state?.grill.toString())
                    Icon(
                        painter = painterResource(iconGrillIds[GrillMode.index]),
                        contentDescription = "Icon",
                        modifier = Modifier.size(100.dp)
                    )
                }
                Text(
                    text = "Grill: ${uiState.state?.grill.toString()}",
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
                        viewModel.iterateHeatMode()
                    }
                ) {
                    val HeatMode = OvenHeatMode.fromString(uiState.state?.heat.toString())
                    Icon(
                        painter = painterResource(iconHeatIds[HeatMode.index]),
                        contentDescription = "Icon",
                        modifier = Modifier.size(100.dp)
                    )
                }
                Text(
                    text = "Heat: ${uiState.state?.heat.toString()}",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OvenConfigScreenPreview() {

    val viewModel: OvenViewModel = viewModel()
    viewModel.fetchDevice("a38ad5a16c0cac8b")

    OvenConfigScreen (viewModel = viewModel)

}

