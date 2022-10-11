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

//        viewModelScope.launch(Dispatchers.IO) {
//            repository.addToTrack(item)
//        }

        viewModelScope.launch{
            val job = launch(Dispatchers.IO){
                repository.addToTrack(item)
            }
            job.join()
            val job1 = launch(Dispatchers.IO) {
                addIssues(item.owner.login , item.name , item.id)
                // TODO: combine this to repository.addIssues and delete addIssue from VM 
            }
            job1.join()
            val job2 = launch(Dispatchers.IO){
                repository.addCommits(item.owner.login , item.name , item.id)
            }
            job2.join()
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
        }
    }


//    ***************************************************** Handling Issues ****************************************************************

    fun addIssues(owner : String , repoName : String , repoId : Long){
        viewModelScope.launch(Dispatchers.IO){
            repository.addIssues(owner , repoName , repoId)
        }
    }

    fun deleteIssues(repoId : Long){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteIssues(repoId)
        }
    }

}