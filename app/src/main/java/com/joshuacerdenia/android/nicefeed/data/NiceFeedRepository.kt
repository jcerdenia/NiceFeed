package com.joshuacerdenia.android.nicefeed.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.joshuacerdenia.android.nicefeed.data.local.database.NiceFeedDatabase
import com.joshuacerdenia.android.nicefeed.data.model.*
import com.joshuacerdenia.android.nicefeed.data.remote.FeedSearcher
import java.util.concurrent.Executors

private const val DATABASE_NAME = "database"

class NiceFeedRepository private constructor(context: Context) {

    companion object {
        private var INSTANCE: NiceFeedRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = NiceFeedRepository(context)
            }
        }

        fun get(): NiceFeedRepository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized!")
        }
    }
    
    private val database: NiceFeedDatabase = Room.databaseBuilder(
        context.applicationContext,
        NiceFeedDatabase::class.java,
        DATABASE_NAME
    ).build()
    
    private val dao = database.feedsAndEntriesDao()
    private val executor = Executors.newSingleThreadExecutor()
    private val searcher = FeedSearcher()

    fun performSearch(query: String): LiveData<List<SearchResultItem>> = searcher.performSearch(query)

    fun getCategories(): LiveData<List<String>> = dao.getCategories()

    fun getFeeds(): LiveData<List<Feed>> = dao.getAllFeeds()

    fun getFeedIds(): LiveData<List<String>> = dao.getAllFeedIds()

    fun getFeedsInfo(): LiveData<List<FeedMinimal>> = dao.getAllFeedsInfo()

    fun getFeedUnreadCount(feedId: String): LiveData<Int> = dao.getFeedUnreadCount(feedId)

    fun getFeedWithEntriesById(feedId: String): LiveData<FeedWithEntries> = dao.getFeedWithEntriesById(feedId)

    fun getEntries(): LiveData<List<Entry>> = dao.getEntries()

    fun getEntriesByFeedId(feedId: String): LiveData<List<Entry>> = dao.getEntriesByFeedId(feedId)

    fun getEntry(guid: String): LiveData<Entry?> = dao.getEntry(guid)

    fun updateFeed(feed: Feed) {
        executor.execute {
            dao.updateFeed(feed)
        }
    }

    fun updateFeeds(feeds: List<Feed>) {
        executor.execute {
            dao.updateFeeds(feeds)
        }
    }

    fun updateFeedUnreadCountById(id: String, count: Int) {
        executor.execute {
            dao.updateFeedUnreadCountById(id, count)
        }
    }

    fun updateEntry(entry: Entry) {
        executor.execute {
            dao.updateEntry(entry)
        }
    }

    fun updateEntries(entries: List<Entry>) {
        executor.execute {
            dao.updateEntries(entries)
        }
    }

    fun addFeed(feed: Feed) {
        executor.execute {
            dao.addFeed(feed)
        }
    }

    fun addFeedWithEntries(feedWithEntries: FeedWithEntries) {
        executor.execute {
            dao.addFeed(feedWithEntries.feed)
            dao.addEntries(feedWithEntries.entries)
        }
    }

    fun addEntry(entry: Entry) {
        executor.execute {
            dao.addEntry(entry)
        }
    }

    fun addEntries(entries: List<Entry>) {
        executor.execute {
            dao.addEntries(entries)
        }
    }

    fun refreshEntries(
        toAdd: List<Entry>,
        toUpdate: List<Entry>,
        toDelete: List<Entry>
    ) {
        executor.execute {
            dao.refreshEntries(toAdd, toUpdate, toDelete)
        }
    }

    fun updateEntryIsReadAndFeedUnreadCount(
        id: String,
        isRead: Boolean,
        operator: Int
    ) {
        executor.execute {
            dao.updateEntryIsReadAndFeedUnreadCount(id, isRead, operator)
        }
    }

    fun deleteFeed(feed: Feed) {
        executor.execute {
            dao.deleteFeed(feed)
        }
    }

    fun deleteFeeds(feeds: List<Feed>) {
        executor.execute {
            dao.deleteFeeds(feeds)
        }
    }

    fun deleteFeedAndEntries(feed: Feed, entries: List<Entry>) {
        executor.execute {
            dao.deleteFeedAndEntries(feed, entries)
        }
    }

    fun deleteFeedsAndEntries(feeds: List<Feed>, entries: List<Entry>) {
        executor.execute {
            dao.deleteFeedsAndEntries(feeds, entries)
        }
    }

    fun deleteFeedsAndEntriesByIds(ids: List<String>) {
        executor.execute {
            dao.deleteFeedsAndEntriesByIds(ids)
        }
    }

    fun deleteEntry(entry: Entry) {
        executor.execute {
            dao.deleteEntry(entry)
        }
    }

    fun deleteEntries(entries: List<Entry>) {
        executor.execute {
            dao.deleteEntries(entries)
        }
    }

    fun deleteEntriesByWebsite(websites: List<String>) {
        executor.execute {
            dao.deleteEntriesByWebsite(websites)
        }
    }

    fun deleteFeedsByWebsite(websites: List<String>) {
        executor.execute {
            dao.deleteFeedsBy(websites)
        }
    }
}