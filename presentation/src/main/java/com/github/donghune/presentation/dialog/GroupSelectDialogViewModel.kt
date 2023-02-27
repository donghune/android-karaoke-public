package com.github.donghune.presentation.dialog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.GetGroupWithIncludeWhetherUseCase
import com.github.donghune.presentation.entity.toGroupModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupSelectDialogViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val getGroupWithIncludeWhetherUseCase: GetGroupWithIncludeWhetherUseCase
) : ViewModel() {

    private val songId = stateHandle.get<Int>("songId") ?: 0

    private val _uiState =
        MutableStateFlow<GroupSelectDialogUiState>(GroupSelectDialogUiState.Loading)
    val uiState: StateFlow<GroupSelectDialogUiState> = _uiState

    init {
        viewModelScope.launch {
            try {
                val params = GetGroupWithIncludeWhetherUseCase.Params(songId)

                val result = getGroupWithIncludeWhetherUseCase(params)
                val groupList = result.map { it.key.toGroupModel() to it.value }
                _uiState.update { GroupSelectDialogUiState.Success(groupList.toMap()) }
            } catch (e: Exception) {
                _uiState.update { GroupSelectDialogUiState.Error(e) }
            }
        }
    }
}
