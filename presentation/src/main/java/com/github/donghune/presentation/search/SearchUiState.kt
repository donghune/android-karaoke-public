package com.github.donghune.presentation.search

import com.github.donghune.presentation.entity.SongModel

sealed interface SearchUiState {
    object Loading : SearchUiState
    data class Success(val songs: List<SongModel>) : SearchUiState
    data class Error(val throwable: Throwable) : SearchUiState
}
