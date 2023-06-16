package com.example.smarthouse_tp3.data.network

import com.example.smarthouse_tp3.data.network.model.NetworkActionResult
import com.example.smarthouse_tp3.data.network.model.NetworkDevice
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceList
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceResult
import com.example.smarthouse_tp3.data.network.model.NetworkRoutineList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/devices")
    suspend fun getAllDevices() : Response<NetworkDeviceList>
    @GET("api/devices/{deviceId}")
    suspend fun getDevice(@Path("deviceId") deviceId: String) : Response<NetworkDeviceResult>

    @PUT("api/devices/{deviceId}/{actionName}")
    suspend fun executeAction(
        @Path("deviceId") deviceId: String,
        @Path("actionName") actionName: String,
        @Body body: Array<String>
    ): Response<NetworkActionResult>

    @GET("api/routines")
    suspend fun getAllRoutines() : Response<NetworkRoutineList>
}