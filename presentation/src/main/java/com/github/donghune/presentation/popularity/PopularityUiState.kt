package com.github.donghune.presentation.popularity

import com.github.donghune.presentation.entity.SongModel

sealed interface PopularityUiState {
    object Loading : PopularityUiState
    data class Success(val playList: List<SongModel>) : PopularityUiState
    data class Error(val throwable: Throwable) : PopularityUiState
}
