package com.github.donghune.data.remote.network

import com.github.donghune.data.remote.response.CrawlingSongResponse

interface KaraokeCrawlingService {
    suspend fun getPopularSongList(): List<CrawlingSongResponse>

    suspend fun getNewSongList(): List<CrawlingSongResponse>
}

