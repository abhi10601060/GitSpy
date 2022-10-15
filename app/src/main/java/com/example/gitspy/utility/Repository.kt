package com.example.gitspy.utility

import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitspy.R
import com.example.gitspy.database.TrackedRepoService
import com.example.gitspy.models.Item
import com.example.gitspy.models.RepoList
import com.example.gitspy.models.User
import com.example.gitspy.models.commits.CommitList
import com.example.gitspy.models.commits.CommitListItem
import com.example.gitspy.models.issues.Issue
import com.example.gitspy.models.issues.Issues
import com.example.gitspy.models.issues_events.IssueEvents
import com.example.gitspy.models.issues_events.IssueEventsItem
import com.example.gitspy.models.pulls.PullRequests
import com.example.gitspy.models.pulls.PullRequestsItem
import com.example.gitspy.models.releases.ReleaseItem
import com.example.gitspy.models.releases.Releases
import com.example.gitspy.network.GitSpyService
import com.example.gitspy.ui.activities.MainActivity
import kotlinx.coroutines.*
import retrofit2.Response

class Repository( private val gitSpyService: GitSpyService , private val database: TrackedRepoService , private val  context: Context){

    private var userLivedata  = MutableLiveData<Resource<User>>()

    val user : LiveData<Resource<User>>
    get() = userLivedata

