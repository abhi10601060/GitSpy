package com.example.gitspy.utility

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitspy.database.TrackedRepoService
import com.example.gitspy.models.issues.Issue
import com.example.gitspy.network.GitSpyService

class StatsRepository(private val database : TrackedRepoService , private val api : GitSpyService) {

    var issues : LiveData<List<Issue>> = MutableLiveData()

    fun getSavedIssues(repoId : Long){
        issues = database.trackRepoDao().getSavedIssues(repoId)
    }


}