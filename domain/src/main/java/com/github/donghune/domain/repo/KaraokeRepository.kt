package com.github.donghune.domain.repo

import com.github.donghune.domain.entity.Song

interface KaraokeRepository {

    suspend fun searchByKeyWord(keyword: String, offset: Int, limit: Int): List<Song>

    suspend fun searchByNumber(id: Int, offset: Int, limit: Int): List<Song>

    suspend fun searchBySinger(singer: String, offset: Int, limit: Int): List<Song>

    suspend fun searchByTitleWithSinger(singer: String, offset: Int, limit: Int): List<Song>

    suspend fun getPopularityList(): List<Song>

    suspend fun getLatestList(): List<Song>
}
