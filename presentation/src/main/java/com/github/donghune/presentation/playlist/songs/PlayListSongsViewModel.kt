package com.github.donghune.presentation.playlist.songs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.GetPlayListSongsUseCase
import com.github.donghune.presentation.entity.SongModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PlayListSongsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPlayListSongs: GetPlayListSongsUseCase
) : ViewModel() {
    private val playListId = savedStateHandle.get<Int>("playListId") ?: 0

    val songs = getPlayListSongs(GetPlayListSongsUseCase.Params(playListId))
        .map { list -> list.map { entity -> SongModel(entity) } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
