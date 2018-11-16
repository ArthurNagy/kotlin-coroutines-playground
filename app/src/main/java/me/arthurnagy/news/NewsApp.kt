package me.arthurnagy.news

import android.app.Application
import me.arthurnagy.news.core.di.appModule
import me.arthurnagy.news.core.di.dbModule
import me.arthurnagy.news.core.di.networkModule
import me.arthurnagy.news.core.di.repositoryModule
import me.arthurnagy.news.feature.detail.detailModule
import me.arthurnagy.news.feature.home.homeModule
import org.koin.android.ext.android.startKoin

class NewsApp : Application() {

//    companion object {
//        init {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
//        }
//    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, networkModule, dbModule, repositoryModule, homeModule, detailModule))
    }

}