    suspend fun getUser(userName : String){
        userLivedata.postValue(Resource.Loading())
        var response = gitSpyService.getUser(userName)
        val res = handleUser(response)
        userLivedata.postValue(res)
        if (res is Resource.Success){
            CoroutineScope(Dispatchers.Main).launch {
                res.data?.let { showUserNotification(it) }
            }
        }
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

    suspend fun addIssues(owner : String , repo : String , repoId : Long ){
        val response = gitSpyService.getIssues(owner , repo)
        val issues = handleIssue(response)
        if (issues is Resource.Success){
            val issueList = issues.data
            if (issueList != null) {
                for(issue in issueList){
                    issue.repoId = repoId
                    issue.repoName = repo
                    Log.d("ISSUE", "addIssues: ${issue.toString()}")
                    val ret = database.trackRepoDao().addIssue(issue)
                    if (ret != -1L){
                        database.trackRepoDao().incrementIssueEventCounts(repoId)
                    }
                    else{
                        break
                    }
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
                    commit.repoName = repoName
                    val ret = database.trackRepoDao().addCommit(commit)
                    if (ret != -1L){
                        database.trackRepoDao().incrementCommitsCount(repoId)
                    }
                    else{
                        break
                    }
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

    suspend fun deleteCommits(repoId : Long){
        database.trackRepoDao().deleteCommits(repoId)
    }

//  ***************************************************** Handling Releases ****************************************************************

    suspend fun addReleases(owner: String , repoName : String , repoId : Long){
        val response = gitSpyService.getReleases(owner , repoName)
        val res = handleReleases(response)
        if (res is Resource.Success){
            val releases = res.data
            if (releases!=null){
                for(release in releases){
                    release.repoId= repoId
                    release.repoName = repoName
                    val ret = database.trackRepoDao().addRelease(release)
                    if (ret != -1L){
                        database.trackRepoDao().incrementReleasesCount(repoId)
                    }
                    else{
                        break
                    }
                }
            }
        }

    }

    private fun handleReleases(response : Response<Releases>) : Resource<Releases>{
        if (response.body()!=null){
            return Resource.Success<Releases>(response.body()!!)
        }
        return Resource.Error<Releases>(response.message())
    }

    suspend fun deleteReleases(repoId : Long){
        database.trackRepoDao().deleteReleases(repoId)
    }

    //  ***************************************************** Handling PR's ****************************************************************

    suspend fun addPullrequests(owner: String , repoName : String , repoId : Long){
        val response = gitSpyService.getPullRequests(owner , repoName)
        val res = handlePulls(response)
        if (res is Resource.Success){
            val prs = res.data
            if (prs!=null){
                for(pr in prs){
                    pr.repoId= repoId
                    pr.repoName = repoName
                    val ret = database.trackRepoDao().addPullRequest(pr)
                    if (ret != -1L){
                        database.trackRepoDao().incrementPullRequestsCount(repoId)
                    }
                    else{
                        break
                    }
                }
            }
        }

    }

    private fun handlePulls(response : Response<PullRequests>) : Resource<PullRequests>{
        if (response.body()!=null){
            return Resource.Success<PullRequests>(response.body()!!)
        }
        return Resource.Error<PullRequests>(response.message())
    }

    suspend fun deletePullRequests(repoId : Long){
        database.trackRepoDao().deletePullRequests(repoId)
    }


    //  ***************************************************** Handling Issues Events ****************************************************************


    suspend fun addIssueEvents(owner: String , repoName : String , repoId : Long){
        val response = gitSpyService.getIssueEvents(owner , repoName)
        val res = handleIssueEvents(response)
        if (res is Resource.Success){
            val issueEvents = res.data
            if (issueEvents!=null){
                for(issueEvent in issueEvents){
                    issueEvent.repoId= repoId
                    issueEvent.repoName = repoName
                    val ret = database.trackRepoDao().addIssueEvent(issueEvent)
                    Log.d("ABHI", "addIssueEvents: ret value is $ret ")
                    if (ret != -1L){
                        database.trackRepoDao().incrementIssueEventCounts(repoId)
                    }
                    else{
                        break
                    }
                }
            }
        }

    }

    private fun handleIssueEvents(response : Response<IssueEvents>) : Resource<IssueEvents>{
        if (response.body()!=null){
            return Resource.Success<IssueEvents>(response.body()!!)
        }
        return Resource.Error<IssueEvents>(response.message())
    }

    suspend fun deleteIssueEvents(repoId : Long){
        database.trackRepoDao().deleteIssueEvents(repoId)
    }


    //  ***************************************************** Handling background worker ****************************************************************

    suspend fun addIssuesBackground(owner : String , repo : String , repoId : Long){
        val response = gitSpyService.getIssues(owner , repo)
        val issues = handleIssue(response)
        if (issues is Resource.Success){
            val issueList = issues.data
            if (issueList != null) {
                for(issue in issueList){
                    issue.repoId = repoId
                    issue.repoName = repo
                    Log.d("ISSUE", "addIssues: ${issue.toString()}")
                    val ret = database.trackRepoDao().addIssue(issue)
                    Log.d("ABHI", "addIssuesBackground: this is ret $ret")
                    if (ret != -1L){
                        Log.d("ABHI", "addIssuesBackground:  isuue added in background")
                        database.trackRepoDao().incrementUnseenIssueEventCounts(repoId)
                        CoroutineScope(Dispatchers.Main).launch{
                            showIssueNotification(issue)
                        }
                    }
                    else{
                        break
                    }
                }
            }
        }
    }

    suspend fun addIssueEventsBackground(owner: String , repoName : String , repoId : Long){
        val response = gitSpyService.getIssueEvents(owner , repoName)
        val res = handleIssueEvents(response)
        if (res is Resource.Success){
            val issueEvents = res.data
            if (issueEvents!=null){
                for(issueEvent in issueEvents){
                    issueEvent.repoId= repoId
                    issueEvent.repoName = repoName
                    val ret = database.trackRepoDao().addIssueEvent(issueEvent)
                    Log.d("ABHI", "addIssueEventsBackground: ${issueEvent.toString()}")
                    Log.d("ABHI", "addIssueEvents: ret value is $ret ")
                    if (ret != -1L){
                        database.trackRepoDao().incrementUnseenIssueEventCounts(repoId)
                        CoroutineScope(Dispatchers.Main).launch {
                            showIssueEventNotification(issueEvent)
                        }
                    }
                    else{
                        break
                    }
                }
            }
        }
    }

    suspend fun addCommitsBackground(owner: String , repoName : String , repoId : Long){
        val response = gitSpyService.getCommits(owner , repoName)
        val commits = handleCommits(response)
        if (commits is Resource.Success){
            if (commits.data!=null){
                for(commit in commits.data){
                    commit.repoId= repoId
                    commit.repoName = repoName
                    val ret = database.trackRepoDao().addCommit(commit)
                    if (ret != -1L){
                        database.trackRepoDao().incrementUnseenCommitsCount(repoId)
                        CoroutineScope(Dispatchers.Main).launch{
                            showCommitsNotification(commit)
                        }
                    }
                    else{
                        break
                    }
                }
            }

        }
    }

    suspend fun addReleasesBackground(owner: String , repoName : String , repoId : Long){
        val response = gitSpyService.getReleases(owner , repoName)
        val res = handleReleases(response)
        if (res is Resource.Success){
            val releases = res.data
            if (releases!=null){
                for(release in releases){
                    release.repoId= repoId
                    release.repoName = repoName
                    val ret = database.trackRepoDao().addRelease(release)
                    if (ret != -1L){
                        database.trackRepoDao().incrementUnseenReleasesCount(repoId)
                        CoroutineScope(Dispatchers.Main).launch{
                            showReleaseNotification(release)
                        }
                    }
                    else{
                        break
                    }
                }
            }
        }

    }

    suspend fun addPullRequestsBackground(owner: String , repoName : String , repoId : Long){
        val response = gitSpyService.getPullRequests(owner , repoName)
        val res = handlePulls(response)
        if (res is Resource.Success){
            val prs = res.data
            if (prs!=null){
                for(pr in prs){
                    pr.repoId= repoId
                    pr.repoName = repoName
                    val ret = database.trackRepoDao().addPullRequest(pr)
                    if (ret != -1L){
                        database.trackRepoDao().incrementUnseenPullRequestsCount(repoId)
                        CoroutineScope(Dispatchers.Main).launch{
                            showPullRequestNotification(pr)
                        }
                    }
                    else{
                        break
                    }
                }
            }
        }

    }

    suspend fun getTrackedRepoList() : List<Item>{
        return database.trackRepoDao().getAllTrackedReposList()
    }


    //  ***************************************************** Handling Notifications ****************************************************************

    private var notificationCount = 0

    private fun showUserNotification(body: User) {
        val notification = NotificationCompat.Builder(context , CHANNEL_ID)
            .setContentTitle("User Found")
            .setContentText("${body.login} is found successfully...")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationCount++ , notification)

    }

    private fun showIssueNotification(issue : Issue){

        val notification = NotificationCompat.Builder(context , CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New Issue Created")
            .setContentText("${issue.title} issue created in ${issue.repoName}.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationCount++ , notification)
    }

    private fun showCommitsNotification(commit : CommitListItem){
        val notification = NotificationCompat.Builder(context , CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New Commit Pushed")
            .setContentText("${commit.commit.committer.name} commited ${commit.commit.message} on ${commit.repoName}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationCount++ , notification)
    }

    private fun showReleaseNotification(release : ReleaseItem){
        val notification = NotificationCompat.Builder(context , CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New Release")
            .setContentText("${release.repoName} Published ${release.name}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationCount++ , notification)
    }

    private fun showPullRequestNotification(pr : PullRequestsItem){
        val notification = NotificationCompat.Builder(context , CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New pull request created")
            .setContentText("${pr.title} : pull request created on ${pr.repoName} ")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationCount++ , notification)
    }

    private fun showIssueEventNotification(issueEvent: IssueEventsItem){
        var text = "${issueEvent.repoName} : ${issueEvent.issue.title} is ${issueEvent.event} by ${issueEvent.actor.login}"

        when(issueEvent.event){
            "labeled" -> text = "${issueEvent.repoName} : ${issueEvent.issue.title} is labeled by ${issueEvent.actor.login}."
            "closed" ->  text = "${issueEvent.repoName} : ${issueEvent.issue.title} is closed by ${issueEvent.actor.login}"
            "renamed" -> text = "${issueEvent.repoName} : ${issueEvent.issue.title} is renamed by ${issueEvent.actor.login}."
            "referenced" -> text = "${issueEvent.repoName} : ${issueEvent.issue.title} is referenced by ${issueEvent.actor.login}."
            "merged" -> text = "${issueEvent.repoName} : ${issueEvent.issue.title} is merged by ${issueEvent.actor.login}."
        }

        val notification = NotificationCompat.Builder(context , CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Issue Updated")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationCount++ , notification)
    }

}