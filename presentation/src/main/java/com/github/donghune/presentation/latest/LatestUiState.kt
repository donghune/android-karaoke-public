package com.github.donghune.presentation.latest

import com.github.donghune.presentation.entity.SongModel

sealed interface LatestUiState {
    object Loading : LatestUiState
    data class Success(val songs: List<SongModel>) : LatestUiState
    data class Error(val throwable: Throwable) : LatestUiState
}
