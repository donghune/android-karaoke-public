package com.github.donghune.data.mapper

import com.github.donghune.data.local.table.GroupPref
import com.github.donghune.domain.entity.GroupEntity

fun GroupPref.toGroupEntity(): GroupEntity {
    return GroupEntity(id, name, listOf())
}

fun GroupEntity.toGroupPref(): GroupPref {
    return GroupPref(name)
}
