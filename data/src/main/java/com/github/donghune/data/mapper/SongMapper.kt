package com.github.donghune.data.mapper

import com.github.donghune.data.local.table.LatestSongEntity
import com.github.donghune.data.local.table.SongEntity
import com.github.donghune.data.remote.response.SongResponse
import com.github.donghune.domain.entity.Song

fun SongResponse.toSong(): Song {
    return Song(number, title, singer)
}

fun Song.toSongEntity(): SongEntity {
    return SongEntity(id, title, singer)
}

fun SongEntity.toSong(): Song {
    return Song(id, title, singing)
}

fun Song.toLatestSongEntity(): LatestSongEntity {
    return LatestSongEntity(id, title, singer)
}

fun LatestSongEntity.toSong(): Song {
    return Song(id, title, singing)
}
