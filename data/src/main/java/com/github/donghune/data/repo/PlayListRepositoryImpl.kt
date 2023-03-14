package com.github.donghune.data.repo

import android.util.Log
import com.github.donghune.data.local.dao.PlayListDao
import com.github.donghune.data.local.dao.SongDao
import com.github.donghune.data.local.table.PlayListEntity
import com.github.donghune.data.local.table.SongToPlayListEntity
import com.github.donghune.data.mapper.toSong
import com.github.donghune.data.mapper.toSongEntity
import com.github.donghune.domain.entity.PlayList
import com.github.donghune.domain.entity.Song
import com.github.donghune.domain.repo.PlayListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PlayListRepositoryImpl @Inject constructor(
    private val playListDao: PlayListDao,
    private val songDao: SongDao
) : PlayListRepository {

    override fun getPlayListFlow(): Flow<List<PlayList>> {
        return playListDao.getPlayListsFlow()
            .map { list ->
                list.map { playListEntity ->
                    playListEntity to playListDao.getPlayListSongs(playListEntity.id)
                }
            }
            .map { list ->
                list.map { (playListEntity, playListSongs) ->
                    PlayList(
                        playListEntity.id,
                        playListEntity.name,
                        playListSongs.map { it.songId }
                    )
                }
            }
    }

    override suspend fun getPlayList(): List<PlayList> {
        return playListDao.getPlayLists()
            .map { playListEntity ->
                playListEntity to playListDao.getPlayListSongs(playListEntity.id)
            }.map { (PlayListEntity, playListEntities) ->
                PlayList(PlayListEntity.id, PlayListEntity.name, playListEntities.map { it.songId })
            }
    }

    override suspend fun addPlayItem(song: Song, playListId: Int) {
        playListDao.addSongToPlayerList(SongToPlayListEntity(song.id, playListId))
        songDao.insertSong(song.toSongEntity())
    }

    override suspend fun removePlayItem(song: Song, playListId: Int) {
        playListDao.removeSongToPlayList(song.id, playListId)
        songDao.deleteSong(song.toSongEntity())
    }

    override suspend fun addPlayList(playListName: String) {
        playListDao.addPlayList(PlayListEntity(playListName))
    }

    override suspend fun removePlayList(playListName: String) {
        playListDao.removePlayList(PlayListEntity(playListName))
    }

    override fun getPlayListSongs(playListId: Int): Flow<List<Song>> {
        return playListDao.getPlayListSongsFlow(playListId)
            .map { list -> list.mapNotNull { song -> songDao.getSong(song.songId) } }
            .map { list -> list.map { it.toSong() } }
    }
}
