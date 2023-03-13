package com.github.donghune.data.local.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song_playlist")
data class SongToPlayListEntity(
    @ColumnInfo(name = "songId") val songId: Int,
    @ColumnInfo(name = "playListId") val playListId: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
