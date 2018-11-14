package me.arthurnagy.news.core.data.source

import kotlinx.coroutines.CoroutineDispatcher

class SourceRepository(
    private val dispatcher: CoroutineDispatcher,
    private val sourceDao: SourceDao
)