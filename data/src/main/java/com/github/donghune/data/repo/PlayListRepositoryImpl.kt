package com.github.donghune.data.repo

import com.github.donghune.data.local.dao.PlayListDao
import com.github.donghune.data.local.dao.SongDao
import com.github.donghune.data.local.table.PlayListEntity
import com.github.donghune.data.local.table.SongToPlayListEntity
import com.github.donghune.data.mapper.toSong
import com.github.donghune.domain.entity.PlayList
import com.github.donghune.domain.entity.Song
import com.github.donghune.domain.repo.PlayListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun addPlayItem(songId: Int, playListId: Int) {
        playListDao.addSongToPlayerList(SongToPlayListEntity(songId, playListId))
    }

    override suspend fun removePlayItem(songId: Int, playListId: Int) {
        playListDao.removeSongToPlayList(songId, playListId)
    }

    override suspend fun addPlayList(PlayListName: String) {
        playListDao.addPlayList(PlayListEntity(PlayListName))
    }

    override suspend fun removePlayList(PlayListName: String) {
        playListDao.removePlayList(PlayListEntity(PlayListName))
    }

    override fun getPlayListSongs(playListId: Int): Flow<List<Song>> {
        return playListDao.getPlayListSongsFlow(playListId)
            .map { list -> list.map { songDao.getSongById(it.songId) } }
            .map { list -> list.map { it.toSong() } }
    }
}
