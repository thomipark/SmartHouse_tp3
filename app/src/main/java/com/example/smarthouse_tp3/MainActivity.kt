package com.example.smarthouse_tp3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smarthouse_tp3.ui.theme.SmartHouse_tp3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHouse_tp3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DeviceScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun CartItemStateless(
    quantity: Int,                      //  state
    incrementQuantity: () -> Unit,       //event
    decrementQuantity: () -> Unit
){

    Row {
        Text( text = "Cart item:")
        Button(
            onClick = { incrementQuantity() },
        ) {
            Text(text = "+")
        }
        Button(onClick = { decrementQuantity() }) {
            Text(text = "-")
        }
        Text(text = quantity.toString())
    }
}


@Composable
fun GreetingPreview() {
    SmartHouse_tp3Theme {
        Greeting("Android")
    }
}

@Preview (showBackground = true)
@Composable
fun CartItemStatelessPreview(){
    var quantity: Int by remember { mutableStateOf(1) }

    CartItemStateless(
        quantity,
        {quantity++},
        {quantity--}
    )
}
