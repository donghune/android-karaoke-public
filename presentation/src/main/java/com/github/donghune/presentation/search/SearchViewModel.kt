package com.github.donghune.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.GetSongsByKeywordUseCase
import com.github.donghune.domain.usecase.GetSongsBySingerUseCase
import com.github.donghune.domain.usecase.GetSongsByTitleWithSingerUseCase
import com.github.donghune.presentation.entity.SongModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSongsByKeywordUseCase: GetSongsByKeywordUseCase,
    private val getSongsBySingerUseCase: GetSongsBySingerUseCase,
    private val getSongsByTitleWithSingerUseCase: GetSongsByTitleWithSingerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val uiState: StateFlow<SearchUiState>
        get() = _uiState

    sealed class SearchType {
        data class Keyword(val keyword: String, val offset: Int, val limit: Int) : SearchType()
        data class Singer(val singer: String, val offset: Int, val limit: Int) : SearchType()
        data class TitleWithSinger(val singing: String, val offset: Int, val limit: Int) :
            SearchType()
    }

    fun search(searchType: SearchType) {
        viewModelScope.launch {
            try {
                val result = createFlow(searchType).map { SongModel(it) }
                _uiState.update { SearchUiState.Success(result) }
            } catch (e: Exception) {
                _uiState.update { SearchUiState.Error(e) }
            }
        }
    }

    private suspend fun createFlow(searchType: SearchType) = when (searchType) {
        is SearchType.Keyword -> getSongsByKeywordUseCase(
            GetSongsByKeywordUseCase.Param(
                searchType.keyword,
                searchType.offset,
                searchType.limit
            )
        )
        is SearchType.Singer -> getSongsBySingerUseCase(
            GetSongsBySingerUseCase.Param(
                searchType.singer,
                searchType.offset,
                searchType.limit
            )
        )
        is SearchType.TitleWithSinger -> getSongsByTitleWithSingerUseCase(
            GetSongsByTitleWithSingerUseCase.Param(
                searchType.singing,
                searchType.offset,
                searchType.limit
            )
        )
    }

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
    }
}
