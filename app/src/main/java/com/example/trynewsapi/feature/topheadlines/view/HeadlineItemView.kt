package com.example.trynewsapi.feature.topheadlines.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.trynewsapi.core.model.Article

@Composable
fun HeadlineItemView(
    modifier: Modifier = Modifier,
    item: Article,
    isBookmarked: Boolean,
    onItemClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onItemClick),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = item.publishedAt.orEmpty(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = item.source?.name.orEmpty(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            model = item.urlToImage,
            contentDescription = null
        )
        Text(
            text = item.title.orEmpty(),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = item.description.orEmpty(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Button(onClick = onBookmarkClick) {
            Text(text = if (isBookmarked) "Unbookmark" else "Bookmark")
        }
    }
}