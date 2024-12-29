package com.example.trynewsapi.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.trynewsapi.R
import com.example.trynewsapi.core.model.SavableSource


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterSourcesBottomSheet(
    sources: List<SavableSource>,
    onFollowToggle: (Int, SavableSource) -> Unit,
    onResetClick: () -> Unit,
    onApplyClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.filter_source_title),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            TextButton(onClick = onResetClick) {
                Text(stringResource(R.string.filter_reset))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false),
        ) {
            sources.forEachIndexed { index, savableSource ->
                SourceChip(
                    savableSource = savableSource,
                    onFollowToggle = { updatedSource ->
                        onFollowToggle(index, updatedSource)
                    }
                )
            }
        }

        Button(
            onClick = onApplyClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.filter_apply))
        }
    }
}