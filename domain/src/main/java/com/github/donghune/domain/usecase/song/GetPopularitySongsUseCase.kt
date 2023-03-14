package com.github.donghune.domain.usecase.song

import com.github.donghune.domain.entity.Song
import com.github.donghune.domain.repo.KaraokeRepository
import javax.inject.Inject

class GetPopularitySongsUseCase @Inject constructor(
    private val karaokeRepository: KaraokeRepository
) {

    suspend operator fun invoke(): List<Song> {
        return karaokeRepository.getPopularityList()
    }
}
