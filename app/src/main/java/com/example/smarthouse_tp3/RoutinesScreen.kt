package com.example.smarthouse_tp3.com.example.smarthouse_tp3


import android.content.res.Configuration
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
    onNavigateToDevicesScreen: () -> Unit,
    onNavigateToPlacesScreen: () -> Unit
){
    Column(
        modifier
            .padding(8.dp)
    ) {
        SmallRoutineTilesRow()
        //Button(onClick = { onNavigateToDevicesScreen() }) {
        //    Text(text = "Go back to devices")
        //}
    }
}

/**
 * Genera un small tile de routine horizontal.
 */
@Composable
fun SmallRoutineTile(
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
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
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
                            text = routine.getRoutineName(),
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
                        .padding(horizontal = 20.dp)
                ) {
                    val playIconSize = 40.dp // Adjust the size of the icon
                    val playIcon = painterResource(R.drawable.screen_routines_icon)
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
        }
    }
}


@Composable
fun SmallRoutineTilesRow (
    modifier: Modifier = Modifier
) {
    val isHorizontal = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isHorizontal) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            items(items = smallRoutinesTileData) { item ->
                SmallRoutineTile(routine = item)
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            items(items = smallRoutinesTileData) { item ->
                SmallRoutineTile(routine = item)
            }
        }
    }
}


//-------- A PARTIR DE ACA ESTAN LAS PREVIEW ------------------

@Preview (showBackground = false)
@Composable
fun SmallRoutineTilePreview(){
    SmartHouse_tp3Theme() {
        SmallRoutineTile(
            routine = Routine("Morning Routine"),
            modifier = Modifier.padding(8.dp)
        )
    }
}



@Preview
@Composable
fun SmallRoutineTileRowPreview(){
    SmallRoutineTilesRow()
}

val smallRoutinesTileData = listOf(
    Routine("Afternoon Routine"),
    Routine("Bedtime Routine"),
)