package com.joshuacerdenia.android.nicefeed

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.joshuacerdenia.android.nicefeed.database.Database
import java.util.concurrent.Executors

private const val DATABASE_NAME = "database"

class Repository private constructor(context: Context) {

    companion object {
        private var INSTANCE: Repository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = Repository(context)
            }
        }

        fun get(): Repository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized!")
        }
    }

    private val feedSearcher = FeedSearcher()

    private val database: Database = Room.databaseBuilder(
        context.applicationContext,
        Database::class.java,
        DATABASE_NAME
    ).build()

    private val feedDao = database.feedDao()
    private val entryDao = database.entryDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun performSearch(query: String): LiveData<List<SearchResultItem>>
            = feedSearcher.performSearch(query)

    fun getFeeds(): LiveData<List<Feed>> = feedDao.getFeeds()

    fun getFeed(website: String): LiveData<Feed?> = feedDao.getFeed(website)

    fun getFeedWithEntries(website: String): LiveData<FeedWithEntries> =
        feedDao.getFeedWithEntries(website)

    fun getEntries(): LiveData<List<Entry>> = entryDao.getEntries()

    fun getEntriesByFeed(website: String): LiveData<List<Entry>> =
        entryDao.getEntriesByFeed(website)

    fun getEntry(guid: String): LiveData<Entry?> = entryDao.getEntry(guid)

    fun updateFeed(feed: Feed) {
        executor.execute {
            feedDao.updateFeed(feed)
        }
    }

    fun updateEntry(entry: Entry) {
        executor.execute {
            entryDao.updateEntry(entry)
        }
    }

    fun addFeed(feed: Feed) {
        executor.execute {
            feedDao.addFeed(feed)
        }
    }

    fun addFeedWithEntries(feedWithEntries: FeedWithEntries) {
        executor.execute {
            feedDao.addFeed(feedWithEntries.feed)
            entryDao.addEntries(feedWithEntries.entries)
        }
    }

    fun addEntry(entry: Entry) {
        executor.execute {
            entryDao.addEntry(entry)
        }
    }

    fun addEntries(entries: List<Entry>) {
        executor.execute {
            entryDao.addEntries(entries)
        }
    }

    fun deleteFeed(feed: Feed) {
        executor.execute {
            feedDao.deleteFeed(feed)
        }
    }

    fun deleteFeedAndEntries(feed: Feed, entries: List<Entry>) {
        executor.execute {
            feedDao.deleteFeedAndEntries(feed, entries)
        }
    }

    fun deleteEntry(entry: Entry) {
        executor.execute {
            entryDao.deleteEntry(entry)
        }
    }
}