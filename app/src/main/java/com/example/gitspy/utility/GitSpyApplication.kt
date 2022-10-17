package com.example.gitspy.utility

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.example.gitspy.database.TrackedRepoService
import com.example.gitspy.models.User
import com.example.gitspy.network.GitSpyService
import com.example.gitspy.network.RetroInstance
import com.example.gitspy.worker.BackgroundWorker


const val CHANNEL_ID = "Gitspy_Channel_ID"
const val CHANNEL_NAME = "Gitspy_Channel"

class GitSpyApplication : Application() {
    lateinit var repository: Repository


    override fun onCreate() {
        super.onCreate()
        initializeRepo()
        setupWorker()
        createNotificationChannel()
    }

    private fun setupWorker() {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this , BackgroundWorker::class.java)

        val pendingIntent = PendingIntent.getBroadcast(applicationContext , 0 , intent , 0)

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP , System.currentTimeMillis() , 10*60*1000L , pendingIntent)
    }

    fun initializeRepo() {
        val gitSpyService = RetroInstance.getInstance().create(GitSpyService::class.java)
        val database = TrackedRepoService.getInstance(this)
        repository = Repository(gitSpyService , database , baseContext)
    }

    fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID , CHANNEL_NAME , NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

    }
}