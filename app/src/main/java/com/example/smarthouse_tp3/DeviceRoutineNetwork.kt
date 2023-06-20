package com.example.smarthouse_tp3

import androidx.lifecycle.viewModelScope
import com.example.smarthouse_tp3.data.network.RetrofitClient
import com.example.smarthouse_tp3.data.network.model.NetworkAction
import com.example.smarthouse_tp3.data.network.model.NetworkDevice
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeviceRoutineNetwork(
    val networkDevice: NetworkDevice,
    val networkActionList: MutableList<NetworkAction> = mutableListOf()
) {
    private var fetchJob: Job? = null

    fun addAction(action: NetworkAction) {
        networkActionList.add(action)
    }
}
