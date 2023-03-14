package com.github.donghune.domain.repo

import com.github.donghune.domain.entity.PlayList
import com.github.donghune.domain.entity.Song
import kotlinx.coroutines.flow.Flow

interface PlayListRepository {

    fun getPlayListFlow(): Flow<List<PlayList>>

    suspend fun getPlayList(): List<PlayList>

    suspend fun addPlayItem(song: Song, playListId: Int)

    suspend fun removePlayItem(song: Song, playListId: Int)

    suspend fun addPlayList(playListName: String)

    suspend fun removePlayList(playListName: String)

    fun getPlayListSongs(playListId: Int): Flow<List<Song>>
}
