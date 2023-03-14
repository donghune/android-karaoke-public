package com.github.donghune.data.mapper

import com.github.donghune.data.local.table.SongEntity
import com.github.donghune.data.remote.response.CrawlingSongResponse
import com.github.donghune.data.remote.response.MananaSongResponse
import com.github.donghune.domain.entity.Song

fun CrawlingSongResponse.toSong(): Song {
    return Song(number, title, singer)
}

fun MananaSongResponse.toSong(): Song {
    return Song(no.toInt(), title, singer)
}

fun SongEntity.toSong(): Song {
    return Song(id, title, singer)
}

fun Song.toSongEntity(): SongEntity {
    return SongEntity(id, title, singer)
}
