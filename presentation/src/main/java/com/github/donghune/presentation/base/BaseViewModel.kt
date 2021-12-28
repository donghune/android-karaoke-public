package com.github.donghune.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.github.donghune.presentation.state.LoadState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel : ViewModel() {
    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception>
        get() = _error

    private val _loadState = MutableLiveData<LoadState>()
    val loadState: LiveData<LoadState>
        get() = _loadState

    protected fun updateLoadState(state: LoadState) {
        _loadState.value = state
    }

    open fun onError(t: Throwable) {
        _error.postValue(Exception(t))
    }

    protected fun <T> Flow<T>.asLiveData(
        context: CoroutineContext = EmptyCoroutineContext,
        timeoutInMs: Long = 5000L,
    ): LiveData<T> = liveData(context, timeoutInMs) {
        catch { onError(it) }.collect { emit(it) }
    }
}