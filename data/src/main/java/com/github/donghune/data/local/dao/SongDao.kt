package com.github.donghune.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.github.donghune.data.local.table.SongEntity

@Dao
interface SongDao {
    @Query("SELECT * FROM song WHERE id = :songId")
    suspend fun getSong(songId: Int): SongEntity?

    @Insert
    suspend fun insertSong(song: SongEntity)

    @Delete
    suspend fun deleteSong(song: SongEntity)
}
