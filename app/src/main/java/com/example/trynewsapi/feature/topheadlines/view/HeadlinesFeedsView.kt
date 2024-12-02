package com.example.trynewsapi.feature.topheadlines.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trynewsapi.core.model.SavableArticle

@Composable
fun HeadlinesFeedsView(
    modifier: Modifier = Modifier,
    news: List<SavableArticle>,
    onItemClick: (String) -> Unit,
    onToggleBookmark: (String, Boolean) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(news) { item ->
            HeadlineItemView(
                item = item.article,
                isBookmarked = item.isBookmarked,
                onItemClick = {
                    onItemClick(item.article.url)
                },
                onBookmarkClick = {
                    onToggleBookmark(item.article.title, !item.isBookmarked)
                }
            )
        }
    }
}