package com.github.donghune.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.github.donghune.data.local.table.PlayListEntity
import com.github.donghune.data.local.table.SongToPlayListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayListDao {

    @Insert
    suspend fun addSongToPlayerList(songToPlaylistEntity: SongToPlayListEntity)

    @Query("DELETE FROM song_playlist WHERE playListId = :playListId AND songId = :songId")
    suspend fun removeSongToPlayList(songId: Int, playListId: Int)

    @Query("SELECT * FROM song_playlist WHERE playListId = :playListId")
    suspend fun getPlayListSongs(playListId: Int): List<SongToPlayListEntity>

    @Query("SELECT * FROM song_playlist WHERE playListId = :playListId")
    fun  getPlayListSongsFlow(playListId: Int): Flow<List<SongToPlayListEntity>>

    @Query("SELECT * FROM playlist")
    fun getPlayListsFlow(): Flow<List<PlayListEntity>>

    @Query("SELECT * FROM playlist")
    suspend fun getPlayLists(): List<PlayListEntity>

    @Insert
    suspend fun addPlayList(PlayListEntity: PlayListEntity)

    @Delete
    suspend fun removePlayList(PlayListEntity: PlayListEntity)
}
