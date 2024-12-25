package com.example.trynewsapi.core.domain.usecase

import com.example.trynewsapi.core.data.repository.NewsRepository

class ToggleFollowing(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(
        sourceId: String,
        isFollowing: Boolean
    ) = newsRepository.toggleFollowing(sourceId, isFollowing)
}