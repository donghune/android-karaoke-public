package com.github.donghune.presentation.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.AddPlayListUseCase
import com.github.donghune.domain.usecase.GetPlayListsUseCase
import com.github.donghune.domain.usecase.RemovePlayListUseCase
import com.github.donghune.presentation.entity.PlayListModel
import com.github.donghune.presentation.entity.toPlayListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayListViewModel @Inject constructor(
    getPlayListsUseCase: GetPlayListsUseCase,
    private val addPlayListUseCase: AddPlayListUseCase,
    private val removePlayListUseCase: RemovePlayListUseCase
) : ViewModel() {

    val PlayLists = getPlayListsUseCase().map { it.map { it.toPlayListModel() } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addPlayListPlayList(PlayListName: String) {
        viewModelScope.launch {
            val params = AddPlayListUseCase.Params(PlayListName)
            addPlayListUseCase(params)
        }
    }

    fun removePlayListPlayList(PlayListModel: PlayListModel) {
        viewModelScope.launch {
            val params = RemovePlayListUseCase.Params(PlayListModel.name)
            removePlayListUseCase(params)
        }
    }
}
