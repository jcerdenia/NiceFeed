package com.joshuacerdenia.android.nicefeed.data

import androidx.lifecycle.LiveData
import com.joshuacerdenia.android.nicefeed.data.local.database.NiceFeedDatabase
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedEntryCrossRef
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedTitleWithEntryInfo
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.data.model.entry.Entry
import com.joshuacerdenia.android.nicefeed.data.model.entry.EntryUsed
import com.joshuacerdenia.android.nicefeed.data.model.feed.Feed
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedIdWithCategory
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedLight
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable
import com.joshuacerdenia.android.nicefeed.utils.NetworkMonitor
import java.util.concurrent.Executors

class NiceFeedRepository private constructor(
    database: NiceFeedDatabase,
    val networkMonitor: NetworkMonitor
) {

    private val dao = database.combinedDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getFeed(feedId: String): LiveData<Feed?> = dao.getFeed(feedId)

    fun getFeedsLight(): LiveData<List<FeedLight>> = dao.getFeedsLight()

    fun getFeedIds(): LiveData<List<String>> = dao.getFeedIds()

    fun getFeedIdsWithCategories(): LiveData<List<FeedIdWithCategory>> = dao.getFeedIdsWithCategories()

    fun getFeedUrlsSynchronously(): List<String> = dao.getFeedUrlsSynchronously()

    fun getFeedTitleWithEntriesInfoSynchronously(feedId: String): FeedTitleWithEntryInfo {
        return dao.getFeedTitleAndEntriesInfoSynchronously(feedId)
    }

    fun getFeedsManageable(): LiveData<List<FeedManageable>> = dao.getFeedsManageable()

    fun getEntry(entryId: String): LiveData<Entry?> = dao.getEntry(entryId)

    fun getEntriesByFeed(feedId: String): LiveData<List<Entry>> = dao.getEntriesByFeed(feedId)

    fun getNewEntries(max: Int): LiveData<List<Entry>> = dao.getNewEntries(max)

    fun getStarredEntries(): LiveData<List<Entry>> = dao.getStarredEntries()

    fun getEntriesUsedByFeedSynchronously(feedId: String): List<EntryUsed> {
        return dao.getEntriesUsedByFeedSynchronously(feedId)
    }

    fun addFeeds(vararg feed: Feed) {
        executor.execute { dao.addFeeds(*feed) }
    }

    fun addFeedWithEntries(data: FeedWithEntries) {
        executor.execute {
            getCrossRefs(data.feed.url, data.entries).also { crossRefs ->
                dao.addFeedAndEntries(data.feed, data.entries, crossRefs)
            }
        }
    }

    fun updateFeed(feed: Feed) {
        executor.execute { dao.updateFeed(feed) }
    }

    fun updateFeedDetails(feedId: String, title: String, category: String) {
        executor.execute { dao.updateFeedDetails(feedId, title, category) }
    }

    fun updateFeedCategory(vararg feedId: String, category: String) {
        executor.execute { dao.updateFeedCategory(*feedId, category = category) }
    }

    fun updateFeedUnreadCount(feedId: String, count: Int) {
        executor.execute { dao.updateFeedUnreadCount(feedId, count) }
    }

    fun updateEntryAndFeedUnreadCount(entryId: String, isRead: Boolean, isStarred: Boolean) {
        executor.execute { dao.updateEntryAndFeedUnreadCount(entryId, isRead, isStarred) }
    }

    fun updateEntryIsStarred(vararg entryId: String, isStarred: Boolean) {
        executor.execute { dao.updateEntryIsStarred(*entryId, isStarred = isStarred) }
    }

    fun updateEntryIsRead(vararg entryId: String, isRead: Boolean) {
        executor.execute { dao.updateEntryIsReadAndFeedUnreadCount(*entryId, isRead = isRead) }
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

    fun handleBackgroundUpdate(
        feedId: String,
        newEntries: List<Entry>,
        oldEntryIds: List<String>,
        feedImage: String?,
    ) {
        executor.execute {
            getCrossRefs(feedId, newEntries).also { crossRefs ->
                dao.handleBackgroundUpdate(feedId, newEntries, crossRefs, oldEntryIds, feedImage)
            }
        }
    }

    fun deleteFeedAndEntriesById(vararg feedId: String) {
        executor.execute { dao.deleteFeedAndEntriesById(*feedId) }
    }

    fun deleteLeftoverItems() {
        executor.execute { dao.deleteLeftoverItems() }
    }

    private fun getCrossRefs(feedId: String, entries: List<Entry>): List<FeedEntryCrossRef> {
        return entries.map { entry -> FeedEntryCrossRef(feedId, entry.url) }
    }

    companion object {
        private var INSTANCE: NiceFeedRepository? = null

        fun initialize(database: NiceFeedDatabase, networkMonitor: NetworkMonitor) {
            if (INSTANCE == null) INSTANCE = NiceFeedRepository(database, networkMonitor)
        }

        fun get(): NiceFeedRepository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized!")
        }
    }
}