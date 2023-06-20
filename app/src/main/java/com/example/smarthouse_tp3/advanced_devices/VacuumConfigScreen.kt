package com.example.smarthouse_tp3.advanced_devices

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.ui.VacuumMode
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.ui.RoomsViewModel
import com.example.smarthouse_tp3.ui.VacuumViewModel

@Composable
fun VacuumConfigScreen(
    viewModel: VacuumViewModel
) {

    var showDropdown by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    val roomViewModel: RoomsViewModel = viewModel()

    val roomUiState by roomViewModel.uiState.collectAsState()

    var mode = VacuumMode.fromString(uiState.state?.mode.toString())
    var currentMode by remember { mutableStateOf(uiState.state?.mode) }

    val currentRoom by remember { mutableStateOf(uiState.room?.name) }


    var mopColor : Color by remember { mutableStateOf(Color.White)}
    var vacColor : Color by remember { mutableStateOf(Color.White)}

    var dockColor : Color by remember { mutableStateOf(Color.White)}

    if (uiState.state?.status == "docked") {
        dockColor = Color.LightGray
    }
    else {
        dockColor = Color.White
    }
    if (uiState.state != null) {
        if (uiState.state!!.mode.toString() == VacuumMode.MOP.stringValue) {
            mopColor = Color.LightGray
            vacColor = Color.White
        } else if (currentMode.toString() == VacuumMode.VACUUM.stringValue) {
            vacColor = Color.LightGray
            mopColor = Color.White
        }
    }

    val batteryLevel: Long? = uiState.state!!.batteryLevel
    val batteryIcon: Int = viewModel.getBatteryIcon()


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
                Image(
                    painter = painterResource(batteryIcon),
                    contentDescription = "Icon",
                    modifier = Modifier.size(80.dp)
                )
                Column(
                    modifier = Modifier.weight(0.8f),

                    ) {
                    Text(
                        text = "${uiState.state!!.batteryLevel}%",
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
                            viewModel.changeModeVacuum()
                            vacColor = Color.LightGray
                            mopColor = Color.White
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
                            viewModel.changeModeMop()
                            vacColor =  Color.White
                            mopColor =  Color.LightGray
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
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
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
                            if (uiState.state!!.status != "docked") {
                                viewModel.dock()
                                dockColor = Color.LightGray
                            }
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
                            text = stringResource(R.string.return_to_dock),
                            modifier = Modifier,
                            style = MaterialTheme.typography.body1,
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    showDropdown = !showDropdown
                    roomViewModel.fetchRooms()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                ),
                modifier = Modifier.fillMaxWidth(),
                elevation = null,
                border = null
            ) {



                currentRoom?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.body1
                    )
                }


                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_drop_down_24),
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier.size(20.dp)
                )
            }

            DropdownMenu(
                expanded = showDropdown,
                onDismissRequest = { showDropdown = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                roomUiState.rooms?.rooms?.let { rooms ->
                    rooms.map { it.name }.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.setLocation(option.toString())
                                showDropdown = false
                            }
                        ) {
                            Text(text = option.toString())
                        }
                    }
                }
            }

        }
        Log.d("MyDe:", uiState.toString())
    }
}


@Preview(showBackground = true)
@Composable
fun VacuumConfigScreenPreview() {

    // val viewModel: VacuumViewModel = viewModel()
    // viewModel.fetchDevice("985376562da43a64")

    // VacuumConfigScreen(viewModel = viewModel)

}
