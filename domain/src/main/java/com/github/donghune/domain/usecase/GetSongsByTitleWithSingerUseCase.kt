package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.Song
import com.github.donghune.domain.repo.KaraokeRepository
import javax.inject.Inject

class GetSongsByTitleWithSingerUseCase @Inject constructor(
    private val repository: KaraokeRepository
) {

    data class Param(
        val keyword: String,
        val offset: Int,
        val limit: Int
    )

    suspend operator fun invoke(param: Param): List<Song> {
        return repository.searchByTitleWithSinger(
            singer = param.keyword,
            offset = param.offset,
            limit = param.limit
        )
    }
}
