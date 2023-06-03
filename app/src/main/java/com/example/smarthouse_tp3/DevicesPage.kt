package com.example.smarthouse_tp3

import android.media.Image
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthouse_tp3.ui.theme.SmartHouse_tp3Theme



@Composable
fun DeviceScreen(
    modifier: Modifier = Modifier,

){
    Column(
        modifier
            .padding(8.dp)
//            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TopBarDevice(Modifier.padding(horizontal = 8.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),     //se meustra el icono perfecto
            modifier = modifier.fillMaxWidth()
        ) {
            items(items = smallTileData) { item ->
                DeviceSmallTile(icon = item.drawable, deviceType = item.text)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarDevice(
    modifier: Modifier = Modifier
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
        title = { Text(text = "Devices") }
    )
}



/**
 * Genera un small tile de device alargado horizontalmente.
 * Se puede apagar y prender y en funcion de eso cambia la imagen o no
 */
@Composable
fun DeviceSmallTile(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes deviceType: Int
){
    var switchOn: Boolean by remember { mutableStateOf(false) } //el by es para no escribir el    .value
    val myIcon: Int

    if (switchOn) {
        myIcon = R.drawable.device_oven_on
    } else {
        myIcon = icon
    }


    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Card (
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray,
            )
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(myIcon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                )
                Text(
                    text = stringResource(deviceType),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(
                        horizontal = 16.dp
                    )
                )
                Switch(
                    checked = switchOn,
                    onCheckedChange = {switchOn = !switchOn},
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Green,
                    )
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
        items(items = smallTileData){ item ->
            DeviceSmallTile(icon = item.drawable, deviceType = item.text)
        }
    }
}



//@Preview (showBackground = false)
@Composable
fun SmallTilePreview(){
    SmartHouse_tp3Theme {
        DeviceSmallTile(
            icon = R.drawable.device_oven,
            deviceType = R.string.device_oven,
            modifier = Modifier.padding(8.dp)
        )
    }
}

//@Preview
@Composable
fun TopBarDevicePreview(){
    TopBarDevice()
}

//@Preview
@Composable
fun DevicesSmallTileRowPreview(){
    DevicesSmallTileRow()
}


@Preview (showBackground = true)
@Composable
fun DeviceScreenPreview () {
    SmartHouse_tp3Theme() {
        DeviceScreen()
    }
}

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

private val smallTileData = listOf(
    DrawableStringPair(R.drawable.device_oven, R.string.device_oven),
    DrawableStringPair(R.drawable.device_oven, R.string.device_oven),
    DrawableStringPair(R.drawable.device_oven, R.string.device_oven),
    DrawableStringPair(R.drawable.device_oven, R.string.device_oven),
    DrawableStringPair(R.drawable.device_oven, R.string.device_oven),
    DrawableStringPair(R.drawable.device_oven, R.string.device_oven),
    DrawableStringPair(R.drawable.device_oven, R.string.device_oven),
    DrawableStringPair(R.drawable.device_oven, R.string.device_oven),
    DrawableStringPair(R.drawable.device_oven, R.string.device_oven),
    DrawableStringPair(R.drawable.device_oven, R.string.device_oven),
    DrawableStringPair(R.drawable.device_oven, R.string.device_oven),
    DrawableStringPair(R.drawable.device_oven, R.string.device_oven)
)