package me.arthurnagy.news.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.arthurnagy.news.ArticleItemBinding
import me.arthurnagy.news.core.data.article.Article

typealias OnItemSelected = (Int) -> Unit

class ArticlesAdapter : ListAdapter<Article, ArticlesAdapter.ArticleViewHolder>(diffUtilCallback) {

    private var onItemSelected: OnItemSelected? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val viewHolder = ArticleViewHolder.create(parent)
        viewHolder.setOnItemSelectedListener(onItemSelected)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnItemSelectedListener(onItemSelected: OnItemSelected) {
        this.onItemSelected = onItemSelected
    }

    class ArticleViewHolder private constructor(private val binding: ArticleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setOnItemSelectedListener(onItemSelected: OnItemSelected?) {
            onItemSelected?.let {
                binding.root.setOnClickListener {
                    onItemSelected(adapterPosition)
                }
            }
        }

        fun bind(article: Article) {
            binding.article = article
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