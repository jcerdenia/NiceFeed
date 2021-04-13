package com.joshuacerdenia.android.nicefeed.data.local.database.dao

import androidx.room.Dao
import androidx.room.Transaction
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedTitleWithEntriesToggleable
import com.joshuacerdenia.android.nicefeed.data.model.entry.Entry
import com.joshuacerdenia.android.nicefeed.data.model.entry.EntryToggleable
import com.joshuacerdenia.android.nicefeed.data.model.feed.Feed

@Dao
interface CombinedDao: FeedsDao, EntriesDao, FeedEntryCrossRefsDao {

    @Transaction
    fun addFeedAndEntries(feed: Feed, entries: List<Entry>) {
        addFeeds(feed)
        addEntries(entries)
        addFeedEntryCrossRefs(feed.url, entries)
    }

    @Transaction
    fun getFeedTitleAndEntriesToggleableSynchronously(
        feedId: String
    ): FeedTitleWithEntriesToggleable {
        return FeedTitleWithEntriesToggleable(
            getFeedTitleSynchronously(feedId),
            getEntriesToggleableByFeedSynchronously(feedId)
        )
    }

    @Transaction
    fun handleEntryUpdates(
        feedId: String,
        entriesToAdd: List<Entry>,
        entriesToUpdate: List<Entry>,
        entriesToDelete: List<Entry>,
    ) {
        addEntries(entriesToAdd)
        addFeedEntryCrossRefs(feedId, entriesToAdd)
        updateEntries(entriesToUpdate)
        deleteFeedEntryCrossRefs(feedId, entriesToDelete.map { it.url })
        deleteEntries(entriesToDelete)
    }

    @Transaction
    fun handleBackgroundUpdate(
        feedId: String,
        newEntries: List<Entry>,
        oldEntries: List<EntryToggleable>,
        feedImage: String?
    ) {
        addEntries(newEntries)
        addFeedEntryCrossRefs(feedId, newEntries)
        oldEntries.map { it.url }.let { entryIds ->
            deleteEntriesById(entryIds)
            deleteFeedEntryCrossRefs(feedId, entryIds)
        }
        addToFeedUnreadCount(feedId, (newEntries.size - oldEntries.filter { !it.isRead }.size))
        feedImage?.let { updateFeedImage(feedId, it) }
    }

    @Transaction
    fun updateEntryAndFeedUnreadCount(
        entryId: String,
        isRead: Boolean,
        isStarred: Boolean
    ) {
        updateEntryIsStarred(entryId, isStarred = isStarred)
        updateEntryIsReadAndFeedUnreadCount(entryId, isRead = isRead)
    }

    @Transaction
    fun updateEntryIsReadAndFeedUnreadCount(vararg entryId: String, isRead: Boolean) {
        updateEntryIsRead(*entryId, isRead = isRead)
        (if (isRead) -1 else 1).let { addend ->
            entryId.forEach { addToFeedUnreadCountByEntry(it, addend) }
        }
    }

    @Transaction
    fun deleteFeedAndEntriesById(vararg feedId: String) {
        deleteEntriesByFeed(*feedId)
        deleteCrossRefsByFeed(*feedId)
        deleteFeeds(*feedId)
    }

    @Transaction
    fun deleteLeftoverItems() {
        deleteLeftoverCrossRefs()
        deleteLeftoverEntries()
    }
}