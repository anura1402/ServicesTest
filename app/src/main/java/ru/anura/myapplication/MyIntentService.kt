package ru.anura.myapplication

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyIntentService : IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        Log.d("SERVICE_TAG", "onCreate")
    }

    override fun onHandleIntent(p0: Intent?) {
        Log.d("SERVICE_TAG", "onHandleIntent")
        for (i in 0 until 5) {
            Thread.sleep(1000)
            Log.d("SERVICE_TAG", "Timer $i")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SERVICE_TAG", "onDestroy")
    }

    companion object {
        private const val NAME = "MyIntentService"
        fun newIntent(context: Context): Intent {
            return Intent(context, MyIntentService::class.java)
        }
    }
}
