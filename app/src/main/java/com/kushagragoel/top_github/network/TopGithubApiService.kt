package com.kushagragoel.top_github.network

import com.kushagragoel.top_github.network.model.TopGithubApiOutputBean
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://trendings.herokuapp.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL).build()

interface TopGithubApiService {
    @GET("repo")
    fun getRepo(@Query("lang") programLang: String,
                @Query("since") time: String = "weekly") : Call<TopGithubApiOutputBean>?
}

object TopGithubApi {
    val retrofitService : TopGithubApiService by lazy {
        retrofit.create(TopGithubApiService::class.java) }
}