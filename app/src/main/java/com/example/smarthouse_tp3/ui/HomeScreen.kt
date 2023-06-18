package com.example.smarthouse_tp3.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.R

@Composable
fun HomeScreen(
    viewModel: DeviceViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    Column() {
        Row() {
            Button(
                onClick = {
                    viewModel.fetchDevice("449f988c6f20d610")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.load_devices),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Row() {
            Button(
                onClick = {
                    viewModel.executeAction("449f988c6f20d610", "turnOn", emptyArray<String>())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "turnOn",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Row() {
            Button(
                onClick = {
                    viewModel.executeAction("449f988c6f20d610", "turnOff", emptyArray<String>())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "turnOff",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }



        Row() {
            Text(
                text = uiState.toString(),
                modifier = Modifier.padding(8.dp)
            )
        }

    }


}


@Composable
fun LightScreen(
    viewModel: LightViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    Column() {
        Row() {
            Button(
                onClick = {
                    viewModel.changeColor("FF0000")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "change color",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Row() {
            Button(
                onClick = {
                    viewModel.changeBrightness(100.toLong())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "change brightness",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Row() {
            Text(
                text = uiState.state?.color.toString(),
                modifier = Modifier.padding(8.dp)
            )
        }
        Row() {
            Text(
                text = uiState.state?.brightness.toString(),
                modifier = Modifier.padding(8.dp)
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun LightScreenPreview() {

    val viewModel: LightViewModel = viewModel()
    viewModel.fetchDevice("4d842b03d28e19bc")

    LightScreen(viewModel = viewModel)

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    val viewModel: DeviceViewModel = viewModel()
    val uiState = viewModel.uiState

    HomeScreen(viewModel = viewModel())
}
