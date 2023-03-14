package com.github.donghune.domain.usecase.playlist

import com.github.donghune.domain.repo.PlayListRepository
import javax.inject.Inject

class RemovePlayListUseCase @Inject constructor(
    private val playListRepository: PlayListRepository
) {

    data class Params(
        val playListName: String
    )

    suspend operator fun invoke(params: Params) {
        playListRepository.removePlayList(params.playListName)
    }
}
