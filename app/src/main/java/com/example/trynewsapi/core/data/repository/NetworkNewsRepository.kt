package com.example.trynewsapi.core.data.repository

import com.example.trynewsapi.core.model.Article
import com.example.trynewsapi.core.network.NetworkDataSource
import com.example.trynewsapi.core.network.NetworkState
import com.example.trynewsapi.core.network.model.NetworkArticle
import com.example.trynewsapi.core.network.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NetworkNewsRepository @Inject constructor(
    private val network: NetworkDataSource
) : NewsRepository {

    override val newsStream: Flow<NetworkState<List<Article>>> =
        network.newsStream.map {
            when (it) {
                is NetworkState.Loading -> it
                is NetworkState.Success -> NetworkState.Success(it.data.map(NetworkArticle::asExternalModel))
                is NetworkState.Error -> it
            }
        }

    override suspend fun refresh(country: String?) = network.refresh(country)
}