package com.example.smarthouse_tp3.advanced_devices


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.ui.AirConditionerFanSpeed
import com.example.smarthouse_tp3.ui.AirConditionerMode
import com.example.smarthouse_tp3.ui.AirConditionerViewModel


@Composable
fun AirConditionerConfigScreen(viewModel: AirConditionerViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    Column() {

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
                        text = "${uiState.state?.temperature}°C",
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
            R.drawable.fan_auto,
            R.drawable.fan,
            R.drawable.fan_speed_1,
            R.drawable.fan_speed_2,
            R.drawable.fan_speed_3
        )


        val iconModeIds = listOf(
            R.drawable.weather_sunny,
            R.drawable.snowflake,
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
                        viewModel.iterateMode()
                    }
                ) {

                    val mode = uiState.state?.mode?.let { AirConditionerMode.fromString(it) }
                    if (mode != null) {
                        Icon(
                            painter = painterResource(iconModeIds[mode.index]),
                            contentDescription = "Icon",
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
                Text(
                    text = stringResource(R.string.mode),
                    style = MaterialTheme.typography.body1
                )
            }


            val fanSpeed =
                AirConditionerFanSpeed.fromString(uiState.state?.fanSpeed.toString())
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        viewModel.iterateFanSpeed()
                    }
                ) {

                    Icon(
                        painter = painterResource(iconFanIds[fanSpeed.index]),
                        contentDescription = "Icon",
                        modifier = Modifier.size(100.dp)
                    )
                }
                Text(
                    text = stringResource(R.string.fan_speed_text, fanSpeed.stringValue),
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
                        viewModel.increaseVerticalFanDirection()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_drop_up_24),
                        contentDescription = "Icon",
                        modifier = Modifier.size(100.dp)
                    )
                }

                Text(
                    text = if (uiState.state?.verticalSwing.toString() != "auto") uiState.state?.verticalSwing.toString() + "°" else "auto",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = {
                        viewModel.decreaseVerticalFanDirection()
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
                            viewModel.decreaseHorizontalFanDirection()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_left_24),
                            contentDescription = "Icon",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(end = 12.dp)
                        )
                    }



                    IconButton(
                        onClick = {
                            viewModel.increaseHorizontalFanDirection()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_right_24),
                            contentDescription = "Icon",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(start = 12.dp)

                        )
                    }
                }
                Text(
                    text = if (uiState.state?.horizontalSwing.toString() != "auto") uiState.state?.horizontalSwing.toString() + "°" else "auto",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.offset(y = (-70).dp)
                )

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AirConditionerScreenPreview() {

    val viewModel: AirConditionerViewModel = viewModel()
    viewModel.fetchDevice("d495cc0b87d1e918")


    AirConditionerConfigScreen(viewModel = viewModel)

}
