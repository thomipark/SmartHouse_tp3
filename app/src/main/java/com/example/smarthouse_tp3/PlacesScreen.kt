package com.example.smarthouse_tp3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.data.network.model.NetworkRoomList
import com.example.smarthouse_tp3.ui.NavigationViewModel
import com.example.smarthouse_tp3.ui.RoomsViewModel

@Composable
fun PlacesScreen(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel,
    onNavigateToConfigScreen: () -> Unit
) {
    val viewModel: RoomsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRooms()
    }

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
    onNavigateToConfigScreen: () -> Unit
) {
    val filteredDevices = getFilteredDevices(selectedPlace)

    Column(modifier = modifier) {
        SlideGroupPlaces(
            places = devicePlaces,
            selectedPlace = selectedPlace,
            onPlaceSelected = onPlaceSelected
        )

        if (filteredDevices.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items = filteredDevices) { item ->
                    DeviceSmallTile(
                        device = item,
                        navigationViewModel = navigationViewModel,
                        onNavigateToConfigScreen = onNavigateToConfigScreen
                    )
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
                    text = "No devices linked to this place",
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
    LazyRow(modifier = Modifier.padding(16.dp)) {
        items(places) { place ->
            PlaceItem(
                place = place,
                isSelected = place == selectedPlace,
                onPlaceSelected = { onPlaceSelected(place.takeIf { it != selectedPlace }) }
            )
        }
    }
}

@Composable
fun PlaceItem(
    place: String,
    isSelected: Boolean,
    onPlaceSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onPlaceSelected() }
            .background(if (isSelected) Color.LightGray else Color.Transparent)
    ) {
        Text(
            text = place,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}

fun getFilteredDevices(place: String?): List<Device> {
    return if (place == "All") smallTileData
    else smallTileData.filter { it.getRoom() == place }
}
