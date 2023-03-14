package com.github.donghune.domain.usecase.song

import com.github.donghune.domain.entity.Song
import com.github.donghune.domain.repo.KaraokeRepository
import javax.inject.Inject

class GetSongsByTitleWithSingerUseCase @Inject constructor(
    private val repository: KaraokeRepository
) {

    data class Param(
        val keyword: String
    )

    suspend operator fun invoke(param: Param): List<Song> {
        return listOf(
            repository.searchByTitle(
                title = param.keyword
            ),
            repository.searchBySinger(
                singer = param.keyword
            )
        ).flatten()
    }
}
