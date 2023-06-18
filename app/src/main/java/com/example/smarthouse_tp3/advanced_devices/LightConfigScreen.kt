package com.example.smarthouse_tp3.advanced_devices

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.graphics.toArgb
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
fun LightConfigScreen (
    viewModel: LightViewModel
    //changeColor: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val controller = rememberColorPickerController()
    //uiState.state?.brightness?.div(100.0)?.let { controller.setWheelAlpha(it.toFloat()) }

    var color: Color = Color.Black
    var fromUser: Boolean = false
    var hexCode = Integer.toHexString(controller.selectedColor.value.toArgb())
    var newHexCode = uiState.state?.color

    var brightness = (controller.selectedColor.value.alpha * 100).toLong()
    var newBrightness = uiState.state?.brightness


    var red = newHexCode?.substring(0, 2)?.toInt(16)?.toFloat()?.div(255)
    var green = newHexCode?.substring(2, 4)?.toInt(16)?.toFloat()?.plus(1)?.div(255)
    var blue = newHexCode?.substring(4, 6)?.toInt(16)?.toFloat()?.plus(2)?.div(255)

    Column() {
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
                // initialColor = red?.let {
                //     green?.let { it1 ->
                //         blue?.let { it2 ->
                //             Color(
                //                 red = it,
                //                 green = it1,
                //                 blue = it2,
                //                 alpha = brightness.toFloat()
                //             )
                //         }
                //     }
                // },
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    color = colorEnvelope.color // ARGB color value.
                    //viewModel.changeColor(controller.selectedColor.value.toArgb().toString().substring(3)) // Color hex code, which represents clor value.
                    // viewModel.changeBrightness(controller.selectedColor.value.alpha * 100)


                    // Log.d("HELLO", (controller.selectedColor.value.alpha * 100.toLong()).toLong().toString())

                    newHexCode = Integer.toHexString(controller.selectedColor.value.toArgb())
                    newBrightness = (controller.selectedColor.value.alpha * 100).toLong()

                    if (brightness != newBrightness) {
                        brightness = newBrightness as Long
                        viewModel.changeBrightness(brightness)
                    } else if (hexCode != newHexCode) {
                        hexCode = newHexCode
                        viewModel.changeColor(hexCode.substring(hexCode.length - 6))
                    }


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

    LightConfigScreen (viewModel = viewModel)

}
