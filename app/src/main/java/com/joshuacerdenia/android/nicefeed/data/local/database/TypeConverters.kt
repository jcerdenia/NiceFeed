package com.joshuacerdenia.android.nicefeed.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

class TypeConverters {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun fromStringList(stringList: List<String>?): String {
        return Gson().toJson(stringList)

    }

    @TypeConverter
    fun toStringList(string: String): List<String>? {
        return Gson().fromJson(string, Array<String>::class.java).toList()
    }
}