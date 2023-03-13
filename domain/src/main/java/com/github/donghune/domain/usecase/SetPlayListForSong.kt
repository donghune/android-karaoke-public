package com.github.donghune.domain.usecase

import com.github.donghune.domain.repo.PlayListRepository
import javax.inject.Inject

class SetPlayListForSong @Inject constructor(
    private val playListRepository: PlayListRepository
) {
    data class Params(
        val songId: Int,
        val playListId: Int,
        val isChecked: Boolean
    )

    suspend operator fun invoke(params: Params) {
        if (params.isChecked) {
            playListRepository.addPlayItem(
                params.songId,
                params.playListId
            )
        } else {
            playListRepository.removePlayItem(
                params.songId,
                params.playListId
            )
        }
    }
}
