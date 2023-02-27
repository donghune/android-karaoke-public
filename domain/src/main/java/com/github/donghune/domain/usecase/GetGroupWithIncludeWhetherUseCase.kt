package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.GroupEntity
import com.github.donghune.domain.repo.PlayListRepository
import javax.inject.Inject

class GetGroupWithIncludeWhetherUseCase @Inject constructor(
    private val playListRepository: PlayListRepository
) {

    data class Params(
        val songId: Int
    )

    suspend operator fun invoke(params: Params): Map<GroupEntity, Boolean> {
        return playListRepository.getGroupList().associateWith {
            it.songNumbers.contains(params.songId)
        }
    }
}
