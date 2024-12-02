package com.example.trynewsapi.core.testing.repository

import com.example.trynewsapi.core.data.repository.NewsRepository
import com.example.trynewsapi.core.model.Article
import com.example.trynewsapi.core.network.NetworkState
import com.example.trynewsapi.core.network.model.NetworkArticle
import com.example.trynewsapi.core.network.model.asExternalModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class TestNewsRepository : NewsRepository {

    /**
     * The backing hot flow for the list of topics ids for testing.
     */
    private val newsResourcesFlow: MutableSharedFlow<NetworkState<List<NetworkArticle>>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)


    override val newsStream: Flow<NetworkState<List<Article>>> =
        newsResourcesFlow.map {
            when (it) {
                is NetworkState.Loading -> it
                is NetworkState.Success -> NetworkState.Success(it.data.map(NetworkArticle::asExternalModel))
                is NetworkState.Error -> it
            }
        }

    override suspend fun refresh(country: String?) {
        newsResourcesFlow.tryEmit(newsResourcesFlow.replayCache.first())
    }

    /**
     * A test-only API to allow controlling the list of news resources from tests.
     */
    fun sendNewsResources(newsResources: NetworkState<List<NetworkArticle>>) {
        newsResourcesFlow.tryEmit(newsResources)
    }
}