package com.example.gitspy.utility

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitspy.database.TrackedRepoService
import com.example.gitspy.models.Item
import com.example.gitspy.models.RepoList
import com.example.gitspy.models.User
import com.example.gitspy.models.commits.CommitList
import com.example.gitspy.models.issues.Issues
import com.example.gitspy.network.GitSpyService
import retrofit2.Response

class Repository( private val gitSpyService: GitSpyService , private val database: TrackedRepoService){

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


//    ***************************************************** Handling Tracking ****************************************************************

    suspend fun addToTrack(item : Item){
        Log.d("ABHI", "addToTrack: Runned")
        database.trackRepoDao().trackRepo(item)
//        Log.d("ABHI", "addToTrack: $res responce!!!!!!")
//        Log.d("ABHI", "addToTrack: ${item.toString()} responce!!!!!!")
    }

    var trackedRepos : LiveData<List<Item>> = MutableLiveData()

    fun getAllTrackedRepos(){
        trackedRepos = database.trackRepoDao().getAllTrackedRepos()
    }

    suspend fun deleteRepo(item: Item){
        database.trackRepoDao().deleteRepo(item)
    }


    //  ***************************************************** Handling Issues ****************************************************************

    suspend fun addIssues(owner : String , repo : String , repoId : Long){
        val response = gitSpyService.getIssues(owner , repo)
        val issues = handleIssue(response)
        if (issues is Resource.Success){
            val issueList = issues.data
            if (issueList != null) {
                for(issue in issueList){
                    issue.repoId = repoId
                    Log.d("ISSUE", "addIssues: ${issue.toString()}")
                    database.trackRepoDao().addIssue(issue)
                }
            }
        }
    }

    private fun handleIssue(response : Response<Issues>) : Resource<Issues>{
        if (response.body()!=null){
            return Resource.Success<Issues>(response.body()!!)
        }
        return Resource.Error<Issues>(response.message())
    }

    suspend fun deleteIssues(repoId :Long){
        database.trackRepoDao().deleteIssues(repoId)
    }


    //  ***************************************************** Handling Commits ****************************************************************

    suspend fun addCommits(owner: String , repoName : String , repoId : Long){
        val response = gitSpyService.getCommits(owner , repoName)
        val commits = handleCommits(response)
        if (commits is Resource.Success){
            if (commits.data!=null){
                for(commit in commits.data){
                    commit.repoId= repoId
                    database.trackRepoDao().addCommit(commit)
                }
            }

        }
    }

    private fun handleCommits(response : Response<CommitList>) : Resource<CommitList>{
        if (response.body()!=null){
            return Resource.Success<CommitList>(response.body()!!)
        }
        return Resource.Error<CommitList>(response.message())
    }




}