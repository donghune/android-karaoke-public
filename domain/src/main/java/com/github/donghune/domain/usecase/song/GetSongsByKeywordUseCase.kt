package com.github.donghune.domain.usecase.song

import com.github.donghune.domain.entity.Song
import com.github.donghune.domain.repo.KaraokeRepository
import javax.inject.Inject

class GetSongsByKeywordUseCase @Inject constructor(
    val repo: KaraokeRepository
) {
    data class Param(
        val keyword: String
    )

    suspend operator fun invoke(param: Param): List<Song> {
        return repo.searchByTitle(
            title = param.keyword
        )
    }
}
