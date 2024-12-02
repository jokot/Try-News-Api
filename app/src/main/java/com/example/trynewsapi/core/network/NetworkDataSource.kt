package com.example.trynewsapi.core.network

import com.example.trynewsapi.core.network.model.NetworkArticle
import com.example.trynewsapi.core.network.service.NetworkApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

const val TNA_BASE_URL = "https://newsapi.org"
private const val TNA_API_KEY = "646bc103e5d04c4699153a003a369674"

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkDataSource @Inject constructor(
    networkApi: NetworkApiService
) {
    // A MutableSharedFlow to act as a trigger for refreshes
    private val refreshTrigger = MutableSharedFlow<String?>(replay = 0)

    val newsStream: Flow<NetworkState<List<NetworkArticle>>> = refreshTrigger
        .onStart { emit("us") }
        .flatMapLatest { country ->
            flow {
                emit(NetworkState.Loading)
                try {
                    val latestNews = networkApi.getTopHeadlines(
                        country = country.orEmpty().ifEmpty { "us" },
                        apiKey = TNA_API_KEY
                    ).articles.orEmpty()
                    emit(NetworkState.Success(latestNews))
                } catch (e: Exception) {
                    emit(NetworkState.Error(e))
                }
            }
        }

    suspend fun refresh(country: String?) {
        refreshTrigger.emit(country)
    }
}