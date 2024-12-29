package com.example.trynewsapi.core.domain.model

import com.example.trynewsapi.core.domain.usecase.GetHeadlines
import com.example.trynewsapi.core.domain.usecase.SearchNews
import com.example.trynewsapi.core.domain.usecase.GetSavableSources
import com.example.trynewsapi.core.domain.usecase.GetSources
import com.example.trynewsapi.core.domain.usecase.ToggleFollowing

data class UseCases(
    val searchNews: SearchNews,
    val getHeadlines: GetHeadlines,
    val getSavableSources: GetSavableSources,
    val getSources: GetSources,
    val toggleFollowing: ToggleFollowing
)
