package com.github.donghune.presentation.dialog

import com.github.donghune.presentation.entity.GroupModel

sealed interface GroupSelectDialogUiState {
    object Loading : GroupSelectDialogUiState
    data class Success(val groupList: Map<GroupModel, Boolean>) : GroupSelectDialogUiState
    data class Error(val throwable: Throwable) : GroupSelectDialogUiState
}
