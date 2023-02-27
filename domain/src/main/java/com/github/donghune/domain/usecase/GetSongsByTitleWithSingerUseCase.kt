package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.SongEntity
import com.github.donghune.domain.repo.KaraokeRepository
import javax.inject.Inject

class GetSongsByTitleWithSingerUseCase @Inject constructor(
    private val repository: KaraokeRepository
) {

    data class Param(
        val singing: String,
        val offset: Int,
        val limit: Int
    )

    suspend operator fun invoke(param: Param): List<SongEntity> {
        return repository.searchByTitleWithSinger(
            singing = param.singing,
            offset = param.offset,
            limit = param.limit
        )
    }
}
