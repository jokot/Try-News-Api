package com.example.trynewsapi.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

interface LocalDataSource {
    suspend fun getFollowingSources(): Flow<Set<String>>
    suspend fun toggleFollowing(sourceId: String, isFollowing: Boolean)
}

class LocalDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : LocalDataSource {
    override suspend fun getFollowingSources(): Flow<Set<String>> =
        dataStore.data.map { preferences ->
            preferences[DatastoreKey.FOLLOWINGS].orEmpty()
        }.distinctUntilChanged()

    override suspend fun toggleFollowing(sourceId: String, isFollowing: Boolean) {
        dataStore.edit { preferences ->
            val followings = preferences[DatastoreKey.FOLLOWINGS]?.toMutableSet() ?: mutableSetOf()
            if (isFollowing) {
                followings.add(sourceId)
            } else {
                followings.remove(sourceId)
            }

            preferences[DatastoreKey.FOLLOWINGS] = followings
        }
    }
}