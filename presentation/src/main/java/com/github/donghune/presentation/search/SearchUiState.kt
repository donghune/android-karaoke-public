package com.github.donghune.presentation.search

import com.github.donghune.presentation.entity.SongModel

sealed interface SearchUiState {
    object Loading : SearchUiState
    data class InitLoad(
        val latestSongs: List<SongModel> = emptyList(),
        val popularitySongs: List<SongModel> = emptyList()
    ) : SearchUiState

    data class SearchResult(
        val searchSongs: List<SongModel> = emptyList()
    ) : SearchUiState

    data class Error(val throwable: Throwable) : SearchUiState
}
