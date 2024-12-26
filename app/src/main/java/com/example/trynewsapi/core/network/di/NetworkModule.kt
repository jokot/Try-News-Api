package com.example.trynewsapi.core.network.di

import com.example.trynewsapi.BuildConfig
import com.example.trynewsapi.core.network.NetworkDataSource
import com.example.trynewsapi.core.network.NetworkDataSourceImpl
import com.example.trynewsapi.core.network.service.ApiService
import dagger.Binds
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
object NetworkDependenciesModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideOkHttpCallFactory(): Call.Factory =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-Api-Key", BuildConfig.ACCESS_TOKEN)
                    .build()
                chain.proceed(request)
            }
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }

                    )
                }
            }
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        jsonConverter: Json,
        okHttpCallFactory: dagger.Lazy<Call.Factory>
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .callFactory { request ->
                okHttpCallFactory.get().newCall(request)
            }
            .addConverterFactory(
                jsonConverter.asConverterFactory("application/json".toMediaType())
            )
            .build()

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService =
        retrofit.create(ApiService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    internal abstract fun bindNetworkDataSource(
        networkDataSourceImpl: NetworkDataSourceImpl
    ): NetworkDataSource
}