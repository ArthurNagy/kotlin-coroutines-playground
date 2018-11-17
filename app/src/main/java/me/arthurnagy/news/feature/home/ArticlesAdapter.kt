package me.arthurnagy.news.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.util.ViewPreloadSizeProvider
import me.arthurnagy.news.ArticleItemBinding
import me.arthurnagy.news.R
import me.arthurnagy.news.base.GlideApp
import me.arthurnagy.news.core.data.article.Article

typealias OnItemSelected = (Int) -> Unit

class ArticlesAdapter : ListAdapter<Article, ArticlesAdapter.ArticleViewHolder>(diffUtilCallback), ListPreloader.PreloadModelProvider<Article> {

    val glideSizeProvider = ViewPreloadSizeProvider<Article>()

    private var onItemSelected: OnItemSelected? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val viewHolder = ArticleViewHolder.create(parent)
        viewHolder.setOnItemSelectedListener(onItemSelected)
        glideSizeProvider.setView(viewHolder.binding.image)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.binding.article = getItem(position)
    }

    override fun getPreloadItems(position: Int): MutableList<Article> = mutableListOf<Article>().apply { getItem(position)?.let { add(it) } }

    override fun getPreloadRequestBuilder(item: Article): RequestBuilder<*>? = recyclerView?.context?.let {
        GlideApp.with(it)
            .load(item.urlToImage)
            .transforms(CenterCrop(), RoundedCorners(it.resources.getDimensionPixelSize(R.dimen.content_padding)))
            .dontAnimate()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun setOnItemSelectedListener(onItemSelected: OnItemSelected) {
        this.onItemSelected = onItemSelected
    }

    class ArticleViewHolder private constructor(val binding: ArticleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setOnItemSelectedListener(onItemSelected: OnItemSelected?) {
            onItemSelected?.let {
                binding.root.setOnClickListener {
                    onItemSelected(adapterPosition)
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup): ArticleViewHolder =
                ArticleViewHolder(ArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

    }

    companion object {
        private val diffUtilCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem == newItem

        }
    }

}