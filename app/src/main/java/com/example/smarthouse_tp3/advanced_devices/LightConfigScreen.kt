package com.example.smarthouse_tp3.advanced_devices

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.ui.LightViewModel
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun LightConfigScreen(
    viewModel: LightViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val controller = rememberColorPickerController()
    val initialValue = uiState.state?.brightness?.toFloat()
    val sliderValue = remember { mutableStateOf(initialValue ?: 0f) }
    val isPopupOpen = remember { mutableStateOf(false) }
    val formattedValue = (sliderValue.value / 100f).times(100).toInt()
    var hexCode = "FFFFFF"
    val hexColor =
        uiState.state?.color ?: "#000000" // Default color if uiState.state?.color is null
    val colorString = "#FF$hexColor" // Prepend the alpha value "FF" to the hex color string

    fun lightenColor(color: Color, percentage: Float): Color {
        val factor = 1f * percentage
        val red = color.red * factor
        val green = color.green * factor
        val blue = color.blue * factor
        return Color(red.coerceAtMost(1f), green.coerceAtMost(1f), blue.coerceAtMost(1f))
    }

    val color =
        Color(android.graphics.Color.parseColor(colorString)) // Convert the color string to a Color object

    Column {

        Column {
            Row(
                modifier = Modifier
                    .padding(top = 45.dp, start = 20.dp, end = 20.dp)
                    .height(120.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Card(
                        modifier = Modifier
                            .width(160.dp)
                            .height(70.dp),
                        border = BorderStroke(0.5.dp, Color.LightGray),
                        elevation = 0.dp
                    ) {
                        Text(
                            text = stringResource(id = R.string.hue),
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp),
                        shape = RoundedCornerShape(30),
                        elevation = 0.dp,
                        backgroundColor = color,
                        border = BorderStroke(0.5.dp, Color.LightGray)
                    ) {
                        Button(
                            onClick = { isPopupOpen.value = true },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = color
                            ),
                            modifier = Modifier.size(width = 120.dp, height = 120.dp)
                        ) {}
                    }
                }
            }
        }


        Row(
            modifier = Modifier
                .padding(top = 75.dp, start = 20.dp, end = 20.dp)
                .height(120.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(end = 12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Card(
                    modifier = Modifier
                        .width(160.dp)
                        .height(70.dp),
                    border = BorderStroke(0.5.dp, Color.LightGray),
                    elevation = 0.dp
                ) {
                    Text(
                        text = stringResource(id = R.string.brightness_text),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)

                    )
                }
            }

            Column(
                modifier = Modifier.weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var textFieldValue by remember { mutableStateOf("") }
                var isEnabled by remember { mutableStateOf(true) }

                Card(
                    modifier = Modifier
                        .width(120.dp)
                        .height(70.dp),
                    shape = RoundedCornerShape(20),
                    border = BorderStroke(0.1.dp, Color.LightGray),
                    backgroundColor = lightenColor(color, formattedValue.toFloat() / 100)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                    ) {
                        TextField(
                            value = textFieldValue,
                            onValueChange = { newValue: String ->
                                textFieldValue = newValue
                                var inputValue = textFieldValue.toIntOrNull()

                                if (inputValue != null && inputValue > 100) {
                                    inputValue = 100
                                }

                                if (inputValue == null || inputValue < 0) {
                                    inputValue = 0
                                }
                                sliderValue.value = inputValue.toString().toFloat()
                            },
                            textStyle = TextStyle(textAlign = TextAlign.Center),
                            modifier = Modifier.fillMaxSize(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            enabled = isEnabled,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    isEnabled = false
                                    textFieldValue = ""
                                }
                            ),
                        )
                        isEnabled = true
                        Box(
                            modifier = Modifier
                                .size(170.dp, 70.dp)
                                .background(lightenColor(color, formattedValue.toFloat() / 100))
                        )

                        Text(
                            text = "$formattedValue %",
                            fontWeight = FontWeight.Bold,
                            fontSize = 35.sp,
                            textAlign = TextAlign.Center,
                            color = lightenColor(Color.White, 1 - formattedValue.toFloat() / 100),
                        )

                    }
                }
            }
        }

        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.width(270.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .offset(x = (-46).dp, y = 8.dp)
                    ) {
                        Text(
                            text = "0%",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                    Slider(
                        value = sliderValue.value,
                        onValueChange = { newValue ->
                            sliderValue.value = newValue
                        },
                        onValueChangeFinished = {
                            viewModel.changeBrightness(
                                (sliderValue.value).toInt().toString()
                            )
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = if (uiState.switchState) lightenColor(
                                color,
                                formattedValue.toFloat() / 100
                            ) else Color.LightGray,
                            activeTrackColor = if (uiState.switchState) lightenColor(
                                color,
                                formattedValue.toFloat() / 100
                            ) else Color.LightGray,
                            inactiveTrackColor = if (uiState.switchState) color else Color.Gray,
                        ),
                        valueRange = 0f..100f,
                        modifier = Modifier.offset(x = (-5).dp)
                    )
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .offset(x = (276).dp, y = 8.dp)
                    ) {
                        Text(
                            text = "100%",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.padding(top = 90.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 200.dp, height = 70.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            viewModel.changeSwitchState()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primaryVariant,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.elevation(5.dp),
                        modifier = Modifier.size(width = 250.dp, height = 100.dp)
                    ) {
                        Text(
                            text = if (!uiState.switchState) stringResource(id = R.string.turn_on) else stringResource(
                                id = R.string.turn_off
                            ),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                            color = if (MaterialTheme.colors.isLight) Color.White else Color.Black,
                        )
                    }
                }
            }
        }

    }

    if (isPopupOpen.value) {
        AlertDialog(
            onDismissRequest = { isPopupOpen.value = false },
            title = {
                Text(
                    text = stringResource(R.string.select_hue),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    color = if (MaterialTheme.colors.isLight) Color.Black else Color.White
                )
            },
            text = {
                Column {

                    Row {
                        HsvColorPicker(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                                .padding(10.dp)
                                .offset(y = (-20).dp),
                            initialColor = color,
                            controller = controller,
                            onColorChanged = { colorEnvelope: ColorEnvelope ->
                                hexCode = colorEnvelope.hexCode
                            }
                        )
                    }
                    Row {

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            AlphaTile(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(2.dp)),
                                controller = controller
                            )
                        }
                    }
                }

            },
            confirmButton = {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                ) {

                    Button(
                        modifier = Modifier.width(90.dp),
                        onClick = {
                            isPopupOpen.value = false
                            viewModel.changeColor(hexCode.substring(2))
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.save_color),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
            dismissButton = {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .offset(x = (-98).dp)
                ) {

                    Button(
                        modifier = Modifier.width(90.dp),
                        onClick = { isPopupOpen.value = false },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Gray
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = if (MaterialTheme.colors.isLight) Color.White else Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                }
            },
        )
    }
}

