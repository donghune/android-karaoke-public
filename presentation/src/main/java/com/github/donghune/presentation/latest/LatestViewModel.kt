package com.github.donghune.presentation.latest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.GetLatestSongsUseCase
import com.github.donghune.presentation.base.BaseViewModel
import com.github.donghune.presentation.entity.SongModel
import com.github.donghune.presentation.state.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LatestViewModel @Inject constructor(
    private val getLatestSongsUseCase: GetLatestSongsUseCase,
) : BaseViewModel() {

    private var _songList = MutableLiveData<List<SongModel>>(listOf())
    val songList: LiveData<List<SongModel>>
        get() = _songList

    fun getLatestSongList() {
        getLatestSongsUseCase()
            .onStart { updateLoadState(LoadState.Loading) }
            .map { list -> list.map { entity -> SongModel(entity) } }
            .onEach { _songList.postValue(it) }
            .onCompletion { updateLoadState(LoadState.Complete) }
            .catch { onError(it) }
            .launchIn(viewModelScope)
    }

    companion object {
        private val TAG = LatestViewModel::class.java.simpleName
    }

}