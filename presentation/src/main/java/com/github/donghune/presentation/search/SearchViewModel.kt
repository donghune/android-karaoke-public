package com.github.donghune.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.GetSongsByKeywordUseCase
import com.github.donghune.domain.usecase.GetSongsBySingerUseCase
import com.github.donghune.domain.usecase.GetSongsByTitleWithSingerUseCase
import com.github.donghune.presentation.base.BaseViewModel
import com.github.donghune.presentation.entity.SongModel
import com.github.donghune.presentation.state.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSongsByKeywordUseCase: GetSongsByKeywordUseCase,
    private val getSongsBySingerUseCase: GetSongsBySingerUseCase,
    private val getSongsByTitleWithSingerUseCase: GetSongsByTitleWithSingerUseCase,
) : BaseViewModel() {

    private var _songList = MutableLiveData<List<SongModel>>(listOf())
    val songList: LiveData<List<SongModel>>
        get() = _songList

    sealed class SearchType {
        data class Keyword(val keyword: String, val offset: Int, val limit: Int) : SearchType()
        data class Singer(val singer: String, val offset: Int, val limit: Int) : SearchType()
        data class TitleWithSinger(val singing: String, val offset: Int, val limit: Int) :
            SearchType()
    }

    fun search(searchType: SearchType) {
        createFlow(searchType)
            .onStart { updateLoadState(LoadState.Loading) }
            .map { list -> list.map { entity -> SongModel(entity) } }
            .onEach { songList -> _songList.value = songList }
            .onCompletion { updateLoadState(LoadState.Complete) }
            .catch { throwable -> onError(throwable) }
            .launchIn(viewModelScope)
    }

    private fun createFlow(searchType: SearchType) = when (searchType) {
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