package com.example.gitspy.utility

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.SystemClock
import androidx.constraintlayout.widget.ConstraintSet
import androidx.work.*
import com.example.gitspy.database.TrackedRepoService
import com.example.gitspy.network.GitSpyService
import com.example.gitspy.network.RetroInstance
import com.example.gitspy.worker.BackgroundWorker
import java.util.concurrent.TimeUnit

class GitSpyApplication : Application() {
    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()
        initializeRepo()
        setupWorker()
    }

    private fun setupWorker() {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this , BackgroundWorker::class.java)

        val pendingIntent = PendingIntent.getBroadcast(applicationContext , 0 , intent , 0)

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP , System.currentTimeMillis() , 1*60*1000L , pendingIntent)
    }

    fun initializeRepo() {
        val gitSpyService = RetroInstance.getInstance().create(GitSpyService::class.java)
        val database = TrackedRepoService.getInstance(this)
        repository = Repository(gitSpyService , database)
    }




}