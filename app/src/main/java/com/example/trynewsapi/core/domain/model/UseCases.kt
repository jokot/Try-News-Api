package com.example.trynewsapi.core.domain.model

import com.example.trynewsapi.core.domain.usecase.GetFollowingNews
import com.example.trynewsapi.core.domain.usecase.GetHeadlines
import com.example.trynewsapi.core.domain.usecase.GetNews
import com.example.trynewsapi.core.domain.usecase.GetSources
import com.example.trynewsapi.core.domain.usecase.GetTopHeadlines
import com.example.trynewsapi.core.domain.usecase.ToggleFollowing

data class UseCases(
    val getNews: GetNews,
    val getHeadlines: GetHeadlines,
    val getTopHeadlines: GetTopHeadlines,
    val getSources: GetSources,
    val getFollowingNews: GetFollowingNews,
    val toggleFollowing: ToggleFollowing
)
