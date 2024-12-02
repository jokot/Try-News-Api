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