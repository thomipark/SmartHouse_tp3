package com.example.smarthouse_tp3.data.network

import com.example.smarthouse_tp3.data.network.model.NetworkActionResult
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceList
import com.example.smarthouse_tp3.data.network.model.NetworkDeviceResult
import com.example.smarthouse_tp3.data.network.model.NetworkRoomList
import com.example.smarthouse_tp3.data.network.model.NetworkRoutineList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("devices")
    suspend fun getAllDevices(): Response<NetworkDeviceList>

    @GET("devices/{deviceId}")
    suspend fun getDevice(@Path("deviceId") deviceId: String): Response<NetworkDeviceResult>

    @PUT("devices/{deviceId}/{actionName}")
    suspend fun executeAction(
        @Path("deviceId") deviceId: String,
        @Path("actionName") actionName: String,
        @Body body: Array<String>
    ): Response<NetworkActionResult>

    @GET("routines")
    suspend fun getAllRoutines(): Response<NetworkRoutineList>

    @GET("rooms")
    suspend fun getAllRooms(): Response<NetworkRoomList>

    @PUT("routines/{routineId}/execute")
    suspend fun executeRoutine(
        @Path("routineId") routineId: String
    ): Response<NetworkActionResult>
}