package com.example.smarthouse_tp3

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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.smarthouse_tp3.ui.NavigationViewModel

@Composable
fun DeviceScreen(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel,
    onNavigateToConfigScreen: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        DevicesSmallTileRow(
            navigationViewModel = navigationViewModel,
            onNavigateToConfigScreen = onNavigateToConfigScreen
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeviceSmallTile(
    modifier: Modifier = Modifier,
    device: Device,
    navigationViewModel: NavigationViewModel,
    onNavigateToConfigScreen: () -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.small.copy(CornerSize(8.dp)),
        modifier = modifier
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color.LightGray,
            onClick = {
                navigationViewModel.selectNewDevice(device)
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
                Image(
                    painter = painterResource(device.getIcon()),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(0.25f) // 40% of the available width
                        .width(48.dp)
                        .height(48.dp)
                )
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
                        Text(
                            text = device.getName(),
                            style = MaterialTheme.typography.h6,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .weight(0.30f)
                            .fillMaxSize()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        if (device.getSwitchState()) {
                            SmallIconsList(imageList = device.getSmallIconsList())
                        }
                    }
                }

                Switch(
                    checked = device.getSwitchState(),
                    onCheckedChange = { device.changeSwitchState() },
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
        val filteredDevices = getFilteredDevices(selectedCategory)
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
                    DeviceSmallTile(
                        device = item,
                        navigationViewModel = navigationViewModel,
                        onNavigateToConfigScreen = onNavigateToConfigScreen
                    )
                }
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
            .padding(horizontal = 8.dp, vertical = 0.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Disable click indication
            ) { onCategorySelected(category) }
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.h6,
            fontWeight = (if (isSelected) FontWeight.ExtraBold else FontWeight.Light),
            modifier = Modifier.padding(8.dp)
        )
    }
}

fun getFilteredDevices(category: DeviceCategory): List<Device> {
    return when (category) {
        DeviceCategory.All -> smallTileData
        else -> smallTileData.filter { it.getType().name == category.name }
    }
}

@Composable
fun SmallIconsList(imageList: List<Int>) {
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

enum class DeviceCategory {
    All, OVEN, AC, FAUCET, VACUUM, LIGHT
}

val smallTileData = listOf(
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