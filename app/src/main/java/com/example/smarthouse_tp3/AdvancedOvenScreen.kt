package com.example.smarthouse_tp3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


/**
 * Esta funcion deberia recibir como parametro un device de tipo oven
 */
@Composable
fun AdvancedOvenScreen(
    modifier: Modifier = Modifier,
){
    Box(
        modifier = Modifier.fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(text = "this is Advanced Oven Screen")
    }
}


//@Preview (showBackground = true)
//@Composable
//fun AdvancedOvenScreenPreview(){
//    RoutinesScreen()
//}