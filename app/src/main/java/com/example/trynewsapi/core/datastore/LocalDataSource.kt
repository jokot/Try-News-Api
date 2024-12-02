package com.example.trynewsapi.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences.pb")

class LocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    val bookmarksStreams: Flow<List<String>> = dataStore.data.map {
        it[PreferencesKeys.BOOKMARKS]?.toList().orEmpty()
    }

    suspend fun toggleBookmark(articleTitle: String, isBookmarked: Boolean) {
        dataStore.edit {
            val currentValue = it[PreferencesKeys.BOOKMARKS]?.toMutableSet().orEmpty()
            val newValue = mutableSetOf<String>()
            if (isBookmarked) {
                newValue.addAll(currentValue.plus(articleTitle))
            } else {
                newValue.addAll(currentValue.minus(articleTitle))
            }
            it[PreferencesKeys.BOOKMARKS] = newValue
        }
    }
}