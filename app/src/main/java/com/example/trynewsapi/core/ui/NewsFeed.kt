package com.example.trynewsapi.core.ui

import android.net.Uri
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.example.trynewsapi.core.model.SavableArticle

fun LazyStaggeredGridScope.newsFeed(
    news: List<SavableArticle>,
    onSourceClick: (String) -> Unit,
    onToggleBookmark: (String, Boolean) -> Unit
) {
    items(news) { item ->
        val context = LocalContext.current
        val backgroundColor = MaterialTheme.colorScheme.background.toArgb()
        NewsCard(
            item = item.article,
            isBookmarked = item.isBookmarked,
            onItemClick = {
                launchCustomChromeTab(context, Uri.parse(item.article.url), backgroundColor)
            },
            onSourceClick = onSourceClick,
            onBookmarkClick = {
                onToggleBookmark(it, !item.isBookmarked)
            }
        )
    }
}