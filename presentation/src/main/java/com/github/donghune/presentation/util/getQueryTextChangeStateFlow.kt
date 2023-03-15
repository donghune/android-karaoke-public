package com.github.donghune.presentation.util

import android.widget.EditText
import androidx.annotation.CheckResult
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

@CheckResult
fun EditText.addTextChangeStateFlow(): Flow<String?> {
    return callbackFlow {
        val listener = doOnTextChanged { text, _, _, _ -> trySend(text.toString()) }
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text.toString()) }
}
