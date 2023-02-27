package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.GroupEntity
import com.github.donghune.domain.repo.PlayListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGroupsUseCase @Inject constructor(
    private val playListRepository: PlayListRepository
) {

    operator fun invoke(): Flow<List<GroupEntity>> = flow {
        emit(playListRepository.getGroupList())
    }
}
