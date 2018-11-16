package me.arthurnagy.news.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.arthurnagy.news.ArticleItemBinding
import me.arthurnagy.news.core.data.article.Article

class ArticlesAdapter : ListAdapter<Article, ArticlesAdapter.ArticleViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleItemBinding.inflate(LayoutInflater.from(parent.context))
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class ArticleViewHolder(binding: ArticleItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    companion object {
        private val diffUtilCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }

}