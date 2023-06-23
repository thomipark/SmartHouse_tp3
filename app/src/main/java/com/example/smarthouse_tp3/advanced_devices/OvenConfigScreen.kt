package com.example.smarthouse_tp3.advanced_devices

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
            elevation = 4.dp,
            border = BorderStroke(0.5.dp, Color.LightGray),
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(0.8f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        text = "${uiState.state?.temperature?.toString()}°C",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        fontSize = 80.sp,
                    )
                }
                Column(
                    modifier = Modifier.weight(0.2f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = { viewModel.increaseTemperature() },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Increase Temperature",
                            modifier = Modifier.size(36.dp)

                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    IconButton(
                        onClick =
                        { viewModel.decreaseTemperature() },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.minus),
                            contentDescription = "Decrease Temperature",
                            modifier = Modifier.size(48.dp)
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
        val fanMode = OvenFanMode.fromString(uiState.state?.convection.toString())

        val iconGrillIds = listOf(
            R.drawable.grill,
            R.drawable.grill_eco,
            R.drawable.grill_outline
        )
        val grillMode = OvenGrillMode.fromString(uiState.state?.grill.toString())

        Row(
            modifier = Modifier
                .padding(vertical = 18.dp, horizontal = 20.dp)
                .height(120.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.6f),
                horizontalAlignment = Alignment.Start
            ) {
                Card(
                    modifier = Modifier.width(180.dp),
                    border = BorderStroke(0.5.dp, Color.LightGray),
                    elevation = 0.dp
                ) {
                    Text(
                        text = stringResource(id = R.string.convection),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }


            Column(
                modifier = Modifier.weight(0.4f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.size(150.dp),
                    shape = RoundedCornerShape(30),
                    elevation = 2.dp
                ) {

                    IconButton(
                        onClick = {
                            viewModel.iterateFanMode()
                        }
                    ) {
                        if (uiState.state?.convection.toString() == "eco") {
                            Text(
                                text = "ECO",
                                style = MaterialTheme.typography.h6,
                                fontSize = 14.sp,
                                color =
                                if(uiState.switchState)
                                    Color.Green
                                else if(MaterialTheme.colors.isLight)
                                    Color.LightGray
                                else
                                    Color.DarkGray,
                                modifier = Modifier.padding(top = 95.dp, start = 60.dp),
                                )
                        }
                        Icon(
                            painter = painterResource(iconFanIds[fanMode.index]),
                            contentDescription = "Icon",
                            tint =
                            if (fanMode.index == 1 && uiState.switchState)
                                Color.Green
                            else if ((fanMode.index == 2 ||  !uiState.switchState) && MaterialTheme.colors.isLight)
                                Color.LightGray
                            else if ((fanMode.index == 2 ||  !uiState.switchState) && !MaterialTheme.colors.isLight)
                                Color.DarkGray
                            else if(MaterialTheme.colors.isLight)
                                Color.Black
                            else
                                Color.White,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(vertical = 18.dp, horizontal = 20.dp)
                .height(120.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.6f),
                horizontalAlignment = Alignment.Start
            ) {
                Card(
                    modifier = Modifier.width(180.dp),
                    border = BorderStroke(0.5.dp, Color.LightGray),
                    elevation = 0.dp
                ) {
                    Text(
                        text = stringResource(id = R.string.grill_mode),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.weight(0.4f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.size(150.dp),
                    shape = RoundedCornerShape(30),
                    elevation = 2.dp
                ) {
                    IconButton(
                        onClick = {
                            viewModel.iterateGrillMode()
                        }
                    ) {
                        if (uiState.state?.grill.toString() == "eco") {
                            Text(
                                text = "ECO",
                                style = MaterialTheme.typography.h6,
                                fontSize = 14.sp,
                                color =
                                if(uiState.switchState)
                                    Color.Green
                                else if(MaterialTheme.colors.isLight)
                                    Color.LightGray
                                else
                                    Color.DarkGray,
                                modifier = Modifier.padding(top = 98.dp, start = 60.dp),
                            )
                        }

                        Icon(
                            painter = painterResource(iconGrillIds[grillMode.index]),
                            contentDescription = "Icon",
                            tint =
                            if (grillMode.index == 1 && uiState.switchState)
                                Color.Green
                            else if ((grillMode.index == 2 ||  !uiState.switchState) && MaterialTheme.colors.isLight)
                                Color.LightGray
                            else if ((grillMode.index == 2 ||  !uiState.switchState) && !MaterialTheme.colors.isLight)
                                Color.DarkGray
                            else if(MaterialTheme.colors.isLight)
                                Color.Black
                            else
                                Color.White,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            }
        }

        val iconHeatIds = listOf(
            R.drawable.border_bottom_variant,
            R.drawable.border_top_variant,
            R.drawable.border_top_bottom_variant
        )

            Row(
                modifier = Modifier
                    .padding(vertical = 18.dp, horizontal = 20.dp)
                    .height(120.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(0.6f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Card(
                        modifier = Modifier.width(180.dp),
                        border = BorderStroke(0.5.dp, Color.LightGray),
                        elevation = 0.dp
                    ) {
                        Text(
                            text = stringResource(id = R.string.heat_source),
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(0.4f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier.size(150.dp),
                        shape = RoundedCornerShape(30),
                        elevation = 2.dp
                    ) {
                        IconButton(
                            onClick = {
                                viewModel.iterateHeatMode()
                            }
                        ) {
                            val heatMode = OvenHeatMode.fromString(uiState.state?.heat.toString())
                            Icon(
                                painter = painterResource(iconHeatIds[heatMode.index]),
                                contentDescription = "Icon",
                                tint =
                                if (!uiState.switchState && MaterialTheme.colors.isLight)
                                    Color.LightGray
                                else if (!uiState.switchState)
                                    Color.DarkGray
                                else if(MaterialTheme.colors.isLight)
                                    Color.Black
                                else
                                    Color.White,
                                modifier = if (heatMode.index != 2) Modifier.size(100.dp) else Modifier
                                    .size(
                                        101.dp
                                    )
                                    .padding(top = 5.dp, bottom = 4.dp)
                            )
                        }
                    }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OvenConfigScreenPreview() {

    val viewModel: OvenViewModel = viewModel()
    viewModel.fetchDevice("a38ad5a16c0cac8b")

    OvenConfigScreen(viewModel = viewModel)

}

