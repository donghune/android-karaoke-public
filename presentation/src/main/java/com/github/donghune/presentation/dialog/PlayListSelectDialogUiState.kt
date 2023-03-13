package com.github.donghune.presentation.dialog

import com.github.donghune.presentation.entity.PlayListModel

sealed interface PlayListSelectDialogUiState {
    object Empty : PlayListSelectDialogUiState
    object Loading : PlayListSelectDialogUiState
    data class Success(
        val songId: Int,
        val playListList: Map<PlayListModel, Boolean>
    ) : PlayListSelectDialogUiState

    data class Error(val throwable: Throwable) : PlayListSelectDialogUiState
}
