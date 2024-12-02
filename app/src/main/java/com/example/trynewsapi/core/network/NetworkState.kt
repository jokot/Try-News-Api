package com.example.trynewsapi.core.network

sealed interface NetworkState<out T> {
    object Loading: NetworkState<Nothing>
    data class Success<T>(val data: T): NetworkState<T>
    data class Error(val exception: Throwable): NetworkState<Nothing>
}