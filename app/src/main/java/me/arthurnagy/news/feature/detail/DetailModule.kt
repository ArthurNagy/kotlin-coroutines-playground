package me.arthurnagy.news.feature.detail

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val detailModule = module {
    viewModel { params -> DetailViewModel(params[0], get(), get()) }
}