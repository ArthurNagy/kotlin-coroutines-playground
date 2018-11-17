package me.arthurnagy.news.feature.detail

import android.content.Context
import android.net.Uri
import android.text.style.ClickableSpan
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import me.arthurnagy.news.R

class ArticleClickableSpan(private val articleUrl: String?) : ClickableSpan() {
    override fun onClick(widget: View) {
        openArticle(widget.context, articleUrl)
    }
}

fun openArticle(context: Context, articleUrl: String?) {
    articleUrl?.let {
        CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(ContextCompat.getColor(context, R.color.primary))
            .build()
            .launchUrl(context, Uri.parse(it))
    }
}