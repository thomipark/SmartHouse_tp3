package com.example.smarthouse_tp3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RoutinesScreen(
    modifier: Modifier = Modifier,
    onNavigateToDevicesScreen: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Column() {
            Text(text = "this is Routines Screen")
            Button(onClick = { onNavigateToDevicesScreen() }) {
                Text(text = "Go back to devices")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoutinesScreenPreview() {
    RoutinesScreen(onNavigateToDevicesScreen = {})
}


