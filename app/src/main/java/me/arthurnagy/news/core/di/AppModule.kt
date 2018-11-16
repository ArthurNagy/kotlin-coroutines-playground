package me.arthurnagy.news.core.di

import me.arthurnagy.news.core.AppDispatchers
import org.koin.dsl.module.module

val appModule = module {
    single { AppDispatchers() }
}