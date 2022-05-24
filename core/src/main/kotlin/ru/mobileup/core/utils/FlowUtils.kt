package ru.mobileup.core.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Преобразует StateFlow<T> в State<T> из Jetpack Compose
 */
fun <T> StateFlow<T>.toComposeState(coroutineScope: CoroutineScope): State<T> {
    val state: MutableState<T> = mutableStateOf(this.value)
    coroutineScope.launch {
        this@toComposeState.collect {
            state.value = it
        }
    }
    return state
}