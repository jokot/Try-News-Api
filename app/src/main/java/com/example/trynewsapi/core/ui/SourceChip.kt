package com.example.trynewsapi.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trynewsapi.core.model.SavableSource

@Composable
fun SourceChip(
    savableSource: SavableSource,
    onFollowToggle: (SavableSource) -> Unit
) {
    val backgroundColor = if (savableSource.isFollowing) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    FilterChip(
        selected = savableSource.isFollowing,
        onClick = { onFollowToggle(savableSource.copy(isFollowing = !savableSource.isFollowing)) },
        label = {
            Text(
                savableSource.source.name,
                color = if (savableSource.isFollowing) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = backgroundColor,
            selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            selectedLabelColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(end = 8.dp)
    )
}
