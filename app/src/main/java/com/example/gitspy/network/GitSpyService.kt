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

const val PUBLIC_ACCESS_TOKEN = "Bearer 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"
interface GitSpyService {

    @Headers("Authorization: $PUBLIC_ACCESS_TOKEN")
    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username : String) : Response<User>

    @Headers("Authorization: $PUBLIC_ACCESS_TOKEN")
    @GET("/search/repositories")
    suspend fun getRepos(@Query("q") q : String) : Response<RepoList>

    @Headers("Authorization: $PUBLIC_ACCESS_TOKEN")
    @GET("/repos/{owner}/{repoName}/issues?per_page=100")
    suspend fun getIssues(@Path("owner") owner: String , @Path("repoName") repoName : String) : Response<Issues>

    @Headers("Authorization: $PUBLIC_ACCESS_TOKEN")
    @GET("/repos/{owner}/{repoName}/commits?per_page=100")
    suspend fun getCommits(@Path("owner") owner: String , @Path("repoName") repoName: String) : Response<CommitList>

    @Headers("Authorization: $PUBLIC_ACCESS_TOKEN")
    @GET("/repos/{owner}/{repoName}/releases?per_page=100")
    suspend fun getReleases(@Path("owner") owner: String , @Path("repoName") repoName: String) : Response<Releases>

    @Headers("Authorization: $PUBLIC_ACCESS_TOKEN")
    @GET("/repos/{owner}/{repoName}/pulls?per_page=100")
    suspend fun getPullRequests(@Path("owner") owner: String , @Path("repoName") repoName: String) : Response<PullRequests>

    @Headers("Authorization: $PUBLIC_ACCESS_TOKEN")
    @GET("/repos/{owner}/{repoName}/issues/events?per_page=100")
    suspend fun getIssueEvents(@Path("owner") owner: String , @Path("repoName") repoName: String) : Response<IssueEvents>

    //********************************************************* Stats Repository *************************************************************


    @Headers("Authorization: $PUBLIC_ACCESS_TOKEN")
    @GET("/repos/{owner}/{repoName}/issues?state=closed")
    suspend fun getClosedIssues(@Path("owner") owner: String , @Path("repoName") repoName : String) : Response<Issues>

    @Headers("Authorization: $PUBLIC_ACCESS_TOKEN")
    @GET("/repos/{owner}/{repoName}/pulls?state=closed")
    suspend fun getClosedPullRequests(@Path("owner") owner: String , @Path("repoName") repoName : String) : Response<PullRequests>


    //********************************************************* Authorisation Requests *************************************************************

    @Headers("Accept: application/json")
    @POST("/login/oauth/access_token")
    @FormUrlEncoded
    fun getToken(@Field("client_id") id : String , @Field("client_secret") secret : String , @Field("code") code : String) : Call<AccessToken>

}