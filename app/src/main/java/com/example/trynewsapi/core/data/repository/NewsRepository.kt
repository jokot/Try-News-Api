package com.example.trynewsapi.core.data.repository

import com.example.trynewsapi.core.data.state.DataState
import com.example.trynewsapi.core.datastore.LocalDataSource
import com.example.trynewsapi.core.model.Article
import com.example.trynewsapi.core.model.SavableSource
import com.example.trynewsapi.core.network.ApiResult
import com.example.trynewsapi.core.network.NetworkDataSource
import com.example.trynewsapi.core.network.model.NetworkArticle
import com.example.trynewsapi.core.network.model.NetworkSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

interface NewsRepository {
    fun searchNews(
        q: String,
        page: Int,
        pageSize: Int,
        sources: String
    ): Flow<DataState<List<Article>>>

    fun getHeadlines(): Flow<DataState<List<Article>>>
    fun getSources(): Flow<DataState<List<SavableSource>>>
    suspend fun toggleFollowing(sourceId: String, isFollowing: Boolean)
}

class NewsRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) : NewsRepository {

    override fun searchNews(
        q: String,
        page: Int,
        pageSize: Int,
        sources: String
    ): Flow<DataState<List<Article>>> = flow {
        emit(DataState.Loading)
        emit(
            when (val result = networkDataSource.getNews(q, page, pageSize, sources)) {
                is ApiResult.Error -> DataState.Error(result.message)
                is ApiResult.Success -> DataState.Success(
                    data = result.data.articles?.map(NetworkArticle::toDomain).orEmpty()
                )
            }
        )
    }.catch { e ->
        DataState.Error(e.localizedMessage.orEmpty())
    }

    override fun getHeadlines(): Flow<DataState<List<Article>>> = flow {
        emit(DataState.Loading)
        localDataSource.getFollowingSources().map { followings ->
            when (val result = networkDataSource.getHeadlines(followings.joinToString(","))) {
                is ApiResult.Error -> DataState.Error(result.message)
                is ApiResult.Success -> DataState.Success(
                    data = result.data.articles?.map(NetworkArticle::toDomain).orEmpty()
                )
            }
        }.catch { e ->
            emit(DataState.Error(e.localizedMessage.orEmpty()))
        }.collect { dataState ->
            emit(dataState)
        }
    }

    override fun getSources(): Flow<DataState<List<SavableSource>>> = combine(
        flow { emit(networkDataSource.getSources()) },
        localDataSource.getFollowingSources()
    ) { apiResult, following ->
        when (apiResult) {
            is ApiResult.Error -> DataState.Error(apiResult.message)
            is ApiResult.Success -> DataState.Success(
                data = apiResult.data.sources?.map(NetworkSource::toDomain).orEmpty()
                    .map { source ->
                        SavableSource(
                            source = source,
                            isFollowing = source.name in following
                        )
                    }
            )
        }
    }.onStart {
        emit(DataState.Loading)
    }.catch { e ->
        emit(DataState.Error(e.localizedMessage.orEmpty()))
    }


    override suspend fun toggleFollowing(sourceId: String, isFollowing: Boolean) {
        localDataSource.toggleFollowing(sourceId, isFollowing)
    }
}