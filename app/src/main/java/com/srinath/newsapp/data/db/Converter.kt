package com.srinath.newsapp.data.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.srinath.newsapp.data.model.Source

class Converter {
    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String):Source{
        return Source(name,name)
    }
}