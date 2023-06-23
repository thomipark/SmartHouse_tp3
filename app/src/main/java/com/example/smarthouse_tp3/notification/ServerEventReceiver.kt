package com.example.smarthouse_tp3.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.collectAsState
import com.example.smarthouse_tp3.BuildConfig
import com.example.smarthouse_tp3.ui.DeviceMap
import com.example.smarthouse_tp3.ui.NotificationList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ServerEventReceiver : BroadcastReceiver() {

    private val gson = Gson()

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "Alarm received.")

        GlobalScope.launch(Dispatchers.IO) {
            val events = fetchEvents()
            events?.forEach {
                val event = it.event
                Log.d(TAG, "Broadcasting send notification intent (${it.deviceId})")
                Log.d(TAG, "Broadcasting device event (${event})")


                val intent2 = Intent().apply {
                    action = MyIntent.SHOW_NOTIFICATION
                    `package` = MyIntent.PACKAGE
                    putExtra(MyIntent.DEVICE_ID, it.deviceId)
                    putExtra(MyIntent.EVENT, event)
                }


                DeviceMap.map[it.deviceId]?.uiState?.value?.type?.name

                if (NotificationList.list.contains(it.deviceId) || event != "locationChanged") {
                    context?.sendOrderedBroadcast(intent2, null)
                } else {
                    Log.d(TAG, "Skipped beacuse not in the list")
                }

            }

        }
    }

    private fun fetchEvents(): List<EventData>? {

        Log.d(TAG, "Fetching events...")
        val url = "${BuildConfig.API_BASE_URL}devices/events"
        val connection = (URL(url).openConnection() as HttpURLConnection).also {
            it.requestMethod = "GET"
            it.setRequestProperty("Accept", "application/json")
            it.setRequestProperty("Content-Type", "text/event-stream")
            it.doInput = true
        }

        val responseCode = connection.responseCode
        return if (responseCode == HttpURLConnection.HTTP_OK) {
            val stream = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?
            val response = StringBuffer()
            val eventList = arrayListOf<EventData>()
            while (stream.readLine().also { line = it } != null) {
                when {
                    line!!.startsWith("data:") -> {
                        response.append(line!!.substring(5))
                    }

                    line!!.isEmpty() -> {
                        Log.d(TAG, response.toString())
                        val event = gson.fromJson<EventData>(
                            response.toString(),
                            object : TypeToken<EventData?>() {}.type
                        )
                        eventList.add(event)
                        response.setLength(0)
                    }
                }
            }
            stream.close()
            connection.disconnect()
            Log.d(TAG, "New events found (${eventList.size})")
            eventList
        } else {
            Log.d(TAG, "No new events found")
            null
        }
    }

    companion object {
        private const val TAG = "ServerEventReceiver"
    }
}