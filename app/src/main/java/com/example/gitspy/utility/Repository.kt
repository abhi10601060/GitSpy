package com.example.gitspy.utility

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitspy.models.RepoList
import com.example.gitspy.models.User
import com.example.gitspy.network.GitSpyService
import retrofit2.Response

class Repository( private val gitSpyService: GitSpyService){

    private var userLivedata  = MutableLiveData<Resource<User>>()

    val user : LiveData<Resource<User>>
    get() = userLivedata

    suspend fun getUser(userName : String){
        userLivedata.postValue(Resource.Loading())
        var response = gitSpyService.getUser(userName)
        userLivedata.postValue(handleUser(response))
    }

    fun handleUser(response : Response<User>) : Resource<User>{
        if (response.body()!=null){
            return Resource.Success(response.body()!!)
        }
        return Resource.Error(response.message())
    }

//    ***************************************************** Handling Repos ****************************************************************

    private val repoListLiveData = MutableLiveData<Resource<RepoList>>()

    val repoList : LiveData<Resource<RepoList>>
    get() = repoListLiveData

    suspend fun getRepos(name : String){
        repoListLiveData.postValue(Resource.Loading<RepoList>())
        val response = gitSpyService.getRepos(name)
        repoListLiveData.postValue(handleRepo(response))
    }

    private fun handleRepo(response: Response<RepoList>): Resource<RepoList> {
        if (response.body()!=null){
            return Resource.Success<RepoList>(response.body()!!)
        }
        return Resource.Error<RepoList>(response.message())
    }

}