package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.PopularitySong
import com.github.donghune.domain.repo.KaraokeRepository
import javax.inject.Inject

class GetPopularitySongsUseCase @Inject constructor(
    private val karaokeRepository: KaraokeRepository
) {

    suspend operator fun invoke(): List<PopularitySong> {
        return karaokeRepository.getPopularityList()
    }
}
