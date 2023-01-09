package com.example.politi_cal.screens.preferences.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class UpdateApp: Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                UpdateNotificationService.UPDATE_CHANNEL_ID,
                "Update",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Used for update notification"
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}