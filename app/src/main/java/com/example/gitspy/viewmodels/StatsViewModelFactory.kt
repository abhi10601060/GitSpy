package com.example.gitspy.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitspy.utility.StatsRepository

class StatsViewModelFactory(private val repository: StatsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StatsViewModel(repository) as T
    }
}