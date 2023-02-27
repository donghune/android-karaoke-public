package com.github.donghune.domain.repo

import com.github.donghune.domain.entity.PopularitySongEntity
import com.github.donghune.domain.entity.SongEntity

interface KaraokeRepository {

    suspend fun searchByKeyWord(keyword: String, offset: Int, limit: Int): List<SongEntity>

    suspend fun searchByNumber(id: Int, offset: Int, limit: Int): List<SongEntity>

    suspend fun searchBySinger(singing: String, offset: Int, limit: Int): List<SongEntity>

    suspend fun searchByTitleWithSinger(singing: String, offset: Int, limit: Int): List<SongEntity>

    suspend fun getPopularityList(): List<PopularitySongEntity>

    suspend fun getLatestList(): List<SongEntity>
}
