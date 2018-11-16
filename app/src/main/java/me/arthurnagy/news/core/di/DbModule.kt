package me.arthurnagy.news.core.di

import androidx.room.Room
import me.arthurnagy.news.core.data.db.NewsDatabase
import org.koin.dsl.module.module

val dbModule = module {

    single {
        Room.databaseBuilder(get(), NewsDatabase::class.java, "news.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<NewsDatabase>().articleDao() }

    single { get<NewsDatabase>().sourceDao() }

}