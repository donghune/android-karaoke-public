package com.github.donghune.data.remote

import com.github.donghune.data.remote.network.KaraokeService
import com.github.donghune.data.remote.network.KaraokeTJService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideKaraokeTJService(): KaraokeTJService {
        return KaraokeTJService()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkServiceModule {

    @Binds
    @Singleton
    abstract fun bindKaraokeService(
        karaokeTJService: KaraokeTJService
    ) : KaraokeService
}