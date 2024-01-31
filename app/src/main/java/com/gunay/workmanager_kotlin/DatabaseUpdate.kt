package com.gunay.workmanager_kotlin

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class DatabaseUpdate(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val getData = inputData
        val number = getData.getInt("intKey",0)
        updateDatabase(number)
        return Result.success()
    }

    private fun updateDatabase(number : Int){
        val sharedPreferences = context.getSharedPreferences("com.gunay.workmanager_kotlin",Context.MODE_PRIVATE)
        var savedNumber = sharedPreferences.getInt("number",0)
        savedNumber = savedNumber + number
        println(savedNumber)
        sharedPreferences.edit().putInt("number",savedNumber).apply()
    }
}