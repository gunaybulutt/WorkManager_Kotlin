package com.gunay.workmanager_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = Data.Builder().putInt("intKey",1).build()

        val constraints = Constraints.Builder()
            //.setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()

        val workRequest : WorkRequest = OneTimeWorkRequestBuilder<DatabaseUpdate>()
            .setConstraints(constraints)
            .setInputData(data)
            //.setInitialDelay(5,TimeUnit.MINUTES)
            //.addTag("firstRequest")
            .build()

        val workRequest2 : WorkRequest = PeriodicWorkRequestBuilder<DatabaseUpdate>(15,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(data)
            .build()


        //WorkManager.getInstance(this).enqueue(workRequest)
        WorkManager.getInstance(this).enqueue(workRequest2)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest2.id).observe(this,
            Observer {
                if (it.state == WorkInfo.State.RUNNING){
                    println("workRequest2 is working")
                }else if (it.state == WorkInfo.State.FAILED){
                    println("failed")
                }else if (it.state == WorkInfo.State.SUCCEEDED){
                    println("Succeeded")
                }
            })

        //WorkManager.getInstance(this).cancelAllWork()

        //Chaining
        /*
        val oneTimeWorkRequest: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<RefreshDatabase>()
                .setConstraints(constraints)
                .setInputData(data)
                //.setInitialDelay(5,TimeUnit.HOURS)
                //.addTag("myTag")
                .build()

         WorkManager.getInstance(this).beginWith(oneTimeWorkRequest)
             .then(oneTimeWorkRequest)
             .then(oneTimeWorkRequest)
             .enqueue()
         */

    }
}