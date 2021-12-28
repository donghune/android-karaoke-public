package com.github.donghune.data.repo

import android.util.Log
import com.github.donghune.data.local.dao.SongDao
import com.github.donghune.data.mapper.*
import com.github.donghune.data.remote.network.KaraokeService
import com.github.donghune.domain.entity.PopularitySongEntity
import com.github.donghune.domain.entity.SongEntity
import com.github.donghune.domain.repo.KaraokeRepository
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class KaraokeRepositoryImpl @Inject constructor(
    private val songDao: SongDao,
    private val karaokeService: KaraokeService,
    private val preferencesRepository: DBUpdatePreferencesRepository,
) : KaraokeRepository {

    override suspend fun searchByKeyWord(
        keyword: String,
        offset: Int,
        limit: Int,
    ): List<SongEntity> {
        return songDao.getSongListByKeyWord(keyword, offset, limit)
            .map { it.toSongEntity() }
    }

    override suspend fun searchByNumber(
        id: Int,
        offset: Int,
        limit: Int,
    ): List<SongEntity> {
        return songDao.getSongListByNumber(id, offset, limit)
            .map { it.toSongEntity() }
    }

    override suspend fun searchBySinger(
        singing: String,
        offset: Int,
        limit: Int,
    ): List<SongEntity> {
        return songDao.getSongListBySinger(singing, offset, limit)
            .map { it.toSongEntity() }
    }

    override suspend fun searchByTitleWithSinger(
        keyword: String,
        offset: Int,
        limit: Int,
    ): List<SongEntity> {
        return songDao.getSongListByTitleWithSinger(keyword, offset, limit)
            .map { it.toSongEntity() }
    }

    override suspend fun getPopularityList(): List<PopularitySongEntity> {
        val now = dateFormat.format(Calendar.getInstance().time)
        val updated = dateFormat.format(Calendar.getInstance()
            .apply { timeInMillis = preferencesRepository.popularityUpdatedFlow.first() }.time)

        Log.d(TAG, "getPopularityList: $now $updated")

        val data = songDao.getPopularitySongList().map { it.toPopularitySongEntity() }

        if (now != updated || data.isEmpty()) {
            preferencesRepository.updatePopularityUpdated()
            songDao.clearPopularitySongList()
            return karaokeService.getPopularSongList()
                .map { it.toPopularitySongEntity() }
                .onEach { songDao.insertPopularitySong(it.toPopularitySongPref()) }
        }

        return data
    }

    override suspend fun getLatestList(): List<SongEntity> {
        val now = dateFormat.format(Calendar.getInstance().time)
        val updated = dateFormat.format(Calendar.getInstance()
            .apply { timeInMillis = preferencesRepository.latestUpdatedFlow.first() }.time)

        val data = songDao.getLatestSongList().map { it.toSongEntity() }

        Log.d(TAG, "getLatestList: data = [$data]")

        if (now != updated || data.isEmpty()) {
            preferencesRepository.updateLatestUpdated()
            songDao.clearLatestSongList()
            return karaokeService.getNewSongList()
                .map { it.toSongEntity() }
                .onEach { songEntity: SongEntity ->
                    songDao.insertLatestSongPref(songEntity.toLatestSongPref())
                    songDao.insertSongPref(songEntity.toSongPref())
                }
        }
        return data
    }

    companion object {
        private val TAG = KaraokeRepositoryImpl::class.java.simpleName
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    }
}