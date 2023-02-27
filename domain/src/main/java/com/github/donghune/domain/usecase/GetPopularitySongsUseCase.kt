package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.PopularitySongEntity
import com.github.donghune.domain.repo.KaraokeRepository
import javax.inject.Inject

class GetPopularitySongsUseCase @Inject constructor(
    private val karaokeRepository: KaraokeRepository
) {

    suspend operator fun invoke(): List<PopularitySongEntity> {
        return karaokeRepository.getPopularityList()
    }
}
