package com.github.donghune.presentation.state

sealed class LoadState {
    object Initialize : LoadState()
    object Loading : LoadState()
    object Complete : LoadState()
    object Error : LoadState()
}
