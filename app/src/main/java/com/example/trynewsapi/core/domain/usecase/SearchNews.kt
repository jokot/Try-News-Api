package com.example.trynewsapi.core.domain.usecase

import com.example.trynewsapi.core.data.repository.NewsRepository

class SearchNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(
        q: String,
        page: Int,
        pageSize: Int,
        sources: String
    ) = newsRepository.searchNews(q, page, pageSize, sources)
}