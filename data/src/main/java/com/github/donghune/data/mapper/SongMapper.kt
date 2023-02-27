package com.github.donghune.data.mapper

import com.github.donghune.data.local.table.LatestSongPref
import com.github.donghune.data.local.table.SongPref
import com.github.donghune.data.remote.response.SongResponse
import com.github.donghune.domain.entity.SongEntity

fun SongEntity.toSongResponse(): SongResponse {
    return SongResponse(id, title, singer)
}

fun SongResponse.toSongEntity(): SongEntity {
    return SongEntity(number, title, singer)
}

fun SongEntity.toSongPref(): SongPref {
    return SongPref(id, title, singer)
}

fun SongPref.toSongEntity(): SongEntity {
    return SongEntity(id, title, singing)
}

fun SongEntity.toLatestSongPref(): LatestSongPref {
    return LatestSongPref(id, title, singer)
}

fun LatestSongPref.toSongEntity(): SongEntity {
    return SongEntity(id, title, singing)
}
