package com.example.smarthouse_tp3.advanced_devices

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.ui.LightViewModel
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import okhttp3.internal.parseCookie

@Composable
fun LightConfigScreen(
    viewModel: LightViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    val controller = rememberColorPickerController()

    val initialValue = uiState.state?.brightness?.toFloat()?.times(0.01f)

    val sliderValue = remember { mutableStateOf( initialValue ?: 0f ) }

    val isPopupOpen = remember { mutableStateOf(false) }

    var hexCode : String = "FFFFFF"

    Column() {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    ) {
                    Text(
                        text = "${uiState.state?.brightness} %",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(start = 30.dp, bottom = 8.dp),
                        fontSize = 80.sp,
                    )
                }
            }
        }
        Column {
            Row {
                Text(
                    text = stringResource(R.string.brightness_text),
                    fontSize = 28.sp
                )
            }

            Row() {
                Slider(
                    value = sliderValue.value,
                    onValueChange = { newValue ->
                        sliderValue.value = newValue

                    },
                    onValueChangeFinished = {viewModel.changeBrightness((sliderValue.value * 100).toInt().toString())},
                    modifier = Modifier.weight(1f)
                )
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                Button(
                    onClick = { isPopupOpen.value = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    )
                ) {
                    Text(text = stringResource(R.string.change_color))
                }
            }



        }


        // Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        //     BoxWithConstraints(
        //         modifier = Modifier
        //             .size(40.dp)
        //             .background(Color(hexCode), shape = CircleShape)
        //     ) {
        //         // Adjust the size and padding of the circle as needed
        //     }
        //     Spacer(modifier = Modifier.width(8.dp))
        //     Text(text = hexCode, fontSize = 14.sp)
        // }

    }


    if (isPopupOpen.value) {
        AlertDialog(
            onDismissRequest = { isPopupOpen.value = false },
            title = { Text(text = stringResource(R.string.select_color)) },
            text = {
                Column() {

                    Row {
                        HsvColorPicker(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                                .padding(10.dp),
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
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                controller = controller
                            )
                        }


                    }
                }

            },
            confirmButton = {
                Button(
                    onClick = {
                        isPopupOpen.value = false
                        viewModel.changeColor(hexCode.substring(2))
                              },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    )
                ) {
                    Text(text = stringResource(R.string.save_color))
                }
            }
        )
    }
}

@Composable
fun LightConfigScreen2(
    viewModel: LightViewModel
    //changeColor: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val controller = rememberColorPickerController()


    //uiState.state?.brightness?.div(100.0)?.let { controller.setWheelAlpha(it.toFloat()) }

    var color: Color = Color.Black
    var fromUser: Boolean = false
    // var hexCode = Integer.toHexString(controller.selectedColor.value.toArgb()).toString()
    // hexCode = hexCode.substring(hexCode.length - 6)


    var hexCode = uiState.state?.color
    var newHexCode = uiState.state?.color

    var brightness = (controller.selectedColor.value.alpha * 100).toInt()
    var newBrightness = uiState.state?.brightness?.toInt()

    Log.d("INITIAL BR" , ((uiState.state?.brightness?.toInt()?.times(255) ?: 0) /100).toString())
    val red     = uiState.state?.color?.substring(0, 2)?.toInt(16)
    val green   = uiState.state?.color?.substring(2, 4)?.toInt(16)
    val blue    = uiState.state?.color?.substring(4, 6)?.toInt(16)

    Log.d("INITIAL COL:", "Red $red, Green: $green, Blue: $blue , Brightness:" + uiState.state?.brightness.toString())

    Column() {

        Row {
            Text(text = uiState.state?.color.toString())
        }

        Row {
            Text(text = "Red: $red, Green: $green, Blue: $blue , Brightness:" + uiState.state?.brightness.toString())
        }


        Row {
            Text(text = controller.selectedPoint.toString())
        }

        Row {
            Text(
                text = stringResource(R.string.hue),
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Row {
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(10.dp),
                controller = controller,
                initialColor =
                blue?.let {
                    green?.let { it1 ->
                        red?.let { it2 ->
                            uiState.state?.brightness?.toInt()?.let { it3 ->
                                Color(
                                    red = it2,
                                    green = it1,
                                    blue = it,
                                    alpha = (it3 * 255 / 100)
                                )
                            }
                        }
                    }
                },

                onColorChanged = { colorEnvelope: ColorEnvelope ->

                    Log.d("CONTROLLER:", controller.selectedColor.toString())
                    color = colorEnvelope.color // ARGB color value.
                    //viewModel.changeColor(controller.selectedColor.value.toArgb().toString().substring(3)) // Color hex code, which represents clor value.
                    // viewModel.changeBrightness(controller.selectedColor.value.alpha * 100)
                    // Log.d("HELLO", (controller.selectedColor.value.alpha * 100.toLong()).toLong().toString())

                    newHexCode = Integer.toHexString(controller.selectedColor.value.toArgb()).toString()
                    newHexCode = newHexCode!!.substring(newHexCode!!.length - 6)


                    // newBrightness = (controller.selectedColor.value.alpha * 100).toInt()

                    // Log.d("MYnewHexCode:" , newHexCode.toString())
                    // Log.d("MYnewBrightness:" , newBrightness.toString() + "   ${controller.selectedColor.value.alpha}")

                    // if (brightness != newBrightness) {
                    //     brightness = newBrightness as Int
                    //     viewModel.changeBrightness(brightness.toString())
                    // } else if (hexCode != newHexCode) {
                    //     hexCode = newHexCode as String
                    //     viewModel.changeColor(hexCode!!.substring(hexCode!!.length - 6))
                    // }



                    viewModel.changeBrightness(
                        (controller.selectedColor.value.alpha * 100).toInt().toString()
                    )
                    viewModel.changeColor(newHexCode!!)



                    fromUser =
                        colorEnvelope.fromUser // Represents this event is triggered by user or not.
                    //hexCode = colorEnvelope.hexCode
                }
            )
        }
        Text(
            text = stringResource(R.string.intensity),
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
        AlphaSlider(
            modifier = Modifier
                .padding(10.dp)
                .height(35.dp),
            controller = controller,
        )
        Column (
            modifier = Modifier.fillMaxWidth(),
        ) {
            AlphaTile(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(80.dp)
                    .clip(RoundedCornerShape(6.dp)),
                controller = controller
            )
        }

    }
}



@Preview(showBackground = true)
@Composable
fun LightConfigScreenPreview() {

    val viewModel: LightViewModel = viewModel()
    viewModel.fetchDevice("4d842b03d28e19bc")

    LightConfigScreen(viewModel = viewModel)

}

@Preview(showBackground = true)
@Composable
fun LightConfigScreenPreview2() {

    val viewModel: LightViewModel = viewModel()
    viewModel.fetchDevice("4d842b03d28e19bc")

    LightConfigScreen2(viewModel = viewModel)

}


@Preview(showBackground = true)
@Composable
fun ColorPickerPreview() {


    val controller = rememberColorPickerController()
    Column() {



        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(10.dp),
            controller = controller,
            initialColor =
            Color(
                red     = 25,
                green   = 255,
                blue    = 255,
                alpha   = 1
            )
            , onColorChanged = { colorEnvelope: ColorEnvelope ->
                Log.d("Controller", controller.selectedPoint.toString())
                Log.d("Controller", controller.selectedColor.toString())

            }
        )


        Row {
            Text(text = controller.selectedColor.toString())
        }
    }
}
