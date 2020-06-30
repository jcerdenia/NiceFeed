package com.joshuacerdenia.android.nicefeed.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joshuacerdenia.android.nicefeed.Entry
import com.joshuacerdenia.android.nicefeed.Feed

@Database(entities = [ Feed::class, Entry::class ], version=1)
abstract class Database : RoomDatabase() {
    abstract fun feedDao(): FeedDao
    abstract fun entryDao(): EntryDao
}