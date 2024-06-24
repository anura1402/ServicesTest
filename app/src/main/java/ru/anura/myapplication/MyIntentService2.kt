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

class MyIntentService2 : IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        Log.d("SERVICE_TAG", "onCreate")
        setIntentRedelivery(true)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d("SERVICE_TAG", "onHandleIntent")
        val page = intent?.getIntExtra(PAGE,0)?: 0
        for (i in 0 until 5) {
            Thread.sleep(1000)
            Log.d("SERVICE_TAG", "Page ${page}: Timer $i ")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("SERVICE_TAG", "onDestroy")
    }

    companion object {
        private const val NAME = "MyIntentService"
        private const val PAGE = "page"
        fun newIntent(context: Context, page:Int): Intent {
            return Intent(context, MyIntentService2::class.java).apply {
                putExtra(PAGE,page)
            }
        }
    }
}
