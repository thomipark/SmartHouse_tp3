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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.data.network.model.NetworkDevice
import com.example.smarthouse_tp3.ui.AirConditionerViewModel
import com.example.smarthouse_tp3.ui.DeviceViewModel
import com.example.smarthouse_tp3.ui.DevicesViewModel
import com.example.smarthouse_tp3.ui.FaucetViewModel
import com.example.smarthouse_tp3.ui.LightViewModel
import com.example.smarthouse_tp3.ui.NavigationViewModel
import com.example.smarthouse_tp3.ui.OvenViewModel
import com.example.smarthouse_tp3.ui.VacuumViewModel
import com.example.smarthouse_tp3.ui.theme.SmartHouse_tp3Theme

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel,
    devicesViewModel: DevicesViewModel,
    onNavigateToConfigScreen: () -> Unit
) {
    val devicesUiState by devicesViewModel.uiState.collectAsState()
    val devicesList = devicesUiState.devices?.devices


    Column(
        modifier = modifier.padding(0.dp, 8.dp,0.dp,0.dp)
    ) {
        FavoritesSmallTileRow(
            favoriteSmallTileData = favoriteSmallTileData,
            onNavigateToConfigScreen = onNavigateToConfigScreen,
            navigationViewModel = navigationViewModel,
            devicesList = devicesList
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteSmallTile(
    deviceViewModel: DeviceViewModel = viewModel(),
    modifier: Modifier = Modifier,
    device: NetworkDevice = NetworkDevice(),
    navigationViewModel: NavigationViewModel,
    onNavigateToConfigScreen: () -> Unit
) {
//    val deviceViewModel : DeviceViewModel = viewModel()
    val deviceUiState by deviceViewModel.uiState.collectAsState()
    device.id?.let { deviceViewModel.fetchDevice(it) }


    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.primaryVariant,
            onClick = {
                navigationViewModel.selectNewDeviceViewModel(deviceViewModel)
                onNavigateToConfigScreen()
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    deviceUiState.deviceIcon?.let { painterResource(it) }?.let {
                        Image(
                            painter = it,
                            contentDescription = null,
                            modifier = Modifier
                                .width(48.dp)
                                .height(48.dp)
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        deviceUiState.name?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.h6,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }

                        if (deviceUiState.switchState) {
                            FavoritesSmallIconsList(imageList = deviceViewModel.getSmallIconsList())
                        }
                    }

                    Switch(
                        checked = deviceUiState.switchState,
                        onCheckedChange = { deviceViewModel.changeSwitchState() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Green,
                        ),
                        modifier = Modifier.fillMaxWidth(0.2f)
                    )
                }
            }
        }
    }
}

@Composable
fun FavoritesSmallTileRow(
    modifier: Modifier = Modifier,
    favoriteSmallTileData: List<Device>,
    navigationViewModel: NavigationViewModel,
    devicesList: List<NetworkDevice>?,
    onNavigateToConfigScreen: () -> Unit
) {
    Column(modifier = modifier) {
        if (favoriteSmallTileData.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = modifier.fillMaxWidth()
            ) {
                if (devicesList != null) {
                    items(items = devicesList) { item ->
                        item.let {
                            val myDevice : DeviceViewModel = deviceViewModelMaker(typeName = item.type?.name)
                            FavoriteSmallTile(
                                deviceViewModel = myDevice,
                                device = it,
                                navigationViewModel = navigationViewModel,
                                onNavigateToConfigScreen = onNavigateToConfigScreen
                            )
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No devices added to favorites",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


@Composable
fun FavoritesSmallIconsList(imageList: List<Int>) {
    Row(verticalAlignment = Alignment.CenterVertically) {
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
}

// Preview

@Preview(showBackground = true)
@Composable
fun FavoriteSmallTilePreview() {
    SmartHouse_tp3Theme {
        val light : LightViewModel = viewModel()
        FavoriteSmallTile(
            deviceViewModel = light,
            device = NetworkDevice(id = "1fdadb82ef594f00"),
            modifier = Modifier.padding(8.dp),
            onNavigateToConfigScreen = {},
            navigationViewModel = NavigationViewModel()
        )
    }
}

//val favoriteSmallTileData = emptyList<Device>()


val favoriteSmallTileData = listOf(
    DeviceAirConditioner("FAVORITE thomi AC"),
    DeviceOven("FAVORITE marcelo gallardo al horno"),
    DeviceAirConditioner("FAVORITE martin AC"),
    DeviceOven("FAVORITE martin oven"),
    DeviceAirConditioner("FAVORITE federico AC"),
)

