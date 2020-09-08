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
    
    private val database: NiceFeedDatabase = Room.databaseBuilder(
        context.applicationContext,
        NiceFeedDatabase::class.java,
        DATABASE_NAME
    ).build()
    
    private val dao = database.feedsAndEntriesDao()
    private val executor = Executors.newSingleThreadExecutor()
    private val feedSearcher = FeedSearcher()

    fun performSearch(query: String): LiveData<List<SearchResultItem>> = feedSearcher.performSearch(query)

    fun getFeedById(id: String): LiveData<Feed> = dao.getFeedById(id)

    fun getFeeds(): LiveData<List<Feed>> = dao.getAllFeeds()

    fun getFeedIds(): LiveData<List<String>> = dao.getAllFeedIds()

    fun getAllFeedUrlsSync(): List<String> = dao.getAllFeedUrlsSync()

    fun getAllFeedsMinimal(): LiveData<List<FeedMinimal>> = dao.getAllFeedsMinimal()

    fun getAllEntries(): LiveData<List<Entry>> = dao.getAllEntries()

    fun getEntryById(entryId: String): LiveData<Entry> = dao.getEntryById(entryId)

    fun getEntriesByFeedId(feedId: String): LiveData<List<Entry>> = dao.getEntriesByFeedId(feedId)

    fun getEntryIdsByFeedIdSync(feedId: String): List<String> = dao.getEntryIdsByFeedIdSync(feedId)

    fun updateFeedCategory(vararg feedId: String, category: String) {
        executor.execute {
            dao.updateFeedCategory(*feedId, category = category)
        }
    }

    fun updateFeed(feed: Feed) {
        executor.execute {
            dao.updateFeed(feed)
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

    fun updateEntryIsStarred(vararg entryId: String, isStarred: Boolean) {
        executor.execute {
            dao.updateEntryIsStarred(*entryId, isStarred = isStarred)
        }
    }

    fun updateEntryIsRead(vararg entryId: String, isRead: Boolean) {
        executor.execute {
            dao.updateEntryIsRead(*entryId, isRead = isRead)
        }
    }

    fun addFeeds(feeds: List<Feed>) {
        executor.execute {
            dao.addFeeds(feeds)
        }
    }

    fun addFeedWithEntries(data: FeedWithEntries) {
        val crossRefs = getCrossRefs(data.feed.url, data.entries)
        executor.execute {
            dao.addFeedAndEntries(data.feed, data.entries, crossRefs)
        }
    }

    fun handleLatestEntriesFound(entries: List<Entry>, feedId: String) {
        val crossRefs = getCrossRefs(feedId, entries)
        executor.execute {
            dao.handleLatestEntriesFound(entries, feedId, crossRefs)
        }
    }

    fun deleteFeedAndEntries(feed: Feed, entries: List<Entry>) {
        val crossRefs = getCrossRefs(feed.url, entries)
        executor.execute {
            dao.deleteFeedAndEntries(feed, entries, crossRefs)
        }
    }

    fun deleteFeedsAndEntriesByIds(ids: List<String>) {
        executor.execute {
            dao.deleteFeedsAndEntriesByIds(ids)
        }
    }

    fun deleteFeedAndEntriesById(id: String) {
        executor.execute {
            dao.deleteFeedsAndEntriesByIds(listOf(id))
        }
    }

    fun deleteFeedLessEntries() {
        executor.execute {
            dao.deleteFeedlessEntries()
        }
    }

    fun refreshEntries(
        toAdd: List<Entry>,
        toUpdate: List<Entry>,
        toDelete: List<Entry>,
        feedId: String
    ) {
        val crossRefsToAdd = getCrossRefs(feedId, toAdd)
        val crossRefsToDelete = getCrossRefs(feedId, toDelete)
        executor.execute {
            dao.refreshEntries(toAdd, toUpdate, toDelete, crossRefsToAdd, crossRefsToDelete)
        }
    }

    private fun getCrossRefs(feedId: String, entries: List<Entry>): List<FeedEntryCrossRef> {
        val crossRefs = mutableListOf<FeedEntryCrossRef>()
        for (entry in entries) {
            crossRefs.add(FeedEntryCrossRef(feedId, entry.url))
        }
        return crossRefs
    }

    fun addFeedEntryCrossRef(crossRef: FeedEntryCrossRef) {
        executor.execute {
            dao.addFeedEntryCrossRef(crossRef)
        }
    }

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
}