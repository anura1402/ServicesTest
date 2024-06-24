package ru.anura.myapplication

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.Build
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
        Log.d("SERVICE_TAG", "onStartJob")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            coroutineScope.launch {
                var workItem = params?.dequeueWork()
                while (workItem != null) {
                    val page = workItem.intent.getIntExtra(PAGE,0)
                    for (i in 0 until 5) {
                        delay(1000)
                        Log.d("SERVICE_TAG", "Page ${page}: Timer $i ")
                    }
                    params?.completeWork(workItem)
                    workItem = params?.dequeueWork()
                }
                jobFinished(params, false)
            }
        }
        return true
    }

    //вызывается только когда система сама убивает сервис. Если был вызван jobFinished,
    //то этот метод не вызовится. Возвращается true, если нужно, что бы система запланировала
    //заново на выполнение этот сервис
    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d("SERVICE_TAG", "onStopJob")
        coroutineScope.cancel()
        return true
    }

    companion object {
        const val JOB_ID = 10
        private const val PAGE = "page"
        fun newIntent(page: Int): Intent {
            return Intent().apply {
                putExtra(PAGE, page)
            }
        }
    }
}