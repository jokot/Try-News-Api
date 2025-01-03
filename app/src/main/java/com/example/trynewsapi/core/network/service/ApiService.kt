package com.example.trynewsapi.core.network.service

import com.example.trynewsapi.core.network.model.ArticlesResponse
import com.example.trynewsapi.core.network.model.SourcesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 20,
        @Query("sources") sources: String? = null,
    ): Response<ArticlesResponse>

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("language") language: String = "en",
        @Query("sources") sources: String? = null
    ): Response<ArticlesResponse>

    @GET("v2/top-headlines/sources")
    suspend fun getSources(): Response<SourcesResponse>
}