package com.example.newsapp.database

import androidx.room.TypeConverter
import com.example.newsapp.models.Source


class Converters {
    @TypeConverter
    fun nameToSource(value: String): Source {
        val source = Source()
        source.name = value
        return source
    }

    @TypeConverter
    fun sourceToName(source: Source): String {
        return source.name!!
    }
}