package com.pondoo.application.network

import com.pondoo.application.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {


    @GET("/v2/top-headlines")
    suspend fun getNews(@Query("country")country: String,
                        @Query("category")language: String,
                        @Query("apiKey") apiKey: String): Response<News>
}