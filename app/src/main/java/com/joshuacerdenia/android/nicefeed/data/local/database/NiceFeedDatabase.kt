package com.joshuacerdenia.android.nicefeed.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.joshuacerdenia.android.nicefeed.data.model.entry.Entry
import com.joshuacerdenia.android.nicefeed.data.model.feed.Feed
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedEntryCrossRef

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

    companion object {
        private const val DATABASE_NAME = "database"

        fun build(context: Context): NiceFeedDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                NiceFeedDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}