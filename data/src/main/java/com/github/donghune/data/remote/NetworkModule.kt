@file:OptIn(ExperimentalSerializationApi::class)

package com.github.donghune.data.remote

import com.github.donghune.data.remote.network.KaraokeCrawlingService
import com.github.donghune.data.remote.network.KaraokeTJCrawlingService
import com.github.donghune.data.remote.network.MananaKaraokeService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideKaraokeTJService(): KaraokeCrawlingService {
        return KaraokeTJCrawlingService()
    }

    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideMananaKaraokeService(): MananaKaraokeService {
        return Retrofit.Builder()
            .baseUrl("https://api.manana.kr")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(
                OkHttpClient().newBuilder().addNetworkInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                ).build()
            )
            .build()
            .create(MananaKaraokeService::class.java)
    }
}
