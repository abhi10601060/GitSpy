package com.example.gitspy.network

import com.example.gitspy.models.AccessToken
import com.example.gitspy.models.RepoList
import com.example.gitspy.models.User
import com.example.gitspy.models.commits.CommitList
import com.example.gitspy.models.issues.Issues
import com.example.gitspy.models.issues_events.IssueEvents
import com.example.gitspy.models.pulls.PullRequests
import com.example.gitspy.models.releases.Releases
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface GitSpyService {

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username : String) : Response<User>

    @GET("/search/repositories")
    suspend fun getRepos(@Query("q") q : String) : Response<RepoList>

    @GET("/repos/{owner}/{repoName}/issues?per_page=100")
    suspend fun getIssues(@Path("owner") owner: String , @Path("repoName") repoName : String) : Response<Issues>

    @GET("/repos/{owner}/{repoName}/commits?per_page=100")
    suspend fun getCommits(@Path("owner") owner: String , @Path("repoName") repoName: String) : Response<CommitList>

    @GET("/repos/{owner}/{repoName}/releases?per_page=100")
    suspend fun getReleases(@Path("owner") owner: String , @Path("repoName") repoName: String) : Response<Releases>

    @GET("/repos/{owner}/{repoName}/pulls?per_page=100")
    suspend fun getPullRequests(@Path("owner") owner: String , @Path("repoName") repoName: String) : Response<PullRequests>

    @GET("/repos/{owner}/{repoName}/issues/events?per_page=100")
    suspend fun getIssueEvents(@Path("owner") owner: String , @Path("repoName") repoName: String) : Response<IssueEvents>

    //********************************************************* Stats Repository *************************************************************


    @GET("/repos/{owner}/{repoName}/issues?state=closed")
    suspend fun getClosedIssues(@Path("owner") owner: String , @Path("repoName") repoName : String) : Response<Issues>

    @GET("/repos/{owner}/{repoName}/pulls?state=closed")
    suspend fun getClosedPullRequests(@Path("owner") owner: String , @Path("repoName") repoName : String) : Response<PullRequests>


    //********************************************************* Authorisation Requests *************************************************************

    @Headers("Accept: application/json")
    @POST("/login/oauth/access_token")
    @FormUrlEncoded
    fun getToken(@Field("client_id") id : String , @Field("client_secret") secret : String , @Field("code") code : String) : Call<AccessToken>

}