package com.joshuacerdenia.android.nicefeed.utils

import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.utils.extensions.sortedByDate

/*  This class compares recently requested data from the web with current data saved locally.
    It outputs which entries to add, update, and delete, as well as updated feed data, if any.
*/
class UpdateManager(private val receiver: UpdateReceiver) {

    interface UpdateReceiver {
        fun onUnreadEntriesCounted(feedId: String, unreadCount: Int)
        fun onFeedNeedsUpdate(feed: Feed)
        fun onOldAndNewEntriesCompared(
            entriesToAdd: List<Entry>,
            entriesToUpdate: List<Entry>,
            entriesToDelete: List<Entry>,
            feedId: String
        )
    }

    var currentFeed: Feed? = null
        private set
    private var currentEntries = listOf<Entry>()
        get() = field.sortedByDate()

    fun setInitialFeed(feed: Feed) {
        currentFeed = feed
    }

    fun setInitialEntries(entries: List<Entry>) {
        currentEntries = entries
        var unreadCount = 0
        for (entry in entries) {
            if (!entry.isRead) unreadCount += 1
        }
        currentFeed?.let { receiver.onUnreadEntriesCounted(it.url, unreadCount) }
    }

    fun submitUpdates(feedWithEntries: FeedWithEntries) {
        handleFeedUpdate(feedWithEntries.feed)
        handleNewEntries(feedWithEntries.entries)
    }

    fun forceUpdateFeed(feed: Feed) {
        currentFeed = feed
        receiver.onFeedNeedsUpdate(feed)
    }

    private fun handleNewEntries(newEntries: List<Entry>) {
        val newEntryIds = getEntryIds(newEntries)
        val currentEntryIds = getEntryIds(currentEntries)
        val entriesToAdd = mutableListOf<Entry>()
        val entriesToUpdate = mutableListOf<Entry>()
        val entriesToDelete = mutableListOf<Entry>()

        for (entry in newEntries) {
            // First, check if entry already exists unchanged
            if (isUnique(entry)) {
                // If entry is unique, look for existing older version
                if (currentEntryIds.contains(entry.url)) {
                    val currentItemIndex = currentEntryIds.indexOf(entry.url)
                    entry.isStarred = currentEntries[currentItemIndex].isStarred
                    entriesToUpdate.add(entry)
                } else {
                    entriesToAdd.add(entry)
                }
            }
        }

        for (entry in currentEntries) {
            if (!newEntryIds.contains(entry.url)) {
                if (!entry.isStarred && entry.isRead) entriesToDelete.add(entry)
            }
        }

        // Check if entries are changed at all
        if (entriesToAdd.size + entriesToUpdate.size + entriesToDelete.size > 0) {
            currentFeed?.let { feed->
                receiver.onOldAndNewEntriesCompared(
                    entriesToAdd,
                    entriesToUpdate,
                    entriesToDelete,
                    feed.url
                )
            }
        }
    }

    // Check to see if there is any one old entry that is the same as the new entry
    private fun isUnique(newEntry: Entry): Boolean {
        var isUnique = true
        for (currentEntry in currentEntries.filter { it.url == newEntry.url }) {
            if (newEntry.isSameAs(currentEntry)) {
                isUnique = false
                break
            }
        }
        return isUnique
    }

    private fun getEntryIds(entries: List<Entry>): List<String> {
        return entries.map { it.url }
    }

    private fun handleFeedUpdate(newFeed: Feed) {
        currentFeed?.let { oldFeed ->
            newFeed.title = oldFeed.title
            newFeed.category = oldFeed.category
            newFeed.unreadCount = oldFeed.unreadCount
            if (newFeed != oldFeed) receiver.onFeedNeedsUpdate(newFeed)
        }
    }
}