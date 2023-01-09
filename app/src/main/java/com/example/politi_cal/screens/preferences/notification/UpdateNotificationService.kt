package com.example.politi_cal.screens.preferences.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.politi_cal.MainActivity
import com.example.politi_cal.R

class UpdateNotificationService(
    private val context: Context
) {

    companion object {
        const val UPDATE_CHANNEL_ID = "update_channel"
    }

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(message: String) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context, 1, activityIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE
            } else {
                0
            }
        )
        val updateIntent = PendingIntent.getBroadcast(
            context, 2,
            Intent(context, UpdateNotificationReceiver::class.java),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE
            } else {
                0
            }
        )
        val notification = NotificationCompat.Builder(context, UPDATE_CHANNEL_ID)
            .setSmallIcon(R.drawable.app_logo)
            .setContentTitle("Politi-cal update notification")
            .setContentText(message)
            .setContentIntent(activityPendingIntent)
//            .addAction(
//                R.drawable.app_logo,
//                "Update",
//                updateIntent
//            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
        notificationManager.notify(1, notification)
//        with(NotificationManagerCompat.from(this.context)){
//            notify(1, notification.build())
//        }
//    }
    }
}