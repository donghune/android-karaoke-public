package com.github.donghune.domain.usecase

import com.github.donghune.domain.repo.PlayListRepository
import javax.inject.Inject

class AddPlayListUseCase @Inject constructor(
    private val playListRepository: PlayListRepository
) {

    data class Params(
        val playListName: String
    )

    suspend operator fun invoke(params: Params) {
        playListRepository.addPlayList(params.playListName)
    }
}
