package com.github.donghune.presentation.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.GetGroupWithIncludeWhetherUseCase
import com.github.donghune.presentation.base.BaseViewModel
import com.github.donghune.presentation.entity.GroupModel
import com.github.donghune.presentation.entity.toGroupModel
import com.github.donghune.presentation.state.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class GroupSelectDialogViewModel @Inject constructor(
    private val getGroupWithIncludeWhetherUseCase: GetGroupWithIncludeWhetherUseCase
) : BaseViewModel() {

    private val _items = MutableLiveData<Map<GroupModel, Boolean>>()
    val items: LiveData<Map<GroupModel, Boolean>>
        get() = _items

    fun getItems(songId: Int) {
        val params = GetGroupWithIncludeWhetherUseCase.Params(songId)

        getGroupWithIncludeWhetherUseCase(params)
            .onStart { updateLoadState(LoadState.Loading) }
            .map { map -> map.mapKeys { it.key.toGroupModel() } }
            .onEach { _items.postValue(it) }
            .onCompletion { updateLoadState(LoadState.Complete) }
            .catch { onError(it) }
            .launchIn(viewModelScope)
    }
}
