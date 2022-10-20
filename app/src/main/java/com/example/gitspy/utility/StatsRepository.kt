package com.example.gitspy.utility

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitspy.database.TrackedRepoService
import com.example.gitspy.models.commits.CommitList
import com.example.gitspy.models.issues.Issue
import com.example.gitspy.models.issues.Issues
import com.example.gitspy.models.pulls.PullRequests
import com.example.gitspy.models.pulls.PullRequestsItem
import com.example.gitspy.models.releases.ReleaseItem
import com.example.gitspy.models.releases.Releases
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

    suspend fun getOpenIssues(owner: String , repoName : String){
        openIssuesLiveData.postValue(Resource.Loading<Issues>())
        val response = api.getIssues(owner , repoName)
        openIssuesLiveData.postValue(handleIssue(response))
        Log.d("ABHI", "repo getOpenIssues: called ")
    }


    private fun handleIssue(response : Response<Issues>) : Resource<Issues>{
        if (response.body()!=null){
            return Resource.Success<Issues>(response.body()!!)
        }
        return Resource.Error<Issues>(response.message())
    }

    private var closedIssuesLiveData = MutableLiveData<Resource<Issues>>()

    val ClosedIssues : LiveData<Resource<Issues>>
    get() = closedIssuesLiveData

    suspend fun getClosedIssues(owner: String , repoName : String){
        closedIssuesLiveData.postValue(Resource.Loading<Issues>())
        val response = api.getClosedIssues(owner , repoName)
        closedIssuesLiveData.postValue(handleIssue(response))
        Log.d("ABHI", "repo getClosed : called ")
    }


    //******************************************************************** Pull Requests ****************************************************

    var prNotifications : LiveData<List<PullRequestsItem>> = MutableLiveData()

    suspend fun getAllSavedPrs(repoId : Long){
        prNotifications = database.trackRepoDao().getSavedPrs(repoId)
    }

    private var openPrLivedata  = MutableLiveData<Resource<PullRequests>>()

    val openPullRequests : LiveData<Resource<PullRequests>>
    get() = openPrLivedata

    suspend fun getOpenPullrequests(owner: String , repoName : String){
        val response = api.getPullRequests(owner , repoName)
        openPrLivedata.postValue(handlePulls(response))
    }

    private fun handlePulls(response : Response<PullRequests>) : Resource<PullRequests>{
        if (response.body()!=null){
            return Resource.Success<PullRequests>(response.body()!!)
        }
        return Resource.Error<PullRequests>(response.message())
    }

    private var closedPrLivedata  = MutableLiveData<Resource<PullRequests>>()

    val closedPullRequests : LiveData<Resource<PullRequests>>
    get() = closedPrLivedata

    suspend fun getClosedPullrequests(owner: String , repoName : String){
        val response = api.getClosedPullRequests(owner , repoName)
        closedPrLivedata.postValue(handlePulls(response))
    }

    //******************************************************************** Commits ****************************************************

    private var commitsLivedata = MutableLiveData<Resource<CommitList>>()

    val commits : LiveData<Resource<CommitList>>
    get() = commitsLivedata

    suspend fun getAllCommits(owner: String , repoName : String){
        val response = api.getCommits(owner , repoName)
        commitsLivedata.postValue(handleCommits(response))
    }

    private fun handleCommits(response : Response<CommitList>) : Resource<CommitList>{
        if (response.body()!=null){
            return Resource.Success<CommitList>(response.body()!!)
        }
        return Resource.Error<CommitList>(response.message())
    }


    //******************************************************************** releases ****************************************************

    private var releasesLivedata = MutableLiveData<Resource<Releases>>()

    val releases : LiveData<Resource<Releases>>
        get() = releasesLivedata

    suspend fun getAllReleases(owner: String , repoName : String){
        val response = api.getReleases(owner , repoName)
        releasesLivedata.postValue(handleReleases(response))
    }

    private fun handleReleases(response : Response<Releases>) : Resource<Releases>{
        if (response.body()!=null){
            return Resource.Success<Releases>(response.body()!!)
        }
        return Resource.Error<Releases>(response.message())
    }
}