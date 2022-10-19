package com.example.gitspy.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitspy.models.issues.Issue
import com.example.gitspy.models.issues.Issues
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
}