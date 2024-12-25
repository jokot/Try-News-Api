package com.example.trynewsapi.core.data.repository

import com.example.trynewsapi.core.data.state.DataState
import com.example.trynewsapi.core.datastore.LocalDataSource
import com.example.trynewsapi.core.model.Article
import com.example.trynewsapi.core.model.Source
import com.example.trynewsapi.core.network.ApiResult
import com.example.trynewsapi.core.network.NetworkDataSource
import com.example.trynewsapi.core.network.model.NetworkArticle
import com.example.trynewsapi.core.network.model.NetworkSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

interface NewsRepository {
    fun getNews(): Flow<DataState<List<Article>>>
    fun getHeadlines(pageSize: Int): Flow<DataState<List<Article>>>
    fun getFollowingNews(): Flow<DataState<List<Article>>>
    fun getSources(): Flow<DataState<List<Source>>>
    suspend fun toggleFollowing(sourceId: String, isFollowing: Boolean)
}

class NewsRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) : NewsRepository {

    override fun getNews(): Flow<DataState<List<Article>>> = flow {
        emit(DataState.Loading)
        emit(
            when (val result = networkDataSource.getNews()) {
                is ApiResult.Error -> DataState.Error(result.message)
                is ApiResult.Success -> DataState.Success(
                    data = result.data.articles?.map(NetworkArticle::toDomain).orEmpty()
                )
            }
        )
    }.catch { e -> DataState.Error(e.localizedMessage.orEmpty()) }

    override fun getHeadlines(pageSize: Int): Flow<DataState<List<Article>>> = flow {
        emit(DataState.Loading)
        emit(
            when (val result = networkDataSource.getHeadlines(pageSize)) {
                is ApiResult.Error -> DataState.Error(result.message)
                is ApiResult.Success -> DataState.Success(
                    data = result.data.articles?.map(NetworkArticle::toDomain).orEmpty()
                )
            }
        )
    }.catch { e -> DataState.Error(e.localizedMessage.orEmpty()) }

    override fun getFollowingNews(): Flow<DataState<List<Article>>> = flow {
        emit(DataState.Loading)
        localDataSource.getFollowingSources().map { followings ->
            val query = followings.joinToString(",")
            when (val result = networkDataSource.getFollowingNews(query)) {
                is ApiResult.Error -> DataState.Error(result.message)
                is ApiResult.Success -> DataState.Success(
                    data = result.data.articles?.map(NetworkArticle::toDomain).orEmpty()
                )
            }
        }.catch { e ->
            DataState.Error(e.localizedMessage.orEmpty())
        }.collect { dataState ->
            emit(dataState)
        }
    }

    override fun getSources(): Flow<DataState<List<Source>>> = flow {
        emit(DataState.Loading)
        emit(
            when (val result = networkDataSource.getSources()) {
                is ApiResult.Error -> DataState.Error(result.message)
                is ApiResult.Success -> DataState.Success(
                    data = result.data.sources?.map(NetworkSource::toDomain).orEmpty()
                )
            }
        )
    }.catch { e -> DataState.Error(e.localizedMessage.orEmpty()) }

    override suspend fun toggleFollowing(sourceId: String, isFollowing: Boolean) {
        localDataSource.toggleFollowing(sourceId, isFollowing)
    }
}