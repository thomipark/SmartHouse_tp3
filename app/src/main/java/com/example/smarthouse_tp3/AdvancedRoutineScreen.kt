package com.example.smarthouse_tp3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValue() -> Booleans
iAnymport androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoutineConfigScreen(routine: Routine) {
    Scaffold(
        topBar = {
            RoutineTopBar(routine)
        },
        content = {
            it
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                RoutineBody(routine)
            }
        }
    )
}

@Composable
fun RoutineTopBar(routine: Routine) {
    if (routine.getRoutineDevices().isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Devices & Actions",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
            Divider(
                color = if(MaterialTheme.colors.surface == Color.Black) Color.White else Color.Black,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
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
            backgroundColor = MaterialTheme.colors.primaryVariant
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
        if (routine.getRoutineDevices().isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 325.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No devices linked to routine",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        } else {
            items(items = routine.getRoutineDevices()) { item ->
                AdvancedRoutineDeviceTile(item)
            }
        }
    }
    if (routine.getRoutineDevices().isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = { routine.togglePlay() },
                modifier = Modifier
                    .size(128.dp)
                    .padding(4.dp),
                backgroundColor = Color.White
            ) {
                val playIconSize = 128.dp // Adjust the size of the icon
                val playIcon =
                    painterResource(if (routine.isPlaying()) R.drawable.screen_routines_icon else R.drawable.screen_routines_icon)
                val playDescription = if (routine.isPlaying()) "Pause" else "Play"
                val playTint = if (routine.isPlaying()) MaterialTheme.colors.secondary else Color.Black

                Icon(
                    painter = playIcon,
                    contentDescription = playDescription,
                    tint = playTint,
                    modifier = Modifier.size(playIconSize)
                )
            }
        }
    }
}

/*
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

val routine1 = Routine("Afternoon Routine",listOf(routineDevice1, routineDevice2))
*/