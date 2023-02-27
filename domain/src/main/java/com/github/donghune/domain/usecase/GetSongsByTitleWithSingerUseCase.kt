package com.github.donghune.domain.usecase

import com.github.donghune.domain.repo.KaraokeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSongsByTitleWithSingerUseCase @Inject constructor(
    private val repository: KaraokeRepository
) {

    data class Param(
        val singing: String,
        val offset: Int,
        val limit: Int
    )

    operator fun invoke(param: Param) = flow {
        emit(
            repository.searchByTitleWithSinger(
                singing = param.singing,
                offset = param.offset,
                limit = param.limit
            )
        )
    }
}
