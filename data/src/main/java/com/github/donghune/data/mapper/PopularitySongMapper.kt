package com.github.donghune.data.mapper

import com.github.donghune.data.local.table.PopularitySongEntity
import com.github.donghune.data.remote.response.PopularitySongResponse
import com.github.donghune.domain.entity.PopularitySong

fun PopularitySong.toPopularitySongResponse(): PopularitySongResponse {
    return PopularitySongResponse(rank, id, title, singing)
}

fun PopularitySongResponse.toPopularitySong(): PopularitySong {
    return PopularitySong(rank, number, title, singer)
}

fun PopularitySong.toPopularitySongEntity(): PopularitySongEntity {
    return PopularitySongEntity(id, rank, title, singing)
}

fun PopularitySongEntity.toPopularitySong(): PopularitySong {
    return PopularitySong(rank, id, title, singing)
}
