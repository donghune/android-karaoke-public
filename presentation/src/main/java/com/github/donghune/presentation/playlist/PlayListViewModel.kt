package com.github.donghune.presentation.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.AddGroupUseCase
import com.github.donghune.domain.usecase.GetGroupsUseCase
import com.github.donghune.domain.usecase.RemoveGroupUseCase
import com.github.donghune.presentation.entity.GroupModel
import com.github.donghune.presentation.entity.toGroupEntity
import com.github.donghune.presentation.entity.toGroupModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayListViewModel @Inject constructor(
    private val getGroupsUseCase: GetGroupsUseCase,
    private val addGroupUseCase: AddGroupUseCase,
    private val removeGroupUseCase: RemoveGroupUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PlayListUiState>(PlayListUiState.Loading)
    val uiState: StateFlow<PlayListUiState>
        get() = _uiState

    fun getPlayListGroup() {
        viewModelScope.launch {
            val groups = getGroupsUseCase().map { it.toGroupModel() }
            _uiState.update { PlayListUiState.Success(groups) }
        }
    }

    fun addPlayListGroup(groupName: String) {
        viewModelScope.launch {
            try {
                val params =
                    AddGroupUseCase.Params(GroupModel(-1, groupName, listOf()).toGroupEntity())
                addGroupUseCase(params)
            } catch (e: Exception) {
                _uiState.update { PlayListUiState.Error(e) }
            }
        }
    }

    fun removePlayListGroup(groupModel: GroupModel) {
        viewModelScope.launch {
            try {
                val params = RemoveGroupUseCase.Params(groupModel.toGroupEntity())
                removeGroupUseCase(params)
            } catch (e: Exception) {
                _uiState.update { PlayListUiState.Error(e) }
            }
        }
    }
}
