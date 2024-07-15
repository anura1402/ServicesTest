package ru.anura.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyForegroundService : Service() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val notificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    private val notificationBuilder by lazy {
        createNotification()

    }

    var onProgressChanged:((Int)->Unit)? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("SERVICE_TAG", "onCreate")
        createChannel()
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notificationBuilder.build())

    }

    private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Foreground service")
        .setContentText("Foreground Text")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setProgress(100, 0, false)
        .setOnlyAlertOnce(true)


    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        coroutineScope.launch {
            for (i in 0..100 step 5) {
                delay(1000)
                val notification =
                    notificationBuilder.setProgress(100, i, false)
                        .build()
                notificationManager.notify(NOTIFICATION_ID, notification)
                onProgressChanged?.invoke(i)
                Log.d("SERVICE_TAG", "Timer $i")
            }
            stopSelf()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        Log.d("SERVICE_TAG", "onDestroy")
    }

    override fun onBind(p0: Intent?): IBinder {
        return LocalBinder()
    }

    inner class LocalBinder : Binder() {
        fun getService() = this@MyForegroundService
    }
    companion object {
        private const val CHANNEL_ID = "ForegroundServiceChannel"
        private const val CHANNEL_NAME = "ForegroundServiceChannelName"
        private const val NOTIFICATION_ID = 1
        fun newIntent(context: Context): Intent {
            return Intent(context, MyForegroundService::class.java)
        }
    }
}
