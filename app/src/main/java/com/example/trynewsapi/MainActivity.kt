package com.example.trynewsapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.trynewsapi.core.designsystem.theme.TryNewsApiTheme
import com.example.trynewsapi.feature.headlines.HeadlinesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TryNewsApiTheme {
                HeadlinesScreen()
            }
        }
    }
}