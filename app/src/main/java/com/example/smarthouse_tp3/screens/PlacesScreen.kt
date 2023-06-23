package com.example.smarthouse_tp3.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.data.network.model.NetworkDevice
import com.example.smarthouse_tp3.data.network.model.NetworkRoomList
import com.example.smarthouse_tp3.ui.DeviceMap
import com.example.smarthouse_tp3.ui.DeviceViewModel
import com.example.smarthouse_tp3.ui.DevicesViewModel
import com.example.smarthouse_tp3.ui.NavigationViewModel
import com.example.smarthouse_tp3.ui.RoomsViewModel
import kotlinx.coroutines.delay

@Composable
fun PlacesScreen(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel,
    devicesViewModel: DevicesViewModel,
    roomsViewModel : RoomsViewModel,
    onNavigateToConfigScreen: () -> Unit
) {
    val uiState by roomsViewModel.uiState.collectAsState()

    val devicePlaces = remember { mutableListOf("All") }
    LaunchedEffect(uiState.rooms) {
        devicePlaces.clear()
        devicePlaces.add("All")
        devicePlaces.addAll(getRoomNames(uiState.rooms))
    }

    var selectedPlace by rememberSaveable { mutableStateOf(devicePlaces[0]) }

    DevicesSmallTileRowPlaces(
        modifier = modifier,
        devicePlaces = devicePlaces,
        selectedPlace = selectedPlace,
        onPlaceSelected = { place ->
            if (place != null) {
                selectedPlace = place
            }
        },
        navigationViewModel = navigationViewModel,
        devicesViewModel = devicesViewModel,
        onNavigateToConfigScreen = onNavigateToConfigScreen
    )
}

private fun getRoomNames(rooms: NetworkRoomList?): List<String> {
    return rooms?.rooms?.mapNotNull { it.name } ?: emptyList()
}


@Composable
fun DevicesSmallTileRowPlaces(
    modifier: Modifier = Modifier,
    devicePlaces: List<String>,
    selectedPlace: String?,
    onPlaceSelected: (String?) -> Unit,
    navigationViewModel: NavigationViewModel,
    devicesViewModel : DevicesViewModel,
    onNavigateToConfigScreen: () -> Unit
) {

    val devicesUiState by devicesViewModel.uiState.collectAsState()
    val devicesList = devicesUiState.devices?.devices

    if (devicesList != null) {
        if (devicesList.isEmpty()){
            Log.d("Places Screen","lista VACIA TODO MALLL")
        } else {
            Log.d("Places Screen","lista llena, todo en orden")
        }
    }

    var filteredDevices : List<NetworkDevice> = emptyList()
    if (devicesList != null) {
        filteredDevices = getFilteredDevices(selectedPlace, devicesList)
    }

    Column(modifier = modifier) {
        SlideGroupPlaces(
            places = devicePlaces,
            selectedPlace = selectedPlace,
            onPlaceSelected = onPlaceSelected
        )

        if (filteredDevices.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items = filteredDevices) { item ->
                    val myDevice = DeviceMap.map[item.id]
                    // var myDevice : DeviceViewModel? =
                    //     item.id?.let { deviceViewModelMaker(id = it, typeName = item.type?.name) }
                    // if (myDevice != null) {
                    //     myDevice = DeviceMap.map.getOrPut(item.id.toString()) {
                    //         myDevice!!
                    //     }
                    // }
                    // myDevice?.fetchDevice()
                    if (myDevice != null) {
                        DeviceSmallTile(
                            deviceViewModel = myDevice,
                            navigationViewModel = navigationViewModel,
                            onNavigateToConfigScreen = onNavigateToConfigScreen
                        )
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
                    text = if(selectedPlace == "All") "No devices added" else "No devices linked to this place",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


@Composable
fun SlideGroupPlaces(
    places: List<String>,
    selectedPlace: String?,
    onPlaceSelected: (String?) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LazyRow(
        modifier = Modifier
            .padding(16.dp)
            .fadingEdges(lazyListState, MaterialTheme.colors.surface)
            .background(MaterialTheme.colors.surface)
            .clip(MaterialTheme.shapes.small),
        state = lazyListState
    ) {
        items(places) { place ->
            PlaceItem(
                place = place,
                isSelected = place == selectedPlace,
                onPlaceSelected = { onPlaceSelected(place.takeIf { it != selectedPlace }) }
            )
        }
    }
}

fun Modifier.fadingEdges(lazyListState: LazyListState, themeColor: Color): Modifier = this.then(
    Modifier
        .graphicsLayer { alpha = 0.99F }
        .drawWithContent {
            drawContent()

            val gradientColors = listOf(Color.Transparent, themeColor)
            val gradientColorsInverted = listOf(themeColor, Color.Transparent)

            if (lazyListState.canScrollBackward) {
                if (lazyListState.firstVisibleItemIndex != 0) {
                    drawRect(
                        brush = Brush.horizontalGradient(
                            colors = gradientColors,
                            startX = 8f,
                            endX = 128f
                        ),
                        blendMode = BlendMode.DstIn
                    )
                } else {
                    drawRect(
                        brush = Brush.horizontalGradient(
                            colors = gradientColors,
                            startX = 0f,
                            endX = lazyListState.firstVisibleItemScrollOffset.toFloat()
                        ),
                        blendMode = BlendMode.DstIn
                    )
                }
            }

            if (lazyListState.canScrollForward) {
                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = gradientColorsInverted,
                        startX = size.width - 128f,
                        endX = size.width
                    ),
                    blendMode = BlendMode.DstIn
                )
            }

        }
)


@Composable
fun PlaceItem(
    place: String,
    isSelected: Boolean,
    onPlaceSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 0.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Disable click indication
            ) { onPlaceSelected() }
    ) {
        Text(
            text = place,
            style = MaterialTheme.typography.h6,
            fontWeight = (if (isSelected) FontWeight.ExtraBold else FontWeight.Light),
            modifier = Modifier.padding(8.dp)
        )
    }
}

fun getFilteredDevices(place: String?, devicesList : List<NetworkDevice>): List<NetworkDevice> {
    return if (place == "All") devicesList
    else devicesList.filter { it.room?.name == place }
}
