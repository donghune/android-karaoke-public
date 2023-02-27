package com.github.donghune.domain.repo

import com.github.donghune.domain.entity.GroupEntity
import com.github.donghune.domain.entity.SongEntity

interface PlayListRepository {

    suspend fun getGroupList(): List<GroupEntity>

    suspend fun addPlayItem(groupEntity: GroupEntity, songEntity: SongEntity)

    suspend fun addGroup(groupEntity: GroupEntity)

    suspend fun removeGroup(groupEntity: GroupEntity)
}
