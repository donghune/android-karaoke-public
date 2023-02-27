package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.GroupEntity
import com.github.donghune.domain.repo.PlayListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGroupWithIncludeWhetherUseCase @Inject constructor(
    private val playListRepository: PlayListRepository
) {

    data class Params(
        val songId: Int
    )

    operator fun invoke(params: Params): Flow<Map<GroupEntity, Boolean>> = flow {
        emit(
            playListRepository.getGroupList().map {
                it to it.songNumbers.contains(params.songId)
            }.toMap()
        )
    }
}
