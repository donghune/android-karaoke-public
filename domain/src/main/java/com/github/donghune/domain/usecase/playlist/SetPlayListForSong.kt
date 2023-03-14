package com.github.donghune.domain.usecase.playlist

import com.github.donghune.domain.entity.Song
import com.github.donghune.domain.repo.PlayListRepository
import javax.inject.Inject

class SetPlayListForSong @Inject constructor(
    private val playListRepository: PlayListRepository
) {
    data class Params(
        val song: Song,
        val playListId: Int,
        val isChecked: Boolean
    )

    suspend operator fun invoke(params: Params) {
        if (params.isChecked) {
            playListRepository.addPlayItem(
                params.song,
                params.playListId
            )
        } else {
            playListRepository.removePlayItem(
                params.song,
                params.playListId
            )
        }
    }
}
