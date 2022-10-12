package com.example.gitspy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitspy.models.Item
import com.example.gitspy.models.RepoList
import com.example.gitspy.models.User
import com.example.gitspy.utility.Repository
import com.example.gitspy.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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


//    ***************************************************** Handling Tracking ****************************************************************

    fun addToTrack(item: Item){

        viewModelScope.launch{
            val job = launch(Dispatchers.IO){
                repository.addToTrack(item)
            }
            job.join()
            val job1 = launch(Dispatchers.IO) {
                repository.addIssues(item.owner.login , item.name , item.id)
            }
            job1.join()
            val job2 = launch(Dispatchers.IO){
                repository.addCommits(item.owner.login , item.name , item.id)
            }
            job2.join()
            val job3 = launch(Dispatchers.IO){
                repository.addReleases(item.owner.login , item.name , item.id)
            }
            job3.join()
            val job4 = launch(Dispatchers.IO){
                repository.addPullrequests(item.owner.login , item.name , item.id)
            }
            job4.join()
        }
    }

    val trackedRepos : LiveData<List<Item>>
    get() = repository.trackedRepos
    init {
        getAllTrackedRepos()
    }
    fun getAllTrackedRepos(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllTrackedRepos()
        }
    }

    fun deleteRepo(item: Item){
        viewModelScope.launch {
            val job = viewModelScope.launch(Dispatchers.IO) {
                repository.deleteRepo(item)
            }
            job.join()
            val job1 = viewModelScope.launch(Dispatchers.IO){
                repository.deleteIssues(item.id)
            }
            job1.join()
            val job2 = launch(Dispatchers.IO){
                repository.deleteCommits(item.id)
            }
            job2.join()
            val job3 = launch(Dispatchers.IO){
                repository.deleteReleases(item.id)
            }
            job3.join()
            val job4 = launch(Dispatchers.IO){
                repository.deletePullRequests(item.id)
            }
            job4.join()
        }
    }


//    ***************************************************** Handling Issues Events ****************************************************************

    fun addIssueEvents(repos : List<Item>){
        viewModelScope.launch(Dispatchers.IO){
            for(repo in repos){
                repository.addIssueEvents(repo.owner.login , repo.name , repo.id)
            }
        }
    }
}