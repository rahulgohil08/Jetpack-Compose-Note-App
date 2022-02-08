package com.theworld.noteapp.utils


import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


/*------------------------------- Activity Level ---------------------------*/

fun <T> ComponentActivity.collectFlow(
    flow: Flow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    collect: suspend (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(lifecycleState) {
            flow.collect(collect)
        }
    }
}


fun <T> ComponentActivity.collectLatestFlow(
    flow: Flow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    collect: suspend (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(lifecycleState) {
            flow.collectLatest(collect)
        }
    }
}

fun <T> ComponentActivity.collectEachFlow(
    flow: Flow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    collect: suspend (T) -> Unit
) {

    flow.flowWithLifecycle(lifecycle, lifecycleState)
        .onEach(collect).launchIn(lifecycleScope)
}