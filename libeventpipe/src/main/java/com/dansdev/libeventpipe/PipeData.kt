package com.dansdev.libeventpipe

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

data class PipeData<T>(
    val eventDispatcher: CoroutineDispatcher,
    val onEvent: suspend (T) -> Unit
) : CoroutineScope {

    private val sharedFlow = MutableSharedFlow<T>()
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job

    init {
        launch {
            sharedFlow.collect { launch(eventDispatcher) { onEvent(it) } }
        }
    }

    fun send(event: Any) {
        launch {
            delay(1)
            sharedFlow.emit(event as T)
        }
    }

    fun cancel() {
        job.cancel("Canceled by user")
    }
}
