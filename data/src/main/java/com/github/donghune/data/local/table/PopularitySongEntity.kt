package com.github.donghune.data.local.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popularity_songs")
data class PopularitySongEntity(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "rank") val rank: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "singing") val singing: String
)
