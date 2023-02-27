package com.github.donghune.data.repo

import com.github.donghune.data.local.dao.GroupDao
import com.github.donghune.data.local.dao.PlayListDao
import com.github.donghune.data.local.table.PlayListPref
import com.github.donghune.data.mapper.toGroupPref
import com.github.donghune.domain.entity.GroupEntity
import com.github.donghune.domain.entity.SongEntity
import com.github.donghune.domain.repo.PlayListRepository
import javax.inject.Inject

class PlayListRepositoryImpl @Inject constructor(
    private val playListDao: PlayListDao,
    private val groupDao: GroupDao
) : PlayListRepository {

    override suspend fun getGroupList(): List<GroupEntity> {
        return groupDao.getGroupList()
            .map { groupPref -> groupPref to playListDao.getPlayList(groupPref.id) }
            .map { (group, groupPref) ->
                GroupEntity(group.id, group.name, groupPref.map { it.singId })
            }
            .toList()
    }

    override suspend fun addPlayItem(groupEntity: GroupEntity, songEntity: SongEntity) {
        playListDao.addSongToPlayerList(PlayListPref(0, groupEntity.id, songEntity.id))
    }

    override suspend fun addGroup(groupEntity: GroupEntity) {
        groupDao.addGroup(groupEntity.toGroupPref())
    }

    override suspend fun removeGroup(groupEntity: GroupEntity) {
        groupDao.removeGroup(groupEntity.toGroupPref())
    }
}
