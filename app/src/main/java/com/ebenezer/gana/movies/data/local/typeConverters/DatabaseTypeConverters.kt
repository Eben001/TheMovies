package com.ebenezer.gana.movies.data.local.typeConverters

import androidx.room.TypeConverter
import java.util.*

class DatabaseTypeConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time


}