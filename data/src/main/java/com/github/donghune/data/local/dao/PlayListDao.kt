package com.github.donghune.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.github.donghune.data.local.table.PlayListPref

@Dao
interface PlayListDao {

    @Query("SELECT * FROM playlist WHERE groupId == :groupId")
    suspend fun getPlayList(groupId : Int): List<PlayListPref>

    @Insert
    suspend fun addSongToPlayerList(playListPref: PlayListPref)

    @Delete
    suspend fun removeSongToPlayList(playListPref: PlayListPref)

}