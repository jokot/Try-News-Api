package com.example.trynewsapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.trynewsapi.feature.topheadlines.TopHeadlinesScreen
import com.example.trynewsapi.core.network.model.NetworkArticle
import com.example.trynewsapi.core.network.model.NetworkSource
import com.example.trynewsapi.ui.theme.TryNewsApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TryNewsApiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TopHeadlinesScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

fun getDummyData(): List<NetworkArticle> {
    val article = NetworkArticle (
        source = NetworkSource(
            name = "NBCSports.com"
        ),
        author = "Michael David Smith",
        title = "NFL Playoff Picture 2024: Updated AFC and NFC Standings, bracket, tiebreakers for Week 13 - NBC Sports",
        description = "The Lions and Packers bolstered their playoff positions, while the Giants became the first NFL team mathematically eliminated, on Thanksgiving.",
        url = "https://www.nbcsports.com/nfl/profootballtalk/rumor-mill/news/nfl-playoff-picture-2024-updated-afc-and-nfc-standings-bracket-tiebreakers-for-week-13",
        urlToImage = "https://nbcsports.brightspotcdn.com/dims4/default/80e2eeb/2147483647/strip/true/crop/8381x4714+0+280/resize/1440x810!/quality/90/?url=https%3A%2F%2Fnbc-sports-production-nbc-sports.s3.us-east-1.amazonaws.com%2Fbrightspot%2Fbb%2F5e%2F055b1f2a480a9ff03b6e580db29d%2Fhttps-api-imagn.com%2Frest%2Fdownload%2FimageID%3D24870823",
        publishedAt = "2024-11-29T10:56:55Z",
        content = "The Lions and Packers bolstered their playoff positions, while the Giants became the first NFL team mathematically eliminated, on Thanksgiving. Heres how the NFL playoff picture looks for Week 13 hea… [+3727 chars]"
    )
    return listOf(
        article,
        article,
        article,
        article
    )
}