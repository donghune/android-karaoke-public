package com.github.donghune.presentation.dialog

import com.github.donghune.presentation.entity.PlayListModel
import com.github.donghune.presentation.entity.SongModel

sealed interface PlayListSelectDialogUiState {
    object Empty : PlayListSelectDialogUiState
    object Loading : PlayListSelectDialogUiState
    data class Success(
        val songModel: SongModel,
        val playListList: Map<PlayListModel, Boolean>
    ) : PlayListSelectDialogUiState

    data class Error(val throwable: Throwable) : PlayListSelectDialogUiState
}
