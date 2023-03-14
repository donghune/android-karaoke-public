package com.github.donghune.data.mapper

import com.github.donghune.data.local.table.PopularitySongEntity
import com.github.donghune.data.remote.response.PopularitySongResponse
import com.github.donghune.domain.entity.Song

fun PopularitySongResponse.toSong(): Song {
    return Song(number, title, singer)
}

fun PopularitySongEntity.toSong(): Song {
    return Song(id, title, singer)
}

fun Song.toPopularitySongEntity(): PopularitySongEntity {
    return PopularitySongEntity(id, title, singer)
}
