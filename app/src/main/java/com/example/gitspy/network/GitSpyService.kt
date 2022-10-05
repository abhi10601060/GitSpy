package com.example.gitspy.network

import com.example.gitspy.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitSpyService {

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username : String) : Response<User>


}