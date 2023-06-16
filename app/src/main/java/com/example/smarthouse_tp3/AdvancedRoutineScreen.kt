package com.example.smarthouse_tp3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthouse_tp3.com.example.smarthouse_tp3.Action
import com.example.smarthouse_tp3.com.example.smarthouse_tp3.Routine
import com.example.smarthouse_tp3.com.example.smarthouse_tp3.RoutineDevice

@Composable
fun RoutineConfigScreen(routine: Routine) {
    Scaffold(
        topBar = {
            RoutineTopBar(routine)
        },
        content = {it
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ){
                RoutineBody(routine)
            }
        }
    )
}

@Composable
fun RoutineTopBar(routine: Routine) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = routine.getRoutineName(),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { routine.togglePlay() },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {
                val playIconSize = 40.dp // Adjust the size of the icon
                val playIcon = painterResource(if (routine.isPlaying()) R.drawable.screen_routines_icon else R.drawable.screen_routines_icon)
                val playDescription = if (routine.isPlaying()) "Pause" else "Play"
                val playTint = if (routine.isPlaying()) Color(0xFF008000) else Color.Black

                Icon(
                    painter = playIcon,
                    contentDescription = playDescription,
                    tint = playTint,
                    modifier = Modifier.size(playIconSize)
                )
            }
        }
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun AdvancedRoutineDeviceTile(
    routineDevice: RoutineDevice,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 4.dp,
            backgroundColor = Color.LightGray
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = routineDevice.getDeviceName(),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (routineDevice.getActions().isNotEmpty()) {
                    for (action in routineDevice.getActions()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color.Black)
                            )

                            Text(
                                text = action.getActionName(),
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun RoutineBody(
    routine: Routine,
    modifier: Modifier = Modifier
    ) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(items = routine.getRoutineDevices()) { item ->
            AdvancedRoutineDeviceTile(item)
        }
    }
}

@Preview
@Composable
fun AdvancedRoutinePreview() {
    RoutineConfigScreen(routine1)
}

val routineDevice1Action1 = Action("turn on")
val routineDevice1Action2 = Action("set temperature to 220 C")
val routineDevice1 = RoutineDevice("Oven", listOf(routineDevice1Action1, routineDevice1Action2))

val routineDevice2Action1 = Action("turn on")
val routineDevice2Action2 = Action("set color to RED")
val routineDevice2 = RoutineDevice("Light", listOf(routineDevice2Action1, routineDevice2Action2))

val routine1 = Routine("Afternoon Routine", listOf(routineDevice1, routineDevice2))

