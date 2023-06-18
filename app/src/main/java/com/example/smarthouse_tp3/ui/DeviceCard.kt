package com.example.smarthouse_tp3.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthouse_tp3.data.network.model.NetworkRoutine

@Composable
fun DeviceCard(
    data: NetworkRoutine
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { },
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier.padding(5.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = data.id ?: "",
                    fontSize = 16.sp
                )
                Text(
                    text = data.name ?: "",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserCardPreview() {
    DeviceCard(
        data = NetworkRoutine()
    )
}
