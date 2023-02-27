package com.github.donghune.data.repo

import com.github.donghune.domain.repo.KaraokeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class KaraokeRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindKaraokeRepository(
        karaokeRepositoryImpl: KaraokeRepositoryImpl
    ): KaraokeRepository
}
