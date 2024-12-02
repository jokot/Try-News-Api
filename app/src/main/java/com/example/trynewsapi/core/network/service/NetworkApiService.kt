package com.example.trynewsapi.core.network.service

import com.example.trynewsapi.core.network.model.NetworkArticle
import com.example.trynewsapi.core.network.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApiService {

    @GET(value = "v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String
    ): NetworkResponse<List<NetworkArticle>>

}