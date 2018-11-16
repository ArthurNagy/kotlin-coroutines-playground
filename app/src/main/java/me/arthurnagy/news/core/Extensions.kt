package me.arthurnagy.news.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
import me.arthurnagy.news.core.data.Resource
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

val Any?.exhaustive get() = Unit

inline fun <T> LiveData<T>.observeNonNull(lifecycleOwner: LifecycleOwner, crossinline observer: (T) -> Unit) {
    this.observe(lifecycleOwner, Observer { it?.let(observer) })
}

suspend fun <T> CoroutineScope.suspendBlockResource(block: suspend (CoroutineScope.() -> T)): Resource<T> {
    return try {
        Resource.Success(block())
    } catch (exception: Exception) {
        Resource.Error(exception)
    }
}

suspend fun <T> withContextResource(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> T
): Resource<T> = withContext(context) {
    suspendBlockResource(block)
}

suspend fun <T> CoroutineScope.asyncResource(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<Resource<T>> = async(context, start) {
    suspendBlockResource(block)
}

suspend fun <T> Deferred<T>.awaitResource(): Resource<T> = try {
    Resource.Success(await())
} catch (exception: Exception) {
    Resource.Error(exception)
}