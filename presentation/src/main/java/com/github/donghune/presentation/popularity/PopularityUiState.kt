package com.github.donghune.presentation.popularity

import com.github.donghune.presentation.entity.PopularitySongModel

sealed interface PopularityUiState {
    object Loading : PopularityUiState
    data class Success(val groups: List<PopularitySongModel>) : PopularityUiState
    data class Error(val throwable: Throwable) : PopularityUiState
}
