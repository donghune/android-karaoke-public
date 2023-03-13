package com.github.donghune.domain.repo

import com.github.donghune.domain.entity.PlayList
import com.github.donghune.domain.entity.Song
import kotlinx.coroutines.flow.Flow

interface PlayListRepository {

    fun getPlayListFlow(): Flow<List<PlayList>>

    suspend fun getPlayList(): List<PlayList>

    suspend fun addPlayItem(songId: Int, playListId: Int)

    suspend fun removePlayItem(songId: Int, playListId: Int)

    suspend fun addPlayList(PlayListName: String)

    suspend fun removePlayList(PlayListName: String)

    fun getPlayListSongs(playListId: Int): Flow<List<Song>>
}
