package com.example.trynewsapi.core.data.di

import com.example.trynewsapi.core.data.repository.BookmarkRepository
import com.example.trynewsapi.core.data.repository.LocalBookmarkRepository
import com.example.trynewsapi.core.data.repository.NetworkNewsRepository
import com.example.trynewsapi.core.data.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindLocalBookmarkRepository(
        localBookmarkRepository: LocalBookmarkRepository
    ): BookmarkRepository

    @Binds
    internal abstract fun bindNetworkNewsRepository(
        networkNewsRepository: NetworkNewsRepository
    ): NewsRepository
}