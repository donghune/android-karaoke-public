package com.github.donghune.domain

import com.github.donghune.domain.repo.KaraokeRepository
import com.github.donghune.domain.repo.PlayListRepository
import com.github.donghune.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideAddGroupUseCase(
        playListRepository: PlayListRepository
    ): AddGroupUseCase {
        return AddGroupUseCase(playListRepository)
    }

    @Provides
    fun provideGetGroupsUseCase(
        playListRepository: PlayListRepository
    ): GetGroupsUseCase {
        return GetGroupsUseCase(playListRepository)
    }

    @Provides
    fun provideGetGroupWithIncludeWhetherUseCase(
        playListRepository: PlayListRepository
    ): GetGroupWithIncludeWhetherUseCase {
        return GetGroupWithIncludeWhetherUseCase(playListRepository)
    }

    @Provides
    fun provideGetLatestSongsUseCase(
        karaokeRepository: KaraokeRepository
    ): GetLatestSongsUseCase {
        return GetLatestSongsUseCase(karaokeRepository)
    }

    @Provides
    fun provideGetPopularitySongsUseCase(
        karaokeRepository: KaraokeRepository
    ): GetPopularitySongsUseCase {
        return GetPopularitySongsUseCase(karaokeRepository)
    }

    @Provides
    fun provideGetSongsByKeywordUseCase(
        karaokeRepository: KaraokeRepository
    ): GetSongsByKeywordUseCase {
        return GetSongsByKeywordUseCase(karaokeRepository)
    }

    @Provides
    fun provideGetSongsBySingerUseCase(
        karaokeRepository: KaraokeRepository
    ): GetSongsBySingerUseCase {
        return GetSongsBySingerUseCase(karaokeRepository)
    }

    @Provides
    fun provideGetSongsByTitleWithSingerUseCase(
        karaokeRepository: KaraokeRepository
    ): GetSongsByTitleWithSingerUseCase {
        return GetSongsByTitleWithSingerUseCase(karaokeRepository)
    }

    @Provides
    fun provideRemoveGroupUseCase(
        playListRepository: PlayListRepository
    ): RemoveGroupUseCase {
        return RemoveGroupUseCase(playListRepository)
    }
}
