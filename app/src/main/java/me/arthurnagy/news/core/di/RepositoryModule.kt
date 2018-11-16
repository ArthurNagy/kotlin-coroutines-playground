package me.arthurnagy.news.core.di

import me.arthurnagy.news.core.data.article.ArticleRepository
import me.arthurnagy.news.core.data.source.SourceRepository
import org.koin.dsl.module.module

val repositoryModule = module {

    single { ArticleRepository(get(), get()) }

    single { SourceRepository(get(), get()) }

}