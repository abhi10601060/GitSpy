package com.example.gitspy.network

import com.example.gitspy.models.RepoList
import com.example.gitspy.models.User
import com.example.gitspy.models.commits.CommitList
import com.example.gitspy.models.issues.Issues
import com.example.gitspy.models.issues_events.IssueEvents
import com.example.gitspy.models.pulls.PullRequests
import com.example.gitspy.models.releases.Releases
import retrofit2.Response
import retrofit2.http.*

interface GitSpyService {

    @Headers("Authorization: Bearer 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")
    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username : String) : Response<User>

    @Headers("Authorization: Bearer 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")
    @GET("/search/repositories")
    suspend fun getRepos(@Query("q") q : String) : Response<RepoList>

    @Headers("Authorization: Bearer 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")
    @GET("/repos/{owner}/{repoName}/issues?per_page=100")
    suspend fun getIssues(@Path("owner") owner: String , @Path("repoName") repoName : String) : Response<Issues>

    @Headers("Authorization: Bearer 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")
    @GET("/repos/{owner}/{repoName}/commits?per_page=100")
    suspend fun getCommits(@Path("owner") owner: String , @Path("repoName") repoName: String) : Response<CommitList>

    @Headers("Authorization: Bearer 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")
    @GET("/repos/{owner}/{repoName}/releases?per_page=100")
    suspend fun getReleases(@Path("owner") owner: String , @Path("repoName") repoName: String) : Response<Releases>

    @Headers("Authorization: Bearer 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")
    @GET("/repos/{owner}/{repoName}/pulls?per_page=100")
    suspend fun getPullRequests(@Path("owner") owner: String , @Path("repoName") repoName: String) : Response<PullRequests>

    @Headers("Authorization: Bearer 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")
    @GET("/repos/{owner}/{repoName}/issues/events?per_page=100")
    suspend fun getIssueEvents(@Path("owner") owner: String , @Path("repoName") repoName: String) : Response<IssueEvents>
}