package com.example.smarthouse_tp3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PlacesScreen(
    modifier: Modifier = Modifier,
    onNavigateToDevicesScreen: () -> Unit,
    onNavigateToRoutinesScreen: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Column() {
            Text(text = "this is Places Screen")
            Button(onClick = { onNavigateToDevicesScreen() }) {
                Text(text = "Go back to devices")
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun PlacesScreenPreview() {
    PlacesScreen(onNavigateToDevicesScreen = {})
}
*/

