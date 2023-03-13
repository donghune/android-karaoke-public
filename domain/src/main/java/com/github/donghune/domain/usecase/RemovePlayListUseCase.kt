package com.github.donghune.domain.usecase

import com.github.donghune.domain.repo.PlayListRepository
import javax.inject.Inject

class RemovePlayListUseCase @Inject constructor(
    private val playListRepository: PlayListRepository
) {

    data class Params(
        val PlayListName: String
    )

    suspend operator fun invoke(params: Params) {
        playListRepository.removePlayList(params.PlayListName)
    }
}
