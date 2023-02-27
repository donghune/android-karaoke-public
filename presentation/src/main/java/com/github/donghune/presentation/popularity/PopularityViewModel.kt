package com.github.donghune.presentation.popularity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.donghune.domain.usecase.GetPopularitySongsUseCase
import com.github.donghune.presentation.base.BaseViewModel
import com.github.donghune.presentation.entity.PopularitySongModel
import com.github.donghune.presentation.entity.toPopularitySongModel
import com.github.donghune.presentation.state.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PopularityViewModel @Inject constructor(
    private val getPopularitySongsUseCase: GetPopularitySongsUseCase
) : BaseViewModel() {

    private var _songList = MutableLiveData<List<PopularitySongModel>>(listOf())
    val songList: LiveData<List<PopularitySongModel>>
        get() = _songList

    fun getPopularitySongList() {
        getPopularitySongsUseCase()
            .onStart { updateLoadState(LoadState.Loading) }
            .map { list -> list.map { entity -> entity.toPopularitySongModel() } }
            .onEach { _songList.postValue(it) }
            .onCompletion { updateLoadState(LoadState.Complete) }
            .catch { onError(it) }
            .launchIn(viewModelScope)
    }
}
