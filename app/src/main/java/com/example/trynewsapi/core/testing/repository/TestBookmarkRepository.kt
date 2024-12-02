package com.example.trynewsapi.core.testing.repository

import com.example.trynewsapi.core.data.repository.BookmarkRepository
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull

class TestBookmarkRepository : BookmarkRepository {

    /**
     * The backing hot flow for the list of followed topic ids for testing.
     */
    private val _bookmarksStreams = MutableSharedFlow<List<String>>(replay = 1, onBufferOverflow = DROP_OLDEST)

    private val currentBookmarksStreams get() = _bookmarksStreams.replayCache.firstOrNull().orEmpty()

    override val bookmarksStreams: Flow<List<String>> = _bookmarksStreams.filterNotNull()

    override suspend fun toggleBookmark(articleTitle: String, isBookmarked: Boolean) {
        currentBookmarksStreams.let { current ->
            val bookmarkedNews = if (isBookmarked) {
                current + articleTitle
            } else {
                current - articleTitle
            }

            _bookmarksStreams.tryEmit(bookmarkedNews)
        }
    }

    /**
     * A test-only API to allow setting of user data directly.
     */
    fun setBookmarks(bookmarks: List<String>) {
        _bookmarksStreams.tryEmit(bookmarks)
    }
}