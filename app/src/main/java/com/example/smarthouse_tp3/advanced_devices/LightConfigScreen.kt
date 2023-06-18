package com.example.smarthouse_tp3.advanced_devices

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthouse_tp3.ui.LightViewModel
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController


@Composable
fun LightConfigScreen(
    viewModel: LightViewModel
    //changeColor: (String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()


    val controller = rememberColorPickerController()
    var color: Color = Color.Black
    //TODO ver
    var hexCode = "hola"
    var fromUser = false



    Text(
        text = "HUE",
        fontSize = 24.sp,
        modifier = Modifier.padding(start = 16.dp)
    )
    HsvColorPicker(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(10.dp),
        controller = controller,
        onColorChanged = { colorEnvelope: ColorEnvelope ->
            color = colorEnvelope.color // ARGB color value.
            //viewModel.changeColor(controller.selectedColor.value.toArgb().toString().substring(3)) // Color hex code, which represents clor value.
            // viewModel.changeBrightness(controller.selectedColor.value.alpha * 100)
            viewModel.changeBrightness((controller.selectedColor.value.alpha * 100).toLong())
            viewModel.changeColor(
                Integer.toHexString(controller.selectedColor.value.toArgb()).substring(2)
            )
            fromUser = colorEnvelope.fromUser // Represents this event is triggered by user or not.
            hexCode = colorEnvelope.hexCode
        }
    )
    Text(
        text = "INTENSITY",
        fontSize = 24.sp,
        modifier = Modifier.padding(start = 16.dp)
    )
    AlphaSlider(
        modifier = Modifier
            .padding(10.dp)
            .height(35.dp),
        controller = controller,
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Text(
        //     modifier = Modifier.align(Alignment.CenterHorizontally),
        //     text = "#" + (uiState.device?.state?.brightness?.toHexString() ?: "00") +
        //             uiState.device?.state?.color.toString(),
        //     fontSize = 24.sp
        // )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = uiState.state?.color.toString(),
            fontSize = 24.sp
        )


        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = uiState.state?.brightness.toString(),
            fontSize = 24.sp
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Brightness ${controller.selectedColor.value.alpha * 100}",
            fontSize = 24.sp
        )


        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Color: ${Integer.toHexString(controller.selectedColor.value.toArgb())}",
            fontSize = 24.sp
        )


        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = uiState.name.toString(),
            fontSize = 24.sp
        )


        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = uiState.toString(),
            fontSize = 24.sp
        )

        AlphaTile(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(80.dp),
            //.clip(RoundedCornerShape(6.dp)),
            controller = controller
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LightConfigScreenPreview() {

    val viewModel: LightViewModel = viewModel()
    viewModel.fetchDevice("4d842b03d28e19bc")

    LightConfigScreen(viewModel = viewModel)

}
