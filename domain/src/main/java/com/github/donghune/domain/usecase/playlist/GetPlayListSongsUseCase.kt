package com.github.donghune.domain.usecase.playlist

import com.github.donghune.domain.entity.Song
import com.github.donghune.domain.repo.PlayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlayListSongsUseCase @Inject constructor(
    private val playListRepository: PlayListRepository
) {
    data class Params(
        val playListId: Int
    )

    operator fun invoke(params: Params): Flow<List<Song>> {
        return playListRepository.getPlayListSongs(params.playListId)
    }
}
