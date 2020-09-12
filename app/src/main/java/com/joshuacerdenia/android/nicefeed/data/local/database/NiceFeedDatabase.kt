package com.joshuacerdenia.android.nicefeed.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedEntryCrossRef

@Database(
    entities = [
        Feed::class,
        Entry::class,
        FeedEntryCrossRef::class
    ],
    version = 1
)
@TypeConverters(com.joshuacerdenia.android.nicefeed.data.local.database.TypeConverters::class)
abstract class NiceFeedDatabase : RoomDatabase() {

    abstract fun combinedDao(): CombinedDao
}