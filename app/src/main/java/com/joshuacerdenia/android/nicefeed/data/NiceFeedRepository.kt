package com.joshuacerdenia.android.nicefeed.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.joshuacerdenia.android.nicefeed.data.local.database.NiceFeedDatabase
import com.joshuacerdenia.android.nicefeed.data.model.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "database"

class NiceFeedRepository private constructor(context: Context) {
    
    private val database = Room.databaseBuilder(
        context.applicationContext,
        NiceFeedDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val dao = database.combinedDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getFeed(feedId: String): LiveData<Feed?> = dao.getFeed(feedId)

    fun getFeedsLight(): LiveData<List<FeedLight>> = dao.getFeedsLight()

    fun getFeedIds(): LiveData<List<String>> = dao.getFeedIds()

    fun getFeedUrlsSynchronously(): List<String> = dao.getFeedUrlsSynchronously()

    fun getFeedsMinimal(): LiveData<List<FeedMinimal>> = dao.getFeedsMinimal()

    fun getEntry(entryId: String): LiveData<Entry?> = dao.getEntry(entryId)

    fun getEntriesByFeed(feedId: String): LiveData<List<Entry>> = dao.getEntriesByFeed(feedId)

    fun getRecentEntries(max: Int): LiveData<List<Entry>> = dao.getRecentEntries(max)

    fun getStarredEntries(): LiveData<List<Entry>> = dao.getStarredEntries()

    fun getEntryIdsByFeedSynchronously(feedId: String): List<String> = dao.getEntryIdsByFeedSynchronously(feedId)

    fun addFeeds(vararg feed: Feed) {
        executor.execute {
            dao.addFeeds(*feed)
        }
    }

    fun addFeedWithEntries(data: FeedWithEntries) {
        executor.execute {
            getCrossRefs(data.feed.url, data.entries).also { crossRefs ->
                dao.addFeedAndEntries(data.feed, data.entries, crossRefs)
            }
        }
    }

    fun updateFeed(feed: Feed) {
        executor.execute {
            dao.updateFeed(feed)
        }
    }

    fun updateFeedCategory(vararg feedId: String, category: String) {
        executor.execute {
            dao.updateFeedCategory(*feedId, category = category)
        }
    }

    fun updateFeedUnreadCount(feedId: String, count: Int) {
        executor.execute {
            dao.updateFeedUnreadCount(feedId, count)
        }
    }

    fun updateEntryAndFeedUnreadCount(entryId: String, isRead: Boolean, isStarred: Boolean) {
        executor.execute {
            dao.updateEntryAndFeedUnreadCount(entryId, isRead, isStarred)
        }
    }

    fun updateEntryIsStarred(vararg entryId: String, isStarred: Boolean) {
        executor.execute {
            dao.updateEntryIsStarred(*entryId, isStarred = isStarred)
        }
    }

    fun updateEntryIsRead(vararg entryId: String, isRead: Boolean) {
        executor.execute {
            dao.updateEntryIsReadAndFeedUnreadCount(*entryId, isRead = isRead)
        }
    }

    fun handleEntryUpdates(
        toAdd: List<Entry>,
        toUpdate: List<Entry>,
        toDelete: List<Entry>,
        feedId: String
    ) {
        executor.execute {
            val crossRefsToAdd = getCrossRefs(feedId, toAdd)
            val crossRefsToDelete = getCrossRefs(feedId, toDelete)
            dao.handleEntryUpdates(toAdd, toUpdate, toDelete, crossRefsToAdd, crossRefsToDelete)
        }
    }

    fun handleNewEntriesFound(entries: List<Entry>, feedId: String) {
        executor.execute {
            getCrossRefs(feedId, entries).also { crossRefs ->
                dao.handleNewEntriesFound(entries, feedId, crossRefs)
            }
        }
    }

    fun deleteFeedAndEntriesById(vararg feedId: String) {
        executor.execute {
            dao.deleteFeedAndEntriesById(*feedId)
        }
    }

    fun deleteLeftoverItems() {
        executor.execute {
            dao.deleteLeftoverItems()
        }
    }

    private fun getCrossRefs(feedId: String, entries: List<Entry>): List<FeedEntryCrossRef> {
        val crossRefs = mutableListOf<FeedEntryCrossRef>()
        for (entry in entries) {
            crossRefs.add(FeedEntryCrossRef(feedId, entry.url))
        }
        return crossRefs
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