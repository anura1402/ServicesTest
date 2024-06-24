package ru.anura.myapplication

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyJobService : JobService() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate() {
        super.onCreate()
        Log.d("SERVICE_TAG", "onCreate")

    }

    //возвращаемый тип - статус выполняемой работы сервиса(выполняется еще или нет).
    //При синхронной работе возвращаем false, при асинхронной - true, т.е. сами когда-то завершим
    override fun onStartJob(params: JobParameters?): Boolean {

        coroutineScope.launch {
            for (i in 0 until 10) {
                delay(1000)
                Log.d("SERVICE_TAG", "Timer $i")
            }
            jobFinished(params, true)
        }
        return true
    }

    //вызывается только когда система сама убивает сервис. Если был вызван jobFinished,
    //то этот метод не вызовится. Возвращается true, если нужно, что бы система запланировала
    //заново на выполнение этот сервис
    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d("SERVICE_TAG", "onStopJob")
        return true
    }

    companion object {
        const val JOB_ID = 10
        fun newIntent(context: Context): Intent {
            return Intent(context, MyService::class.java)
        }
    }
}