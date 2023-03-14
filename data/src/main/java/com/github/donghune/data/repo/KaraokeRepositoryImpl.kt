package com.github.donghune.data.repo

import com.github.donghune.data.local.dao.SongDao
import com.github.donghune.data.mapper.toLatestSongEntity
import com.github.donghune.data.mapper.toPopularitySongEntity
import com.github.donghune.data.mapper.toSong
import com.github.donghune.data.mapper.toSongEntity
import com.github.donghune.data.remote.network.KaraokeService
import com.github.donghune.domain.entity.Song
import com.github.donghune.domain.repo.KaraokeRepository
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class KaraokeRepositoryImpl @Inject constructor(
    private val songDao: SongDao,
    private val karaokeService: KaraokeService,
    private val preferencesRepository: DBUpdatePreferencesRepository
) : KaraokeRepository {

    override suspend fun searchByKeyWord(
        keyword: String,
        offset: Int,
        limit: Int
    ): List<Song> {
        return songDao.getSongListByKeyWord(keyword, offset, limit)
            .map { it.toSong() }
    }

    override suspend fun searchByNumber(
        id: Int,
        offset: Int,
        limit: Int
    ): List<Song> {
        return songDao.getSongListByNumber(id, offset, limit)
            .map { it.toSong() }
    }

    override suspend fun searchBySinger(
        singer: String,
        offset: Int,
        limit: Int
    ): List<Song> {
        return songDao.getSongListBySinger(singer, offset, limit)
            .map { it.toSong() }
    }

    override suspend fun searchByTitleWithSinger(
        keyword: String,
        offset: Int,
        limit: Int
    ): List<Song> {
        return songDao.getSongListByTitleWithSinger(keyword, offset, limit)
            .map { it.toSong() }
    }

    override suspend fun getPopularityList(): List<Song> {
        val now = dateFormat.format(Calendar.getInstance().time)
        val updated = dateFormat.format(
            Calendar.getInstance()
                .apply { timeInMillis = preferencesRepository.popularityUpdatedFlow.first() }.time
        )

        val data = songDao.getPopularitySongList().map { it.toSong() }

        if (now != updated || data.isEmpty()) {
            preferencesRepository.updatePopularityUpdated()
            songDao.clearPopularitySongList()
            return karaokeService.getPopularSongList()
                .map { it.toSong() }
                .onEach { songDao.insertPopularitySong(it.toPopularitySongEntity()) }
        }

        return data
    }

    override suspend fun getLatestList(): List<Song> {
        val now = dateFormat.format(Calendar.getInstance().time)
        val updated = dateFormat.format(
            Calendar.getInstance()
                .apply { timeInMillis = preferencesRepository.latestUpdatedFlow.first() }.time
        )

        val data = songDao.getLatestSongList().map { it.toSong() }

        if (now != updated || data.isEmpty()) {
            preferencesRepository.updateLatestUpdated()
            songDao.clearLatestSongList()
            return karaokeService.getNewSongList()
                .map { it.toSong() }
                .onEach { song: Song ->
                    songDao.insertLatestSongPref(song.toLatestSongEntity())
                    songDao.insertSongPref(song.toSongEntity())
                }
        }
        return data
    }

    companion object {
        private val TAG = KaraokeRepositoryImpl::class.java.simpleName
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    }
}
