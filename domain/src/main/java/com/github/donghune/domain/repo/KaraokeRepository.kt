package com.github.donghune.domain.repo

import com.github.donghune.domain.entity.PopularitySong
import com.github.donghune.domain.entity.Song

interface KaraokeRepository {

    suspend fun searchByKeyWord(keyword: String, offset: Int, limit: Int): List<Song>

    suspend fun searchByNumber(id: Int, offset: Int, limit: Int): List<Song>

    suspend fun searchBySinger(singing: String, offset: Int, limit: Int): List<Song>

    suspend fun searchByTitleWithSinger(singing: String, offset: Int, limit: Int): List<Song>

    suspend fun getPopularityList(): List<PopularitySong>

    suspend fun getLatestList(): List<Song>
}
