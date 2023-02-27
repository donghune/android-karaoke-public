package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.SongEntity
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

    suspend operator fun invoke(param: Param): List<SongEntity> {
        return repo.searchBySinger(
            singing = param.singing,
            offset = param.offset,
            limit = param.limit
        )
    }
}
