package com.github.donghune.domain.repo

import com.github.donghune.domain.entity.Song

interface KaraokeRepository {

    suspend fun searchByTitle(title: String): List<Song>

    suspend fun searchByNumber(id: Int): List<Song>

    suspend fun searchBySinger(singer: String): List<Song>

    suspend fun getPopularityList(): List<Song>

    suspend fun getLatestList(yearMonth: String): List<Song>
}
