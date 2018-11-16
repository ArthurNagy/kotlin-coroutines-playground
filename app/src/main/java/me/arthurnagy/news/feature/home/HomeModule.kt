package me.arthurnagy.news.feature.home

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val homeModule = module {
    viewModel { HomeViewModel(get(), get()) }
}