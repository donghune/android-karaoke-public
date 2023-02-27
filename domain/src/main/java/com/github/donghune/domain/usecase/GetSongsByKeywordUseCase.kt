package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.SongEntity
import com.github.donghune.domain.repo.KaraokeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSongsByKeywordUseCase @Inject constructor(
    val repo: KaraokeRepository
) {
    data class Param(
        val keyword: String,
        val offset: Int,
        val limit: Int
    )

    operator fun invoke(param: Param): Flow<List<SongEntity>> = flow {
        emit(
            repo.searchByKeyWord(
                keyword = param.keyword,
                offset = param.offset,
                limit = param.limit
            )
        )
    }
}
