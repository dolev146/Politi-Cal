package com.example.politi_cal.screens.preferences.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class UpdateNotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val service = UpdateNotificationService(context)
        service.showNotification("Your preferences has updated!")
    }
}