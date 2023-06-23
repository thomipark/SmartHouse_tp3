package com.example.smarthouse_tp3.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SkipNotificationReceiver(
    private val skip : Boolean
    ) : BroadcastReceiver()
{

    override fun onReceive(context: Context, intent: Intent) {
        if (skip) {
            abortBroadcast()
        }
    }

    companion object {
        private const val TAG = "SkipNotificationReceiver"
    }
}