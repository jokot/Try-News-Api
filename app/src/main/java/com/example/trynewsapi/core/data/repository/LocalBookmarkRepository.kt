package com.example.trynewsapi.core.data.repository

import com.example.trynewsapi.core.datastore.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalBookmarkRepository @Inject constructor(
    private val localDataSource: LocalDataSource
): BookmarkRepository{

    override val bookmarksStreams: Flow<List<String>> = localDataSource.bookmarksStreams

    override suspend fun toggleBookmark(
        articleTitle: String,
        isBookmarked: Boolean
    ) = localDataSource.toggleBookmark(articleTitle, isBookmarked)

}