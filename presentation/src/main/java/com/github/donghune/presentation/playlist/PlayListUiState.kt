package com.github.donghune.presentation.playlist

import com.github.donghune.presentation.entity.GroupModel

sealed interface PlayListUiState {
    object Loading : PlayListUiState
    data class Success(val groups: List<GroupModel>) : PlayListUiState
    data class Error(val throwable: Throwable) : PlayListUiState
}
