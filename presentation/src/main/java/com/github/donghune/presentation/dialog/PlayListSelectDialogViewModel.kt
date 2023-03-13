package com.github.donghune.presentation.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.GetPlayListWithIncludeWhetherUseCase
import com.github.donghune.domain.usecase.SetPlayListForSong
import com.github.donghune.presentation.entity.toPlayListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayListSelectDialogViewModel @Inject constructor(
    private val getPlayListWithIncludeWhetherUseCase: GetPlayListWithIncludeWhetherUseCase,
    private val setPlayListForSong: SetPlayListForSong
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<PlayListSelectDialogUiState>(PlayListSelectDialogUiState.Empty)
    val uiState: StateFlow<PlayListSelectDialogUiState> = _uiState

    fun getPlayListWithIncludeWhether(songId: Int) {
        viewModelScope.launch {
            try {
                val params = GetPlayListWithIncludeWhetherUseCase.Params(songId)
                val result = getPlayListWithIncludeWhetherUseCase(params)
                val playListList = result.map { it.key.toPlayListModel() to it.value }
                _uiState.update { PlayListSelectDialogUiState.Success(songId, playListList.toMap()) }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { PlayListSelectDialogUiState.Error(e) }
            }
        }
    }

    fun setPlayListForSong(songId: Int, playListId: Int, isChecked: Boolean) {
        viewModelScope.launch {
            try {
                val params = SetPlayListForSong.Params(songId, playListId, isChecked)
                setPlayListForSong(params)
            } catch (e: Exception) {
                _uiState.update { PlayListSelectDialogUiState.Error(e) }
            }
        }
    }

    fun dismissDialog() {
        _uiState.update { PlayListSelectDialogUiState.Empty }
    }
}
