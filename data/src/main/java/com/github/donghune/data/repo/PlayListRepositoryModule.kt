package com.github.donghune.data.repo

import com.github.donghune.domain.repo.PlayListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PlayListRepositoryModule {

    @Binds
    abstract fun bindPlayListRepository(
        playListRepositoryImpl: PlayListRepositoryImpl
    ): PlayListRepository
}
