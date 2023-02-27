package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.GroupEntity
import com.github.donghune.domain.repo.PlayListRepository
import javax.inject.Inject

class AddGroupUseCase @Inject constructor(
    private val playListRepository: PlayListRepository
) {

    data class Params(
        val groupEntity: GroupEntity
    )

    suspend operator fun invoke(params: Params) {
        playListRepository.addGroup(params.groupEntity)
    }
}
