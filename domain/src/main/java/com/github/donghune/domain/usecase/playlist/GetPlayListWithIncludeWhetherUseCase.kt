package com.github.donghune.domain.usecase.playlist

import com.github.donghune.domain.entity.PlayList
import com.github.donghune.domain.repo.PlayListRepository
import javax.inject.Inject

class GetPlayListWithIncludeWhetherUseCase @Inject constructor(
    private val playListRepository: PlayListRepository
) {

    data class Params(
        val songId: Int
    )

    suspend operator fun invoke(params: Params): Map<PlayList, Boolean> {
        return playListRepository.getPlayList().associateWith {
            it.songNumbers.contains(params.songId)
        }
    }
}
