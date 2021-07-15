package com.joshuacerdenia.android.nicefeed.data

import androidx.lifecycle.LiveData
import com.joshuacerdenia.android.nicefeed.data.local.database.NiceFeedDatabase
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedTitleWithEntriesToggleable
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.data.model.entry.Entry
import com.joshuacerdenia.android.nicefeed.data.model.entry.EntryToggleable
import com.joshuacerdenia.android.nicefeed.data.model.feed.Feed
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedIdWithCategory
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedLight
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable
import com.joshuacerdenia.android.nicefeed.data.remote.FeedFetcher
import com.joshuacerdenia.android.nicefeed.util.NetworkMonitor
import java.util.concurrent.Executors

class NiceFeedRepository private constructor(
    database: NiceFeedDatabase,
    private val feedFetcher: FeedFetcher,
    val networkMonitor: NetworkMonitor
) {

    private val dao = database.combinedDao()
    private val executor = Executors.newSingleThreadExecutor()

    val feedWithEntriesLive = feedFetcher.feedWithEntriesLive

    // [START] Remote data access methods

    fun requestFeedAndEntriesOnline(url: String) {
        executor.execute { feedFetcher.request(url) }
    }

    private fun resetFeedWithEntriesLive() {
        feedFetcher.reset()
    }

    // [END] Remote data access methods

    // [START] Local data access methods

    fun getFeed(feedId: String): LiveData<Feed?> = dao.getFeed(feedId)

    fun getFeedsLight(): LiveData<List<FeedLight>> = dao.getFeedsLight()

    fun getFeedIds(): LiveData<List<String>> = dao.getFeedIds()

    fun getFeedIdsWithCategories(): LiveData<List<FeedIdWithCategory>> = dao.getFeedIdsWithCategories()

    fun getFeedUrlsSynchronously(): List<String> = dao.getFeedUrlsSynchronously()

    fun getFeedTitleWithEntriesToggleableSynchronously(feedId: String): FeedTitleWithEntriesToggleable {
        return dao.getFeedTitleAndEntriesToggleableSynchronously(feedId)
    }

    fun getFeedsManageable(): LiveData<List<FeedManageable>> = dao.getFeedsManageable()

    fun getEntry(entryId: String): LiveData<Entry?> = dao.getEntry(entryId)

    fun getEntriesByFeed(feedId: String): LiveData<List<Entry>> = dao.getEntriesByFeed(feedId)

    fun getNewEntries(max: Int): LiveData<List<Entry>> = dao.getNewEntries(max)

    fun getStarredEntries(): LiveData<List<Entry>> = dao.getStarredEntries()

    fun getEntriesToggleableByFeedSynchronously(feedId: String): List<EntryToggleable> {
        return dao.getEntriesToggleableByFeedSynchronously(feedId)
    }

    fun addFeeds(vararg feed: Feed) {
        executor.execute { dao.addFeeds(*feed) }
    }

    fun addFeedWithEntries(feedWithEntries: FeedWithEntries) {
        executor.execute {
            dao.addFeedAndEntries(feedWithEntries.feed, feedWithEntries.entries)
        }
    }

    fun updateFeed(feed: Feed) {
        executor.execute { dao.updateFeed(feed) }
    }

    fun updateFeedTitleAndCategory(feedId: String, title: String, category: String) {
        executor.execute { dao.updateFeedTitleAndCategory(feedId, title, category) }
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
        feedId: String,
        entriesToAdd: List<Entry>,
        entriesToUpdate: List<Entry>,
        entriesToDelete: List<Entry>,
    ) {
        executor.execute {
            dao.handleEntryUpdates(feedId, entriesToAdd, entriesToUpdate, entriesToDelete)
        }
    }

    fun handleBackgroundUpdate(
        feedId: String,
        newEntries: List<Entry>,
        oldEntries: List<EntryToggleable>,
        feedImage: String?,
    ) {
        executor.execute {
            dao.handleBackgroundUpdate(feedId, newEntries, oldEntries, feedImage)
        }
    }

    fun deleteFeedAndEntriesById(vararg feedId: String) {
        executor.execute { dao.deleteFeedAndEntriesById(*feedId) }
    }

    fun deleteLeftoverItems() {
        executor.execute { dao.deleteLeftoverItems() }
    }

    // [END] Local data access methods

    companion object {

        private var INSTANCE: NiceFeedRepository? = null

        fun init(database: NiceFeedDatabase, feedFetcher: FeedFetcher, networkMonitor: NetworkMonitor) {
            if (INSTANCE == null) INSTANCE = NiceFeedRepository(database, feedFetcher, networkMonitor)
        }

        fun get(): NiceFeedRepository {
            INSTANCE?.resetFeedWithEntriesLive()
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized!")
        }
    }
}