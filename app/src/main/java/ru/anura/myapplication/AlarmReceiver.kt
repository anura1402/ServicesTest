package ru.anura.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notificationManager = getSystemService(
                it,
                NotificationManager::class.java
            ) as NotificationManager
            createChannel(notificationManager)

            val notification = NotificationCompat.Builder(it, CHANNEL_ID)
                .setContentTitle("AlarmReceiver")
                .setContentText("AlarmReceiver Text")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build()

            notificationManager.notify(NOTIFICATION_ID, notification)
        }


    }

    private fun createChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "ForegroundServiceChannel"
        private const val CHANNEL_NAME = "ForegroundServiceChannelName"
        private const val NOTIFICATION_ID = 1
        fun newIntent(context: Context): Intent {
            return Intent(context, AlarmReceiver::class.java)
        }
    }
}