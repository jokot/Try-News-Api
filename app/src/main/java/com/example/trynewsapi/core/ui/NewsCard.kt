package com.example.trynewsapi.core.ui

import android.content.Context
import android.net.Uri
import androidx.annotation.ColorInt
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.trynewsapi.core.model.Article

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    item: Article,
    isBookmarked: Boolean,
    onItemClick: (String) -> Unit,
    onSourceClick: (String) -> Unit,
    onBookmarkClick: (String) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = { onItemClick(item.url) }
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = item.publishedAt,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = item.source?.name.orEmpty(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onSourceClick(item.source?.id.orEmpty()) }
            )
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                model = item.urlToImage,
                contentDescription = null
            )
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Button(onClick = { onBookmarkClick(item.title) }) {
                Text(text = if (isBookmarked) "Unbookmark" else "Bookmark")
            }
        }
    }
}

fun launchCustomChromeTab(context: Context, uri: Uri, @ColorInt toolbarColor: Int) {
    val customTabColor = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(toolbarColor).build()
    val chromeTabsIntent = CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(customTabColor)
        .build()

    chromeTabsIntent.launchUrl(context, uri)
}