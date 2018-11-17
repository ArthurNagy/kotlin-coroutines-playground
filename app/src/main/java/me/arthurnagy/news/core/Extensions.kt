package me.arthurnagy.news.core

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.coroutines.*
import me.arthurnagy.news.R
import me.arthurnagy.news.core.data.Resource
import java.text.SimpleDateFormat
import java.util.*
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

@BindingAdapter(value = ["articleImage"])
fun ImageView.setArticleImage(image: String?) {
    this.isVisible = !image.isNullOrEmpty()
    if (!image.isNullOrEmpty()) {
        GlideApp.with(this)
            .load(image)
            .transforms(CenterCrop(), RoundedCorners(this.context.resources.getDimensionPixelSize(R.dimen.content_padding)))
            .dontAnimate()
            .into(this)
    }
}

@BindingAdapter(value = ["imageUrl"])
fun ImageView.imageUrl(image: String?) {
    GlideApp.with(this)
        .load(image)
        .into(this)
}

@BindingAdapter(value = ["articlePublishedAt"])
fun TextView.setArticlePublishedAtValue(publishedAt: String) {
    val publishedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(publishedAt)
    val resultFormatter = SimpleDateFormat("hh:mm dd.MM.yyyy", Locale.getDefault())
    text = resultFormatter.format(publishedDate)
}

