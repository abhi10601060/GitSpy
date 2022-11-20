package com.example.gitspy.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroInstance {
    private const val BASE_URL = "https://api.github.com/"
    private var ACCESS_TOKEN : String = ""

    fun setAccessToken(token : String){
        ACCESS_TOKEN = token
    }

    fun getInstance() : Retrofit{
        var interceptor = object  : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request().newBuilder()
                request.addHeader("Authorization" , "Bearer $ACCESS_TOKEN")
                return chain.proceed(request.build())
            }

        }
        var httpclient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpclient)
            .build()
    }

    fun getGithubInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}