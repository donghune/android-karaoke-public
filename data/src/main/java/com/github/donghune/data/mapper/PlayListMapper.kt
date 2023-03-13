package com.github.donghune.data.mapper

import com.github.donghune.data.local.table.PlayListEntity
import com.github.donghune.domain.entity.PlayList

fun PlayListEntity.toPlayList(): PlayList {
    return PlayList(id, name, listOf())
}

fun PlayList.toPlayListEntity(): PlayListEntity {
    return PlayListEntity(name)
}
