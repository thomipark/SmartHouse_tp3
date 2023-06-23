package com.example.smarthouse_tp3.screens

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
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.smarthouse_tp3.ui.LightViewModel
import com.example.smarthouse_tp3.ui.NavigationViewModel
import com.example.smarthouse_tp3.ui.OvenViewModel
import com.example.smarthouse_tp3.ui.VacuumViewModel
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun DeviceScreen(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel,
    devicesViewModel: DevicesViewModel,
    onNavigateToConfigScreen: () -> Unit
) {

    val devicesUiState by devicesViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
    ) {
        DevicesSmallTileRow(
            navigationViewModel = navigationViewModel,
            onNavigateToConfigScreen = onNavigateToConfigScreen,
            devicesViewModel = devicesViewModel,
        )
    }
}


@Composable
fun DevicesSmallTileRow(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel,
    devicesViewModel: DevicesViewModel,
    onNavigateToConfigScreen: () -> Unit
) {
    val devicesUiState by devicesViewModel.uiState.collectAsState()
    val devicesList = devicesUiState.devices?.devices


    var selectedCategory by rememberSaveable { mutableStateOf(DeviceCategory.All) }

    Column(modifier = modifier) {
        SlideGroup(
            categories = DeviceCategory.values(),
            selectedCategory = selectedCategory,
            navigationViewModel = navigationViewModel,
            onCategorySelected = { category -> selectedCategory = category }
        )

        var filteredDevices : List<NetworkDevice> = emptyList()
        if (devicesList != null) {
            filteredDevices = getFilteredDevices(selectedCategory, devicesList)
        }
        if (filteredDevices.isEmpty()) {
            val noDevicesText = when (selectedCategory) {
                DeviceCategory.All -> "No devices added"
                else -> "No devices of this type"
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = noDevicesText,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items = filteredDevices) { item ->
                    val myDevice = DeviceMap.map[item.id]
                    if (myDevice != null) {
                        DeviceSmallTile(
                            deviceViewModel = myDevice,
                            navigationViewModel = navigationViewModel,
                            onNavigateToConfigScreen = onNavigateToConfigScreen
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeviceSmallTile(
    modifier: Modifier = Modifier,
    deviceViewModel : DeviceViewModel = viewModel(),
    navigationViewModel: NavigationViewModel,
    onNavigateToConfigScreen: () -> Unit
) {
    val deviceUiState by deviceViewModel.uiState.collectAsState()



    Surface(
        shape = MaterialTheme.shapes.small.copy(CornerSize(8.dp)),
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                deviceUiState.deviceIcon?.let { painterResource(it) }?.let {
                    Image(
                        painter = it,
                        contentDescription = null,
                        modifier = Modifier
                            .weight(0.25f) // 40% of the available width
                            .width(48.dp)
                            .height(48.dp),
                        colorFilter = ColorFilter.tint(color = deviceUiState.deviceIconColor)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(0.7f),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxSize()
                            .weight(0.45f),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        deviceUiState.name?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.h6,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .weight(0.30f)
                            .fillMaxSize()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {

                    }
                }

                Switch(
                    checked = deviceUiState.switchState,
                    onCheckedChange = { deviceViewModel.changeSwitchState() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Green,
                    ),
                    modifier = Modifier
                        .weight(0.25f) // 30% of the available width
                        .fillMaxWidth()
                )
            }
        }
    }
}



@Composable
fun SlideGroup(
    categories: Array<DeviceCategory>,
    selectedCategory: DeviceCategory,
    navigationViewModel: NavigationViewModel,
    onCategorySelected: (DeviceCategory) -> Unit
) {
    val scrollState = rememberLazyListState()

    LazyRow(
        modifier = Modifier
            .padding(16.dp)
            .fadingEdges(scrollState, MaterialTheme.colors.surface)
            .background(MaterialTheme.colors.surface)
            .clip(MaterialTheme.shapes.small),
        state = scrollState
    ) {
        items(categories) { category ->
            CategoryItem(
                category = category,
                isSelected = category == selectedCategory,
                navigationViewModel = navigationViewModel,
                onCategorySelected = onCategorySelected
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: DeviceCategory,
    isSelected: Boolean,
    navigationViewModel: NavigationViewModel,
    onCategorySelected: (DeviceCategory) -> Unit
) {
    val categoryName = when (category) {
        DeviceCategory.All -> stringResource(id = R.string.all)
        DeviceCategory.AC -> category.name + "s" // Append 's' without modifying the case
        else -> category.name.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } + "s" // Convert to lowercase, capitalize first letter, and add 's'
    }

    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 0.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Disable click indication
            ) {
                onCategorySelected(category)
            }
    ) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.h6,
            fontWeight = (if (isSelected) FontWeight.ExtraBold else FontWeight.Light),
            modifier = Modifier.padding(8.dp)
        )
    }
}

fun getFilteredDevices(category: DeviceCategory, deviceList : List<NetworkDevice>): List<NetworkDevice> {
    return when (category) {
        DeviceCategory.All -> deviceList
        else -> deviceList.filter { it.type?.name == category.stringValue }
    }
}



enum class DeviceCategory(val stringValue: String) {
    All("All"),
    OVEN("oven"),
    AC("ac"),
    FAUCET("faucet"),
    VACUUM("vacuum"),
    LIGHT("lamp")
}


@Composable
fun deviceViewModelMaker(id : String = "", typeName : String?) : DeviceViewModel{
    if (typeName == "lamp") {
        return LightViewModel(deviceId = id)
    } else if (typeName == "oven") {
        return OvenViewModel(deviceId = id)
    } else if (typeName == "vacuum") {
        return VacuumViewModel(id)
    } else if (typeName == "ac") {
        return AirConditionerViewModel(id)
    } else if (typeName == "faucet") {
        return FaucetViewModel(id)
    } else {
        return LightViewModel(id)
    }
}
