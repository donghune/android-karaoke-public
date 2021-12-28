package com.github.donghune.presentation.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.AddGroupUseCase
import com.github.donghune.domain.usecase.GetGroupsUseCase
import com.github.donghune.domain.usecase.RemoveGroupUseCase
import com.github.donghune.presentation.base.BaseViewModel
import com.github.donghune.presentation.entity.GroupModel
import com.github.donghune.presentation.entity.toGroupEntity
import com.github.donghune.presentation.entity.toGroupModel
import com.github.donghune.presentation.state.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PlayListViewModel @Inject constructor(
    private val getGroupsUseCase: GetGroupsUseCase,
    private val addGroupUseCase: AddGroupUseCase,
    private val removeGroupUseCase: RemoveGroupUseCase,
) : BaseViewModel() {

    private var _playerList = MutableLiveData<List<GroupModel>>()
    val playList: LiveData<List<GroupModel>>
        get() = _playerList

    fun getPlayListGroup() {
        getGroupsUseCase()
            .onStart { updateLoadState(LoadState.Loading) }
            .map { list -> list.map { entity -> entity.toGroupModel() } }
            .onEach { _playerList.postValue(it) }
            .onCompletion { updateLoadState(LoadState.Complete) }
            .catch { onError(it) }
            .launchIn(viewModelScope)
    }

    fun addPlayListGroup(groupName: String) {
        val params = AddGroupUseCase.Params(GroupModel(-1, groupName, listOf()).toGroupEntity())

        addGroupUseCase(params)
            .onStart { updateLoadState(LoadState.Loading) }
            .onCompletion { updateLoadState(LoadState.Complete) }
            .catch { onError(it) }
            .launchIn(viewModelScope)
    }

    fun removePlayListGroup(groupModel: GroupModel) {
        val params = RemoveGroupUseCase.Params(groupModel.toGroupEntity())

        removeGroupUseCase(params)
            .onStart { updateLoadState(LoadState.Loading) }
            .onCompletion { updateLoadState(LoadState.Complete) }
            .catch { onError(it) }
            .launchIn(viewModelScope)
    }

}