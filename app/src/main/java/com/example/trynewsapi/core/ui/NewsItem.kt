package com.example.trynewsapi.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.trynewsapi.core.model.Article
import com.example.trynewsapi.core.util.formatDate

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(article.url)
            }
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        // Article Thumbnail
        AsyncImage(
            model = article.urlToImage,
            contentDescription = article.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 8.dp)
        )
        // Article Details
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "By ${article.author}",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = article.description,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = formatDate(article.publishedAt),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}