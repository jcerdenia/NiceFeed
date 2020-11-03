package com.joshuacerdenia.android.nicefeed.data.local.database

import androidx.room.Dao
import androidx.room.Transaction
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedEntryCrossRef
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedTitleWithEntriesToggleable
import com.joshuacerdenia.android.nicefeed.data.model.entry.Entry
import com.joshuacerdenia.android.nicefeed.data.model.feed.Feed

@Dao
interface CombinedDao: FeedsDao, EntriesDao, FeedEntryCrossRefsDao {

    @Transaction
    fun addFeedAndEntries(
        feed: Feed,
        entries: List<Entry>,
        crossRefs: List<FeedEntryCrossRef>
    ) {
        addFeeds(feed)
        addEntries(entries)
        addFeedEntryCrossRefs(crossRefs)
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
        entriesToAdd: List<Entry>,
        entriesToUpdate: List<Entry>,
        entriesToDelete: List<Entry>,
        crossRefsToAdd: List<FeedEntryCrossRef>,
        crossRefsToDelete: List<FeedEntryCrossRef>
    ) {
        addEntries(entriesToAdd)
        addFeedEntryCrossRefs(crossRefsToAdd)
        updateEntries(entriesToUpdate)
        deleteFeedEntryCrossRefs(crossRefsToDelete)
        deleteEntries(entriesToDelete)
    }

    @Transaction
    fun handleBackgroundUpdate(
        feedId: String,
        newEntries: List<Entry>,
        newCrossRefs: List<FeedEntryCrossRef>,
        oldEntryIds: List<String>,
        feedImage: String?
    ) {
        addEntries(newEntries)
        addFeedEntryCrossRefs(newCrossRefs)
        deleteEntriesById(oldEntryIds)
        deleteFeedEntryCrossRefs(feedId, oldEntryIds)
        addToFeedUnreadCount(feedId, newEntries.size)
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
            for (id in entryId) addToFeedUnreadCountByEntry(id, addend)
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