package com.example.kotlincoroutinesplayground

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

open class CoroutineViewModel : ViewModel() {

    private val job = Job()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    protected fun launchWithParent(
        context: CoroutineContext = BACKGROUND,
        block: suspend CoroutineScope.() -> Unit
    ) = launch(context = context, parent = job, block = block)

}

