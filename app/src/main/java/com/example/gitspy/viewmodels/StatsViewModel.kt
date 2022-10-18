package com.example.gitspy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitspy.models.issues.Issue
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

}