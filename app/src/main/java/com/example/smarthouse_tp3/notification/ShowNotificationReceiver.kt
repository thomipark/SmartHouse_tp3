package com.example.smarthouse_tp3.notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.smarthouse_tp3.MainActivity
import com.example.smarthouse_tp3.R
import com.example.smarthouse_tp3.ui.DeviceMap
import com.example.smarthouse_tp3.ui.DeviceViewModel

class ShowNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == MyIntent.SHOW_NOTIFICATION) {
            val deviceId: String? = intent.getStringExtra(MyIntent.DEVICE_ID)
            Log.d(TAG, "Show notification intent received {$deviceId)")

            val deviceViewModel = DeviceMap.map[deviceId]
            if (deviceViewModel != null) {
                intent.getStringExtra(MyIntent.EVENT)
                    ?.let { showNotification(context, deviceViewModel, it)
                    }
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun showNotification(context: Context, device: DeviceViewModel, event : String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MyIntent.DEVICE_ID, device.getDeviceId())
            putExtra(MyIntent.EVENT, event)
        }
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )


        val name = device.uiState.value.name
        val room = device.uiState.value.room?.name
        val type = device.uiState.value.type?.name
        Log.d(TAG, "showNot device event (${event})")

        val notificationText = context.getString(
            R.string.notification_text,
            name, type, room, event
        )


        val builder = NotificationCompat.Builder(context, MyApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.notifications_on)
            .setContentTitle(name)
            .setContentText("$name - $type - $event - $room")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(notificationText)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        try {
            val notificationManager = NotificationManagerCompat.from(context)
            if (notificationManager.areNotificationsEnabled())
                notificationManager.notify(device.getDeviceId().hashCode(), builder.build())
        } catch (e: SecurityException) {
            Log.d(TAG, "Notification permission not granted $e")
        }
    }

    companion object {
        private const val TAG = "ShowNotificationReceiver"
    }
}