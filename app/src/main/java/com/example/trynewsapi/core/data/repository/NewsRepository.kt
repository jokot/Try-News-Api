package com.example.trynewsapi.core.data.repository

import com.example.trynewsapi.core.model.Article
import com.example.trynewsapi.core.network.NetworkState
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    val newsStream: Flow<NetworkState<List<Article>>>

    suspend fun refresh(country: String?)
}