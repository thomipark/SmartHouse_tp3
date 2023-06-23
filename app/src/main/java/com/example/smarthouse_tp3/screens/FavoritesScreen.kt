package com.example.smarthouse_tp3.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.data.network.model.NetworkDevice
import com.example.smarthouse_tp3.ui.AirConditionerViewModel
import com.example.smarthouse_tp3.ui.DeviceMap
import com.example.smarthouse_tp3.ui.DeviceViewModel
import com.example.smarthouse_tp3.ui.DevicesViewModel
import com.example.smarthouse_tp3.ui.FaucetViewModel
import com.example.smarthouse_tp3.ui.FavouritesList
import com.example.smarthouse_tp3.ui.LightViewModel
import com.example.smarthouse_tp3.ui.NavigationViewModel
import com.example.smarthouse_tp3.ui.OvenViewModel
import com.example.smarthouse_tp3.ui.VacuumViewModel
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel,
    devicesViewModel: DevicesViewModel,
    onNavigateToConfigScreen: () -> Unit
) {

    Column(
        modifier = modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
    ) {
        DevicesSmallTileRowFav(
            navigationViewModel = navigationViewModel,
            onNavigateToConfigScreen = onNavigateToConfigScreen,
            devicesViewModel = devicesViewModel,
        )
    }
}


@Composable
fun DevicesSmallTileRowFav(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel,
    devicesViewModel: DevicesViewModel,
    onNavigateToConfigScreen: () -> Unit
) {
    val noDevicesText = stringResource(id = R.string.no_devices_added_to_favorites)

    Column(modifier = modifier) {
        if (FavouritesList.list.isEmpty()) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (FavouritesList.list.isEmpty()) {
                    Box(
                        modifier = Modifier.padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = noDevicesText,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items = DeviceMap.map.values.filter { item ->
                    FavouritesList.list.contains(item.getDeviceId())
                }) { item ->
                    DeviceSmallTile(
                        deviceViewModel = item,
                        navigationViewModel = navigationViewModel,
                        onNavigateToConfigScreen = onNavigateToConfigScreen
                    )
                }
            }
        }
    }
}


