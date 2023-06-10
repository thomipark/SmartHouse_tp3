package com.example.smarthouse_tp3


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthouse_tp3.ui.theme.SmartHouse_tp3Theme


/***
 * Pantalla dedicada a Devices.
 */
@Composable
fun DeviceScreen(
    modifier: Modifier = Modifier,
    onNavigateToRoutinesScreen: () -> Unit,
    onNavigateToPlacesScreen: () -> Unit
){
    Column(
        modifier
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TopBarDevice(
            modifier = Modifier.padding(horizontal = 8.dp),
        )
        Button(onClick = { onNavigateToPlacesScreen() }) {
            Text(text = "Go to Places")
        }
        Button(onClick = { onNavigateToRoutinesScreen() }) {
            Text(text = "Go to Routines")
        }
        //        DevicesSmallTileRow()
    }
}


/***
 * TopBar que va volar despues, pq la topbar debertia estar en el navHost o en el main,
 * pero al hacer click en la felchita se va a routines
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarDevice(
    modifier: Modifier = Modifier,
){
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Ir hacia arriba"
                )
            }
        },
        title = { Text(text = stringResource(id = R.string.device_screen)) }
    )
}



/**
 * Genera un small tile de device alargado horizontalmente.
 * Se puede apagar y prender y en funcion de eso cambia la imagen o no
 */
@Composable
fun DeviceSmallTile(
    modifier: Modifier = Modifier,
    device: Device
){
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Card (
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray
            )
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Image(
                    painter = painterResource(device.getIcon()),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(0.3f) // 40% of the available width
                        .padding(horizontal = 8.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .weight(0.5f),
                ) {
                    Text(
                        text = device.getName(),
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,// Display the text in a single line
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .weight(0.7f)
                            .align(Alignment.CenterHorizontally)
                            .wrapContentHeight()
                    )
                    Row(
                        modifier = Modifier
                            .weight(0.3f)
                    ) {
                        if (device.getSwitchState()){
                            Image(
                                painter = painterResource(device.getIcon()),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                            )
                            Image(
                                painter = painterResource(device.getIcon()),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                            )
                        }
                    }
                }

                Switch(
                    checked = device.getSwitchState(),
                    onCheckedChange = {device.changeSwitchState()},
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Green,
                    ),
                    modifier = Modifier
                        .weight(0.2f) // 30% of the available width
                        .fillMaxWidth()
                        .padding(8.dp)
                )

            }

        }
    }
}

@Composable
fun DevicesSmallTileRow (
    modifier: Modifier = Modifier
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),     //se meustra el icono perfecto
        modifier = modifier.fillMaxWidth()
    ) {
        items(items = smallTileData) { item ->
            DeviceSmallTile(device = item)
        }
    }
}

//-------- A PARTIR DE ACA ESTAN LAS PREVIEW ------------------

//@Preview (showBackground = false)
@Composable
fun SmallTilePreview(){
    SmartHouse_tp3Theme {
        DeviceSmallTile(
            device = DeviceOven("thomi Oven"),
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
fun TopBarDevicePreview(){
    TopBarDevice()
}

//@Preview
@Composable
fun DevicesSmallTileRowPreview(){
    DevicesSmallTileRow()
}


//@Preview (showBackground = true)
@Composable
fun DeviceScreenPreview () {
    SmartHouse_tp3Theme() {
    }
}

val smallTileData = listOf<Device>(
    DeviceOven("thomi light"),
    DeviceOven("pepe oven"),
    DeviceOven("marcelo gallardo al horno"),
    DeviceOven("martin oven"),
    DeviceOven("samuel umtiti light"),
    DeviceOven("martin oven"),
    DeviceOven("mbappe light"),
    DeviceOven("martin oven"),
    DeviceOven("federico light"),
)