package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.GroupEntity
import com.github.donghune.domain.repo.PlayListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddGroupUseCase @Inject constructor(
    private val playListRepository: PlayListRepository,
) {

    data class Params(
        val groupEntity: GroupEntity,
    )

    operator fun invoke(params: Params): Flow<Unit> = flow {
        emit(playListRepository.addGroup(params.groupEntity))
    }

}