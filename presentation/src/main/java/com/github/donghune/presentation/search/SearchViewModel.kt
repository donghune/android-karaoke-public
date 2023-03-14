package com.github.donghune.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.song.*
import com.github.donghune.presentation.entity.SongModel
import com.github.donghune.presentation.entity.toSongModel
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
    private val getSongsByTitleWithSingerUseCase: GetSongsByTitleWithSingerUseCase,
    private val getLatestSongsUseCase: GetLatestSongsUseCase,
    private val getPopularitySongsUseCase: GetPopularitySongsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val uiState: StateFlow<SearchUiState>
        get() = _uiState

    private val _searchType = MutableStateFlow(SearchType.SINGER)
    val searchType: StateFlow<SearchType> = _searchType

    enum class SearchType {
        TITLE, SINGER, TITLE_WITH_SINGER;

        fun next(): SearchType {
            return when (this) {
                TITLE -> SINGER
                SINGER -> TITLE_WITH_SINGER
                TITLE_WITH_SINGER -> TITLE
            }
        }
    }

    init {
        viewModelScope.launch {
            val latestSongs = try {
                getLatestSongsUseCase().map { it.toSongModel() }.subList(0, 5)
            } catch (e: Exception) {
                emptyList()
            }

            val popularitySongs = getPopularitySongsUseCase()
                .map { SongModel(it.id, it.title, it.singer) }
                .subList(0, 5)

            _uiState.update {
                SearchUiState.InitLoad(
                    latestSongs,
                    popularitySongs
                )
            }
        }
    }

    fun updateSearchType(searchType: SearchType) {
        _searchType.update { searchType }
    }

    fun search(searchType: SearchType, keyword: String) {
        viewModelScope.launch {
            try {
                val result = createFlow(searchType, keyword).map { SongModel(it) }
                _uiState.update {
                    SearchUiState.SearchResult(
                        result
                    )
                }
            } catch (e: Exception) {
                _uiState.update { SearchUiState.Error(e) }
            }
        }
    }

    private suspend fun createFlow(searchType: SearchType, keyword: String) = when (searchType) {
        SearchType.TITLE -> getSongsByKeywordUseCase(
            GetSongsByKeywordUseCase.Param(keyword)
        )
        SearchType.SINGER -> getSongsBySingerUseCase(
            GetSongsBySingerUseCase.Param(keyword)
        )
        SearchType.TITLE_WITH_SINGER -> getSongsByTitleWithSingerUseCase(
            GetSongsByTitleWithSingerUseCase.Param(keyword)
        )
    }

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
    }
}
