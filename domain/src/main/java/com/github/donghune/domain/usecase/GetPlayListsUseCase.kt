package com.github.donghune.domain.usecase

import com.github.donghune.domain.entity.PlayList
import com.github.donghune.domain.repo.PlayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlayListsUseCase @Inject constructor(
    private val playListRepository: PlayListRepository
) {

    operator fun invoke(): Flow<List<PlayList>> {
        return playListRepository.getPlayListFlow()
    }
}
