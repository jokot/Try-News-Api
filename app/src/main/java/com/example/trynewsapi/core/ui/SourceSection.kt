package com.example.trynewsapi.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trynewsapi.core.model.SavableSource

@Composable
fun SourcesSection(
    savableSources: List<SavableSource>,
    onSeeAllClick: () -> Unit,
    onSourceFollowedChanged: (SavableSource) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Sources",
                style = MaterialTheme.typography.titleLarge
            )
            TextButton(onClick = onSeeAllClick) {
                Text("See All")
            }
        }

        // Horizontal Scrollable Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(savableSources) { savableSource ->
                SourceChip(
                    savableSource = savableSource,
                    onFollowToggle = { updatedSource ->
                        onSourceFollowedChanged(updatedSource)
                    }
                )
            }
        }
    }
}
