package com.example.gitspy.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitspy.models.issues.Issue
import com.example.gitspy.models.issues.Issues
import com.example.gitspy.models.pulls.PullRequests
import com.example.gitspy.models.pulls.PullRequestsItem
import com.example.gitspy.utility.Resource
import com.example.gitspy.utility.StatsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatsViewModel(private val repository: StatsRepository) : ViewModel(){

    val issue : LiveData<List<Issue>>
    get() = repository.issues

    fun getAllIssues(repoId : Long){
        viewModelScope.launch(Dispatchers.IO){
            repository.getSavedIssues(repoId)
        }
    }

    val OpenIssues : LiveData<Resource<Issues>>
    get() = repository.OpenIssues

    fun getOpenIssues(owner :String , repoName : String){
        viewModelScope.launch(Dispatchers.IO){
            Log.d("ABHI", "getOpenIssues: called")
            repository.getOpenIssues(owner , repoName)

        }
    }

    val ClosedIssues : LiveData<Resource<Issues>>
    get() = repository.ClosedIssues

    fun getClosedIssues(owner :String , repoName : String){
        viewModelScope.launch(Dispatchers.IO){
            Log.d("ABHI", "getClosed : called")
            repository.getClosedIssues(owner , repoName)

        }
    }

    //******************************************************************** Pull Requests ****************************************************

    val savedPrNotifications : LiveData<List<PullRequestsItem>>
    get() = repository.prNotifications

    fun getAllSavedPrNotifications(repoId : Long){
        viewModelScope.launch(Dispatchers.IO){
            repository.getAllSavedPrs(repoId)
        }
    }

    val openPullRequests : LiveData<Resource<PullRequests>>
    get() = repository.openPullRequests

    fun getOpenPullRequest(owner : String , repoName : String){
        viewModelScope.launch(Dispatchers.IO){
            repository.getOpenPullrequests(owner , repoName)
        }
    }

    val closedPullRequests : LiveData<Resource<PullRequests>>
    get() = repository.closedPullRequests

    fun getClosedPullRequest(owner : String , repoName : String){
        viewModelScope.launch(Dispatchers.IO){
            repository.getClosedPullrequests(owner , repoName)
        }
    }

}