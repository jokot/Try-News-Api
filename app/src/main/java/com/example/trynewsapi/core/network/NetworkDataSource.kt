package com.example.trynewsapi.core.network

import com.example.trynewsapi.core.network.model.ArticlesResponse
import com.example.trynewsapi.core.network.model.SourcesResponse
import com.example.trynewsapi.core.network.service.ApiService
import retrofit2.Response
import javax.inject.Inject

interface NetworkDataSource {
    suspend fun getNews(): ApiResult<ArticlesResponse>
    suspend fun getFollowingNews(sources: String): ApiResult<ArticlesResponse>
    suspend fun getSources(): ApiResult<SourcesResponse>
    suspend fun getHeadlines(pageSize: Int): ApiResult<ArticlesResponse>
}

class NetworkDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : NetworkDataSource {
    override suspend fun getNews(): ApiResult<ArticlesResponse> =
        apiCall { apiService.getNews() }

    override suspend fun getFollowingNews(sources: String): ApiResult<ArticlesResponse> =
        apiCall { apiService.getNews(sources = sources) }

    override suspend fun getSources(): ApiResult<SourcesResponse> =
        apiCall { apiService.getSources() }

    override suspend fun getHeadlines(pageSize: Int): ApiResult<ArticlesResponse> =
        apiCall { apiService.getHeadlines(pageSize = pageSize) }
}

suspend fun <T> apiCall(
    call: suspend () -> Response<T>
): ApiResult<T> {
    return try {
        val response = call()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ApiResult.Success(body)
        } else {
            ApiResult.Error(response.message().orEmpty())
        }
    } catch (e: Exception) {
        ApiResult.Error(e.localizedMessage.orEmpty())
    }
}