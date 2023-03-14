package com.github.donghune.presentation.popularity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.song.GetPopularitySongsUseCase
import com.github.donghune.presentation.entity.toSongModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularityViewModel @Inject constructor(
    private val getPopularitySongsUseCase: GetPopularitySongsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PopularityUiState>(PopularityUiState.Loading)
    val uiState: StateFlow<PopularityUiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            try {
                val songs = getPopularitySongsUseCase()
                    .map { entity -> entity.toSongModel() }
                _uiState.update { PopularityUiState.Success(songs) }
            } catch (e: Exception) {
                _uiState.update { PopularityUiState.Error(e) }
            }
        }
    }
}
