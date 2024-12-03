package com.example.trynewsapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.trynewsapi.core.designsystem.theme.TnaTheme
import com.example.trynewsapi.ui.TnaApp
import com.example.trynewsapi.ui.rememberTnaAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberTnaAppState()
            TnaTheme {
                TnaApp(appState = appState)
            }
        }
    }
}