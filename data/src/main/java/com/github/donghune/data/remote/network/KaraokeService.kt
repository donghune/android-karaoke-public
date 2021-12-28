package com.github.donghune.data.remote.network

import com.github.donghune.domain.entity.SearchType
import com.github.donghune.data.remote.response.SongResponse
import com.github.donghune.data.remote.response.PopularitySongResponse

interface KaraokeService {

    suspend fun getSongList(
        search: String,
        type: SearchType = SearchType.SINGER,
        page: Int = 0,
        count: Int = 0
    ): List<SongResponse>

    suspend fun getPopularSongList(): List<PopularitySongResponse>

    suspend fun getNewSongList(): List<SongResponse>

}