package com.example.gitspy.utility

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

}