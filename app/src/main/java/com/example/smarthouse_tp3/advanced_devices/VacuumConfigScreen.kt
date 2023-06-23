package com.example.smarthouse_tp3.advanced_devices

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.ui.RoomsViewModel
import com.example.smarthouse_tp3.ui.VacuumMode
import com.example.smarthouse_tp3.ui.VacuumViewModel

@Composable
fun VacuumConfigScreen(
    viewModel: VacuumViewModel
) {

    var showDropdown by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val roomViewModel: RoomsViewModel = viewModel()
    val roomUiState by roomViewModel.uiState.collectAsState()
    val currentMode by remember { mutableStateOf(uiState.state?.mode) }
    var currentRoom by remember { mutableStateOf(uiState.room?.name) }

    val colorTheme = MaterialTheme.colors.isLight

    var mopColor: Color by remember { mutableStateOf(if (colorTheme) Color.White else Color.Black) }
    var vacColor: Color by remember { mutableStateOf(if (colorTheme) Color.White else Color.Black) }
    var dockColor: Color by remember { mutableStateOf(if (colorTheme) Color.White else Color.Black) }

    if (uiState.state?.status == "docked") {
        dockColor = Color.Gray
    } else {
        dockColor = if (colorTheme) Color.White else Color.Black
    }
    if (uiState.state != null) {
        if (uiState.state!!.mode.toString() == VacuumMode.MOP.stringValue) {
            mopColor = Color.Gray
            vacColor = if (colorTheme) Color.White else Color.Black

        } else if (currentMode.toString() == VacuumMode.VACUUM.stringValue) {
            vacColor = Color.Gray
            mopColor = if (colorTheme) Color.White else Color.Black
        }
    }

    uiState.state!!.batteryLevel
    val batteryIcon: Int = viewModel.getBatteryIcon()

    if (uiState.room?.id != null) {

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
                    Icon(
                        painter = painterResource(batteryIcon),
                        contentDescription = "Icon",
                        modifier = Modifier.size(80.dp),
                        tint = if (colorTheme) Color.Black else Color.White
                    )
                    Column(
                        modifier = Modifier.weight(0.8f),

                        ) {
                        Text(
                            text = "${uiState.state!!.batteryLevel}%",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(start = 30.dp, bottom = 8.dp),
                            fontSize = 80.sp,
                            color = if (colorTheme) Color.Black else Color.White
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
                                vacColor = Color.Gray
                                mopColor = if (colorTheme) Color.White else Color.Black
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.vacuum_outline),
                                contentDescription = "Icon",
                                modifier = Modifier.size(100.dp),
                                tint = if (colorTheme) Color.Black else Color.White
                            )
                        }

                    }
                    Text(
                        text = stringResource(id = R.string.vacuum),
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
                                vacColor = if (colorTheme) Color.White else Color.Black
                                mopColor = Color.Gray
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_water_drop_24),
                                contentDescription = "Icon",
                                modifier = Modifier.size(100.dp),
                                tint = if (colorTheme) Color.Black else Color.White
                            )
                        }
                    }
                    Text(
                        text = stringResource(R.string.mop),
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
                            .fillMaxWidth(),
                        border = BorderStroke(1.dp, if (colorTheme) Color.Black else Color.White),
                    ) {

                        Button(
                            onClick = {
                                if (uiState.state!!.status != "docked") {
                                    viewModel.dock()
                                    dockColor = Color.Gray
                                    currentRoom = uiState.room?.name
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = dockColor,
                            ),
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_electric_bolt_24),
                                contentDescription = "Button Icon",
                                modifier = Modifier.size(25.dp),
                                tint = if (colorTheme) Color.Black else Color.White
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
                        border = BorderStroke(0.5.dp, if (colorTheme) Color.Black else Color.White),
                        elevation = 0.dp
                    ) {
                        Text(
                            text = stringResource(id = R.string.current_room),
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
                        shape = RoundedCornerShape(20),
                        backgroundColor = MaterialTheme.colors.primaryVariant,
                        border = BorderStroke(0.5.dp, if (colorTheme) Color.Black else Color.White),

                        ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    showDropdown = !showDropdown
                                    roomViewModel.fetchRooms()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.primaryVariant
                                ),
                                modifier = Modifier
                                    .clickable { showDropdown = true }
                                    .size(width = 170.dp, height = 100.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .width(170.dp)
                                        .padding(8.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row {
                                        currentRoom?.let {
                                            Text(
                                                text = it,
                                                modifier = Modifier.padding(4.dp),
                                                style = MaterialTheme.typography.body1,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }

                            DropdownMenu(
                                expanded = showDropdown,
                                onDismissRequest = { showDropdown = false },
                                offset = DpOffset((0).dp, (-25).dp),
                                modifier = Modifier
                                    .width(170.dp)
                                    .background(MaterialTheme.colors.primaryVariant)
                            ) {
                                roomUiState.rooms?.rooms?.let { rooms ->
                                    rooms.map { it }.forEach { option ->
                                        DropdownMenuItem(
                                            onClick = {
                                                option.let { viewModel.setLocation(it) }
                                                currentRoom = option.name
                                                showDropdown = false
                                                if (dockColor == Color.Gray) {
                                                    viewModel.pause()
                                                    dockColor =
                                                        if (colorTheme) Color.White else Color.Black
                                                }
                                            }
                                        ) {
                                            Text(
                                                text = option.name.toString(),
                                                style = MaterialTheme.typography.body1,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,
                                                textAlign = TextAlign.Center,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    } else {

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.vacuum_not_associated_with_room),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }


    }
}


@Preview(showBackground = true)
@Composable
fun VacuumConfigScreenPreview() {

    // val viewModel: VacuumViewModel = viewModel()
    // viewModel.fetchDevice("985376562da43a64")

    // VacuumConfigScreen(viewModel = viewModel)

}
