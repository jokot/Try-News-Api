package com.example.trynewsapi.core.domain.usecase

import com.example.trynewsapi.core.data.repository.NewsRepository

class GetHeadlines(
    private val newsRepository: NewsRepository
) {
    operator fun invoke() = newsRepository.getHeadlines()
}