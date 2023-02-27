package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.GroupEntity
import com.github.donghune.domain.repo.PlayListRepository
import javax.inject.Inject

class GetGroupsUseCase @Inject constructor(
    private val playListRepository: PlayListRepository
) {

    suspend operator fun invoke(): List<GroupEntity> {
        return playListRepository.getGroupList()
    }
}
