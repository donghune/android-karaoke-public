package com.github.donghune.presentation.playlist.songs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.playlist.GetPlayListSongsUseCase
import com.github.donghune.presentation.entity.SongModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PlayListSongsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPlayListSongs: GetPlayListSongsUseCase
) : ViewModel() {
    private val playListId = savedStateHandle.get<Int>("EXTRA_PLAY_LIST_ID") ?: 0

    val songs = getPlayListSongs(GetPlayListSongsUseCase.Params(playListId))
        .map { list -> list.map { entity -> SongModel(entity) } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
