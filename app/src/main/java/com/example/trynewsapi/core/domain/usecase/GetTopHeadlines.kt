package com.example.trynewsapi.core.domain.usecase

import com.example.trynewsapi.core.data.repository.NewsRepository

class GetTopHeadlines(
    private val newsRepository: NewsRepository
) {
    operator fun invoke() = newsRepository.getHeadlines(PAGE_SIZE)

    private companion object {
        private const val PAGE_SIZE = 5
    }
}