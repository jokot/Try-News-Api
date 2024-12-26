package com.example.trynewsapi.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.trynewsapi.core.model.SavableSource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FollowingSourcesBottomSheet(
    sources: List<SavableSource>,
    onFollowToggle: (SavableSource) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Following Sources",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            sources.forEach { savableSource ->
                SourceChip(
                    savableSource = savableSource,
                    onFollowToggle = onFollowToggle
                )
            }
        }
    }
}