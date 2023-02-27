package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.SongEntity
import com.github.donghune.domain.repo.KaraokeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLatestSongsUseCase @Inject constructor(
    private val karaokeRepository: KaraokeRepository
) {

    operator fun invoke(): Flow<List<SongEntity>> = flow {
        emit(
            karaokeRepository.getLatestList()
        )
    }
}
