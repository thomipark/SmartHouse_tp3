package com.example.smarthouse_tp3

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smarthouse_tp3.notification.MyIntent
import com.example.smarthouse_tp3.notification.SkipNotificationReceiver
import com.example.smarthouse_tp3.ui.DeviceMap
import com.example.smarthouse_tp3.ui.DevicesViewModel
import com.example.smarthouse_tp3.ui.NavigationViewModel
import com.example.smarthouse_tp3.ui.NotificationList
import com.example.smarthouse_tp3.ui.theme.SmartHouse_tp3Theme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    private lateinit var receiver: SkipNotificationReceiver
    private var shouldSkipNotification: Boolean = true


    @OptIn(ExperimentalPermissionsApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHouse_tp3Theme {
                val navController = rememberNavController()
                var showBottomBar by rememberSaveable { mutableStateOf(true) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val navigationViewModel: NavigationViewModel = viewModel()
                val deviceConfigScreen = stringResource(id = R.string.device_configuration_screen)
                val routineConfigScreen = stringResource(id = R.string.routine_configuration_screen)


                showBottomBar = when (navBackStackEntry?.destination?.route) {
                    deviceConfigScreen -> false
                    routineConfigScreen -> false
                    else -> true // in all other cases, show the bottom bar
                }


                Scaffold(
                    bottomBar = { if (showBottomBar) BottomBar(navController = navController) },
                    topBar = {
                        TopBar(
                            navController = navController,
                            navigationViewModel = navigationViewModel
                        )
                    }
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val permissionState =
                            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
                        if (!permissionState.hasPermission) {
                            NotificationPermission(permissionState = permissionState)
                            LaunchedEffect(true) {
                                permissionState.launchPermissionRequest()
                            }
                        }
                    }

                    MyNavHost(
                        navController = navController,
                        navigationViewModel = navigationViewModel
                    )


                }



            }
        }
    }

    /***
     * Returns true if the app is in the foreground
     */
    private fun isAppInForeground(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        val appProcesses = activityManager?.runningAppProcesses ?: return false

        for (appProcess in appProcesses) {
            if (appProcess.processName == packageName && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true
            }
        }
        return false
    }
    override fun onStart() {
        super.onStart()
        val deviceId = intent?.getStringExtra(MyIntent.DEVICE_ID)

        val skip = isAppInForeground() || !NotificationList.list.contains(deviceId)
        receiver = SkipNotificationReceiver(DEVICE_ID,skip)
        IntentFilter(MyIntent.SHOW_NOTIFICATION)
            .apply { priority = 1 }
            .also {
                var flags = 0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    flags = Context.RECEIVER_NOT_EXPORTED

                registerReceiver(receiver, it, flags)
            }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun NotificationPermission(
        permissionState: PermissionState,
    ) {
        PermissionRequired(
            permissionState = permissionState,
            permissionNotGrantedContent = { /* TODO: función para infromarle al usuario de la necesidad de otrogar el permiso */ },
            permissionNotAvailableContent = { /* TODO: función hacer las adecuaciones a la App debido a que el permiso no fue otorgado  */ }
        ) {
            /* Hacer uso del recurso porque el permiso fue otorgado */
        }
    }

    companion object {
        // TODO: valor fijo, cambiar por un valor de dispositivo válido.
        private const val DEVICE_ID = "17a0e159797bc497a"
    }


}


