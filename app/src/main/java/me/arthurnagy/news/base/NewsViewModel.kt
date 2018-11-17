package me.arthurnagy.news.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import me.arthurnagy.news.core.AppDispatchers
import kotlin.coroutines.CoroutineContext

open class NewsViewModel(protected val dispatchers: AppDispatchers) : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = dispatchers.main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}