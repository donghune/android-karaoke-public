package com.github.donghune.presentation.latest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.song.GetLatestSongsUseCase
import com.github.donghune.presentation.entity.SongModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestViewModel @Inject constructor(
    private val getLatestSongsUseCase: GetLatestSongsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LatestUiState>(LatestUiState.Loading)
    val uiState: StateFlow<LatestUiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            try {
                val latestSongs = getLatestSongsUseCase()
                _uiState.update { LatestUiState.Success(latestSongs.map { SongModel(it) }) }
            } catch (e: Throwable) {
                e.printStackTrace()
                _uiState.update { LatestUiState.Error(e) }
            }
        }
    }
}
