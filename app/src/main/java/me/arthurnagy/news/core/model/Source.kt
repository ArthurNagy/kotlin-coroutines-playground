package me.arthurnagy.news.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.arthurnagy.news.core.model.Source.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Source(
    val id: String?,
    @PrimaryKey val name: String
) {
    companion object {
        const val TABLE_NAME = "source"
    }
}