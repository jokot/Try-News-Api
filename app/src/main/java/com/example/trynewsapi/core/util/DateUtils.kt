package com.example.trynewsapi.core.util

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(dateString: String): String {
    // Assuming dateString is in ISO 8601 format
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.getDefault())
    return try {
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: "Unknown date"
    } catch (e: Exception) {
        "Unknown date"
    }
}