package com.example.smarthouse_tp3.com.example.smarthouse_tp3


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
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.ui.theme.SmartHouse_tp3Theme

/***
 * Pantalla dedicada a Routines.
 */
@Composable
fun RoutinesScreen(
    modifier: Modifier = Modifier,
    //onNavigateToDevicesScreen: () -> Unit,
    //onNavigateToPlacesScreen: () -> Unit
){
    Column(
        modifier
            .padding(8.dp)
    ) {
        RoutinesSmallTileRow()
        //Button(onClick = { onNavigateToDevicesScreen() }) {
        //    Text(text = "Go back to devices")
        //}
    }
}

/**
 * Genera un small tile de routine horizontal.
 */
@Composable
fun RoutineSmallTile(
    modifier: Modifier = Modifier,
    routine: Routine
){
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Card (
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = Color.LightGray
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(0.5f),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxSize()
                            .weight(0.45f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = routine.getName(),
                            style = MaterialTheme.typography.h6,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis, // Display the text in a single line
                        )
                    }
                }
                IconButton(
                    onClick = { routine.togglePlay() },
                    modifier = Modifier
                        .size(512.dp)
                        .weight(0.3f)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 8.dp)
                ) {
                    val playIcon = painterResource(if (routine.isPlaying()) R.drawable.screen_routines_icon else R.drawable.screen_routines_icon)
                    val playDescription = if (routine.isPlaying()) "Pause" else "Play"
                    val playTint = if (routine.isPlaying()) Color.Green else Color.Black

                    Icon(
                        painter = playIcon,
                        contentDescription = playDescription,
                        tint = playTint
                    )
                }
            }
        }
    }
}



@Composable
fun RoutinesSmallTileRow (
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(items = smallRoutinesTileData) { item ->
            RoutineSmallTile(routine = item)
        }
    }
}


//-------- A PARTIR DE ACA ESTAN LAS PREVIEW ------------------

@Preview (showBackground = false)
@Composable
fun SmallRoutineTilePreview(){
    SmartHouse_tp3Theme() {
        RoutineSmallTile(
            routine = Routine("Morning Routine"),
            modifier = Modifier.padding(8.dp)
        )
    }
}



@Preview
@Composable
fun SmallRoutineTileRowPreview(){
    RoutinesSmallTileRow()
}

val smallRoutinesTileData = listOf<Routine>(
    Routine("Afternoon Routine"),
    Routine("Bedtime Routine"),
)