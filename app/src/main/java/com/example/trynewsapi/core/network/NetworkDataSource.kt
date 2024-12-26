package com.example.trynewsapi.core.network

import com.example.trynewsapi.core.network.model.ArticlesResponse
import com.example.trynewsapi.core.network.model.SourcesResponse
import com.example.trynewsapi.core.network.service.ApiService
import retrofit2.Response
import javax.inject.Inject

interface NetworkDataSource {
    suspend fun getNews(
        q: String,
        page: Int,
        pageSize: Int,
        sources: String
    ): ApiResult<ArticlesResponse>

    suspend fun getSources(): ApiResult<SourcesResponse>
    suspend fun getHeadlines(sources: String): ApiResult<ArticlesResponse>
}

class NetworkDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : NetworkDataSource {
    override suspend fun getNews(
        q: String,
        page: Int,
        pageSize: Int,
        sources: String
    ): ApiResult<ArticlesResponse> =
        apiCall {
            apiService.searchNews(
                q = q,
                page = page,
                pageSize = pageSize,
                sources = sources
            )
        }

    override suspend fun getSources(): ApiResult<SourcesResponse> =
        apiCall { apiService.getSources() }

    override suspend fun getHeadlines(sources: String): ApiResult<ArticlesResponse> =
        apiCall { apiService.getHeadlines(sources = sources) }
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