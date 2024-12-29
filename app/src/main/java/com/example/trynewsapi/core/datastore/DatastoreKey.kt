package com.example.trynewsapi.core.datastore

import androidx.datastore.preferences.core.stringSetPreferencesKey

object DatastoreKey {
    val FOLLOWINGS = stringSetPreferencesKey("FOLLOWINGS_KEY")
}