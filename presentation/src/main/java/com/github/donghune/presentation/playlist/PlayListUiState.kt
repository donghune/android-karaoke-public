package com.github.donghune.presentation.playlist

import com.github.donghune.presentation.entity.PlayListModel

sealed interface PlayListUiState {
    object Loading : PlayListUiState
    data class Success(val PlayLists: List<PlayListModel>) : PlayListUiState
    data class Error(val throwable: Throwable) : PlayListUiState
}
