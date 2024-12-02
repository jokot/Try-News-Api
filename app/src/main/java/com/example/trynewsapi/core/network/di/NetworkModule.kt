package com.example.trynewsapi.core.network.di

import androidx.compose.ui.util.trace
import com.example.trynewsapi.core.network.TNA_BASE_URL
import com.example.trynewsapi.core.network.service.NetworkApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = trace("TnaOkHttpClient") {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        networkJson: Json,
        okHttpCallFactory: dagger.Lazy<Call.Factory>,
    ): Retrofit = trace("RetrofitTnaNetwork") {
        Retrofit.Builder()
            .baseUrl(TNA_BASE_URL)
            .callFactory {
                okHttpCallFactory.get().newCall(it)
            }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideService(
        retrofit: Retrofit
    ): NetworkApiService = trace("TnaNetworkApiService") {
        retrofit.create(NetworkApiService::class.java)
    }
}