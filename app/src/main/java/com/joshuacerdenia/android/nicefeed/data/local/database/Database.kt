package com.joshuacerdenia.android.nicefeed.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed

@Database(entities = [ Feed::class, Entry::class ], version=1)
@TypeConverters(com.joshuacerdenia.android.nicefeed.data.local.database.TypeConverters::class)
abstract class Database : RoomDatabase() {
    abstract fun feedDao(): FeedDao
    abstract fun entryDao(): EntryDao
}