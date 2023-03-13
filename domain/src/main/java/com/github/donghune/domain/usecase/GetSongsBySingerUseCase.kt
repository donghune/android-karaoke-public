package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.Song
import com.github.donghune.domain.repo.KaraokeRepository
import javax.inject.Inject

class GetSongsBySingerUseCase @Inject constructor(
    val repo: KaraokeRepository
) {
    data class Param(
        val singing: String,
        val offset: Int,
        val limit: Int
    )

    suspend operator fun invoke(param: Param): List<Song> {
        return repo.searchBySinger(
            singing = param.singing,
            offset = param.offset,
            limit = param.limit
        )
    }
}
