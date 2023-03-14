package com.github.donghune.data.repo

import com.github.donghune.data.mapper.toSong
import com.github.donghune.data.remote.network.KaraokeCrawlingService
import com.github.donghune.data.remote.network.MananaKaraokeService
import com.github.donghune.data.remote.response.CrawlingSongResponse
import com.github.donghune.data.remote.response.MananaSongResponse
import com.github.donghune.domain.entity.Song
import com.github.donghune.domain.repo.KaraokeRepository
import javax.inject.Inject

class KaraokeRepositoryImpl @Inject constructor(
    private val mananaKaraokeService: MananaKaraokeService,
    private val crawlingService: KaraokeCrawlingService
) : KaraokeRepository {

    override suspend fun searchByTitle(title: String): List<Song> {
        return mananaKaraokeService.searchByTitle(title)
            .map(MananaSongResponse::toSong)
    }

    override suspend fun searchByNumber(id: Int): List<Song> {
        return mananaKaraokeService.searchByNo(id.toString())
            .map(MananaSongResponse::toSong)
    }

    override suspend fun searchBySinger(singer: String): List<Song> {
        return mananaKaraokeService.searchBySinger(singer)
            .map(MananaSongResponse::toSong)
    }

    override suspend fun getPopularityList(): List<Song> {
        return mananaKaraokeService.getPopularSongs(period = MananaKaraokeService.Period.MONTHLY.value)
            .map(MananaSongResponse::toSong)
            .ifEmpty { crawlingService.getPopularSongList().map(CrawlingSongResponse::toSong) }
    }

    override suspend fun getLatestList(yearMonth: String): List<Song> {
        return mananaKaraokeService.getReleaseSongs(release = yearMonth)
            .map(MananaSongResponse::toSong)
            .ifEmpty { crawlingService.getNewSongList().map(CrawlingSongResponse::toSong) }
    }
}
