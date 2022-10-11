package com.example.gitspy.network

import com.example.gitspy.models.RepoList
import com.example.gitspy.models.User
import com.example.gitspy.models.issues.Issues
import retrofit2.Response
import retrofit2.http.*

interface GitSpyService {

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username : String) : Response<User>

    @GET("/search/repositories")
    suspend fun getRepos(@Query("q") q : String) : Response<RepoList>


    @GET("/repos/{owner}/{repoName}/issues")
    suspend fun getIssues(@Path("owner") owner: String , @Path("repoName") repoName : String) : Response<Issues>
}