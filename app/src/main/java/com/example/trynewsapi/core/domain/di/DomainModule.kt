package com.example.trynewsapi.core.domain.di

import com.example.trynewsapi.core.data.repository.NewsRepository
import com.example.trynewsapi.core.domain.model.UseCases
import com.example.trynewsapi.core.domain.usecase.GetFollowingNews
import com.example.trynewsapi.core.domain.usecase.GetHeadlines
import com.example.trynewsapi.core.domain.usecase.GetNews
import com.example.trynewsapi.core.domain.usecase.GetSources
import com.example.trynewsapi.core.domain.usecase.GetTopHeadlines
import com.example.trynewsapi.core.domain.usecase.ToggleFollowing
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideUseCases(
        newsRepository: NewsRepository
    ): UseCases = UseCases(
        getNews = GetNews(newsRepository),
        getHeadlines = GetHeadlines(newsRepository),
        getTopHeadlines = GetTopHeadlines(newsRepository),
        getSources = GetSources(newsRepository),
        getFollowingNews = GetFollowingNews(newsRepository),
        toggleFollowing = ToggleFollowing(newsRepository)
    )
}