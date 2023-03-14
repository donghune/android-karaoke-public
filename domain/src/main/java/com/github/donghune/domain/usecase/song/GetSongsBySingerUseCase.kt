package com.github.donghune.domain.usecase.song

import com.github.donghune.domain.entity.Song
import com.github.donghune.domain.repo.KaraokeRepository
import javax.inject.Inject

class GetSongsBySingerUseCase @Inject constructor(
    val repo: KaraokeRepository
) {
    data class Param(
        val singer: String
    )

    suspend operator fun invoke(param: Param): List<Song> {
        return repo.searchBySinger(
            singer = param.singer
        )
    }
}
