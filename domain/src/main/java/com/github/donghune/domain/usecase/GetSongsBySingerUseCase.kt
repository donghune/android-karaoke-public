package com.github.donghune.domain.usecase

import com.github.donghune.domain.repo.KaraokeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSongsBySingerUseCase @Inject constructor(
    val repo: KaraokeRepository,
) {
    data class Param(
        val singing: String,
        val offset: Int,
        val limit: Int,
    )

    operator fun invoke(param: Param) = flow {
        emit(
            repo.searchBySinger(
                singing = param.singing,
                offset = param.offset,
                limit = param.limit
            )
        )
    }
}