package com.example.smarthouse_tp3.data.network

import com.example.smarthouse_tp3.data.network.model.NetworkDevice
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceList
import com.example.smarthouse_tp3.data.network.model.NetworkRoutineList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/devices")
    suspend fun getAllDevices() : Response<NetworkDeviceList>

    @GET("api/devices")
    suspend fun getDevices(@Query("deviceId") id: Int) : Response<NetworkDevice>



    @GET("api/routines")
    suspend fun getAllRoutines() : Response<NetworkRoutineList>
}