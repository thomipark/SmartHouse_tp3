package com.example.smarthouse_tp3


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.smarthouse_tp3.ui.NavigationViewModel

/***
 * Pantalla dedicada a Devices.
 */
@Composable
fun DeviceScreen(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel,
    onNavigateToConfigScreen: () -> Unit
){
    Column(
        modifier
            .padding(8.dp)
    ) {
        DevicesSmallTileRow(
            navigationViewModel = navigationViewModel,
            onNavigateToConfigScreen = onNavigateToConfigScreen
        )
    }
}


/**
 * Genera un small tile de device alargado horizontalmente.
 * Se puede apagar y prender y en funcion de eso cambia la imagen o no
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeviceSmallTile(
    modifier: Modifier = Modifier,
    device: Device,
    navigationViewModel: NavigationViewModel,
    onNavigateToConfigScreen: () -> Unit
){
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Card (
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = Color.LightGray,
            onClick = {
                navigationViewModel.selectNewDevice(device)
                onNavigateToConfigScreen()
            }
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
                            SmallIconsList(imageList = device.getSmallIconsList())
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
fun DevicesSmallTileRow(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel,
    onNavigateToConfigScreen: () -> Unit
) {
    var selectedCategory by rememberSaveable { mutableStateOf(DeviceCategory.All) }

    Column(modifier = modifier) {
        SlideGroup(
            categories = DeviceCategory.values(),
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                selectedCategory = category
            }
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items = getFilteredDevices(selectedCategory)) { item ->
                DeviceSmallTile(
                    device = item,
                    navigationViewModel = navigationViewModel,
                    onNavigateToConfigScreen = onNavigateToConfigScreen
                )
            }
        }
    }
}

@Composable
fun SlideGroup(
    categories: Array<DeviceCategory>,
    selectedCategory: DeviceCategory,
    onCategorySelected: (DeviceCategory) -> Unit
) {
    LazyRow(
        modifier = Modifier.padding(16.dp)
    ) {
        items(categories) { category ->
            CategoryItem(
                category = category,
                isSelected = category == selectedCategory,
                onCategorySelected = onCategorySelected
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: DeviceCategory,
    isSelected: Boolean,
    onCategorySelected: (DeviceCategory) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onCategorySelected(category) }
            .background(if (isSelected) Color.LightGray else Color.Transparent)
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}



fun getFilteredDevices(category: DeviceCategory): List<Device> {
    return when (category) {
        DeviceCategory.All -> smallTileData
        else -> smallTileData.filter { it.deviceType.name == category.name }
    }
}

enum class DeviceCategory {
    All, OVEN, AC, FAUCET, VACUUM, LIGHT
}

@Composable
fun SmallIconsList(imageList: List<Int>){
    imageList.forEach(){ id ->
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
/*
@Preview (showBackground = false)
@Composable
fun SmallTilePreview(){
    SmartHouse_tp3Theme() {
        DeviceSmallTile(
            device = DeviceOven("thomi Oven"),
            modifier = Modifier.padding(8.dp),
            onNavigateToConfigScreen = {}
        )
    }
}


@Preview
@Composable
fun DevicesSmallTileRowPreview(){
    DevicesSmallTileRow(onNavigateToConfigScreen = {})
}


//@Preview (showBackground = true)
@Composable
fun DevicesScreenPreview () {
    SmartHouse_tp3Theme() {

    }
}
*/

val smallTileData = listOf<Device>(
    DeviceAirConditioner("thomi AC"),
    DeviceOven("pepe oven"),
    DeviceOven("marcelo gallardo al horno"),
    DeviceAirConditioner("martin AC"),
    DeviceOven("samuel umtiti light"),
    DeviceOven("martin oven"),
    DeviceAirConditioner("mbappe AC"),
    DeviceOven("martin oven"),
    DeviceAirConditioner("federico AC"),
)