package com.example.gitspy.utility

import android.app.Application
import com.example.gitspy.database.TrackedRepoService
import com.example.gitspy.network.GitSpyService
import com.example.gitspy.network.RetroInstance

class GitSpyApplication : Application() {
    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()
        initializeRepo()
    }

    fun initializeRepo() {
        val gitSpyService = RetroInstance.getInstance().create(GitSpyService::class.java)
        val database = TrackedRepoService.getInstance(this)
        repository = Repository(gitSpyService , database)
    }
}