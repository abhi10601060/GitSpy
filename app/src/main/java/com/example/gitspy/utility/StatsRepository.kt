package com.example.gitspy.utility

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitspy.database.TrackedRepoService
import com.example.gitspy.models.issues.Issue
import com.example.gitspy.models.issues.Issues
import com.example.gitspy.network.GitSpyService
import retrofit2.Response

class StatsRepository(private val database : TrackedRepoService , private val api : GitSpyService) {

    var issues : LiveData<List<Issue>> = MutableLiveData()

    fun getSavedIssues(repoId : Long){
        issues = database.trackRepoDao().getSavedIssues(repoId)
    }

    private var openIssuesLiveData = MutableLiveData<Resource<Issues>>()

    val OpenIssues : LiveData<Resource<Issues>>
    get() = openIssuesLiveData

    suspend fun getOpenIssues(fullName: String){
        openIssuesLiveData.postValue(Resource.Loading<Issues>())
        val response = api.getOpenIssues(fullName)
        openIssuesLiveData.postValue(handleIssue(response))
        Log.d("ABHI", "repo getOpenIssues: called ")
    }


    private fun handleIssue(response : Response<Issues>) : Resource<Issues>{
        if (response.body()!=null){
            return Resource.Success<Issues>(response.body()!!)
        }
        return Resource.Error<Issues>(response.message())
    }

}