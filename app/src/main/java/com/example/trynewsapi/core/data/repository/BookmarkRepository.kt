package com.example.trynewsapi.core.data.repository

import kotlinx.coroutines.flow.Flow


interface BookmarkRepository {

    val bookmarksStreams: Flow<List<String>>

    suspend fun toggleBookmark(articleTitle: String, isBookmarked: Boolean)
}
