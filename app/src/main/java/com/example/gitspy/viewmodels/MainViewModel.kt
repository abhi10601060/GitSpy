package com.example.gitspy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitspy.models.RepoList
import com.example.gitspy.models.User
import com.example.gitspy.utility.Repository
import com.example.gitspy.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val user : LiveData<Resource<User>>
    get() = repository.user

    fun getUser(name : String){
        viewModelScope.launch(Dispatchers.IO){
            repository.getUser(name)
        }
    }

//    ***************************************************** Handling Repos ****************************************************************

    val repoList : LiveData<Resource<RepoList>>
    get() = repository.repoList

    fun getRepos(repoName : String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRepos(repoName)
        }
    }


}