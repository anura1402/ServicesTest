package ru.anura.myapplication

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService: Service() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate() {
        super.onCreate()
        Log.d("SERVICE_TAG","onCreate")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        coroutineScope.launch {
            for (i in 0 until 100){
                delay(1000)
                Log.d("SERVICE_TAG","Timer $i")
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        Log.d("SERVICE_TAG","onDestroy")
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
    companion object{
        fun newIntent(context: Context):Intent{
            return Intent(context,MyService::class.java)
        }
    }
}