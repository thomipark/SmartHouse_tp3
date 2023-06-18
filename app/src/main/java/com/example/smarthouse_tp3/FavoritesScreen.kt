package com.example.smarthouse_tp3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthouse_tp3.ui.theme.SmartHouse_tp3Theme


/***
     * Pantalla dedicada a Favoritess.
     */
    @Composable
    fun FavoritesScreen(
    modifier: Modifier = Modifier,
    onNavigateToConfigScreen: (Type) -> Unit
){
        Column(
            modifier
                .padding(8.dp)
        ) {
            FavoritesSmallTileRow(onNavigateToConfigScreen = onNavigateToConfigScreen)
        }
    }





    /**
     * Genera un small tile de device alargado horizontalmente.
     * Se puede apagar y prender y en funcion de eso cambia la imagen o no
     */
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun FavoriteSmallTile(
        modifier: Modifier = Modifier,
        device: Device,
        onNavigateToConfigScreen: (Type) -> Unit
    ){
        Surface(
            shape = MaterialTheme.shapes.small,
            modifier = modifier
        ) {
            Card (
                modifier = Modifier
                    .fillMaxWidth(),
                backgroundColor = Color.LightGray,
                onClick = {onNavigateToConfigScreen(device.deviceType)}
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
                            .width(48.dp)
                            .height(48.dp)
                    )
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
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Text(
                                text = device.getName(),
                                style = MaterialTheme.typography.h6,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis, // Display the text in a single line
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .weight(0.30f)
                                .fillMaxSize()
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            if (device.getSwitchState()){
                                FavoritesSmallIconsList(imageList = device.getSmallIconsList())
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
                    )

                }

            }
        }
    }

    @Composable
    fun FavoritesSmallTileRow (
        modifier: Modifier = Modifier,
        onNavigateToConfigScreen: (Type) -> Unit
    ){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),     //se meustra el icono perfecto
            modifier = modifier.fillMaxWidth()
        ) {
            items(items = favoriteSmallTileData) { item ->
                FavoriteSmallTile(
                    device = item,
                    onNavigateToConfigScreen = onNavigateToConfigScreen
                )

            }
        }
    }

    @Composable
    fun FavoritesSmallIconsList(imageList: List<Int>){
        imageList.forEach { id ->
            Image(
                painter = painterResource(id),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .width(32.dp)
                    .height(32.dp)
            )
        }
    }


//-------- A PARTIR DE ACA ESTAN LAS PREVIEW ------------------

    @Preview (showBackground = false)
    @Composable
    fun FavoriteSmallTilePreview(){
        SmartHouse_tp3Theme {
            FavoriteSmallTile(
                device = DeviceOven("thomi Oven"),
                modifier = Modifier.padding(8.dp),
                onNavigateToConfigScreen = {}
            )
        }
    }



    val favoriteSmallTileData = listOf(
        DeviceAirConditioner("FAVORITE thomi AC"),
        DeviceOven("FAVORITE marcelo gallardo al horno"),
        DeviceAirConditioner("FAVORITE martin AC"),
        DeviceOven("FAVORITE martin oven"),
        DeviceAirConditioner("FAVORITE federico AC"),
    )
