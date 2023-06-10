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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.smarthouse_tp3.ui.theme.SmartHouse_tp3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHouse_tp3Theme {
                MyNavHost()
            }
        }
    }
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
fun BottomBar(
    navController: NavController
){
    val items = listOf(
        MainScreen.RoutinesScreen,
        MainScreen.DevicesScreen,
        MainScreen.PlacesScreen
    )


}

@Composable
fun BottomNavigation(
    navController: NavController
) {
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route
//    items.forEach { item ->
//        BottomNavigationItem(
//            icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
//            label = { Text(text = item.title) },
//            alwaysShowLabel = true,
//            selected = currentRoute == item.route,
//            onClick = {
//                navController.navigate(item.route) {
//                    navController.graph.startDestinationRoute?.let { screenRoute ->
//                        popUpTo(screenRoute) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                }
//            }
//        )
//    }
}



/* --------------------- LAS PREVIEW EMPIEZAN ACA ------------------*/

//@Preview (showBackground = true)
@Composable
fun CartItemStatelessPreview(){
    var quantity: Int by remember { mutableStateOf(1) }

    CartItemStateless(
        quantity,
        {quantity++},
        {quantity--}
    )
}

