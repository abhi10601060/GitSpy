package com.example.gitspy.network

import com.example.gitspy.models.RepoList
import com.example.gitspy.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitSpyService {

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username : String) : Response<User>

    @GET("/search/repositories")
    suspend fun getRepos(@Query("q") q : String) : Response<RepoList>
}