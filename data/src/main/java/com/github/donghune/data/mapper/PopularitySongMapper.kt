package com.github.donghune.data.mapper

import com.github.donghune.data.local.table.PopularitySongPref
import com.github.donghune.data.remote.response.PopularitySongResponse
import com.github.donghune.domain.entity.PopularitySongEntity

fun PopularitySongEntity.toPopularitySongResponse(): PopularitySongResponse {
    return PopularitySongResponse(rank, id, title, singing)
}

fun PopularitySongResponse.toPopularitySongEntity(): PopularitySongEntity {
    return PopularitySongEntity(rank, number, title, singer)
}

fun PopularitySongEntity.toPopularitySongPref(): PopularitySongPref {
    return PopularitySongPref(id, rank, title, singing)
}

fun PopularitySongPref.toPopularitySongEntity(): PopularitySongEntity {
    return PopularitySongEntity(rank, id, title, singing)
}