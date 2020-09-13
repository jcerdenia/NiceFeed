package com.joshuacerdenia.android.nicefeed.utils

import android.util.Log
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed

/*  This class compares recently requested data from the web with current data saved locally.
    It outputs which entries to add, update, and delete, as well as updated feed data, if any.
*/
class UpdateManager(private val listener: UpdateListener) {

    interface UpdateListener {
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
        get() = field.sortedByDescending { it.date }

    fun submitInitialFeed(feed: Feed) {
        currentFeed = feed
    }

    fun submitInitialEntries(entries: List<Entry>) {
        currentEntries = entries
        var unreadCount = 0
        for (entry in entries) {
            if (!entry.isRead) {
                unreadCount += 1
            }
        }

        currentFeed?.let { feed ->
            listener.onUnreadEntriesCounted(feed.url, unreadCount)
        }
    }

    fun submitNewData(feed: Feed, entries: List<Entry>) {
        Log.d("UpdateManager", "Submitting new data to UpdateManager...")
        handleFeedUpdate(feed)
        handleNewEntries(entries)
    }

    private fun handleNewEntries(newEntries: List<Entry>) {
        val newEntryIds = getEntryIds(newEntries)
        val currentEntryIds = getEntryIds(currentEntries)

        val entriesToAdd = mutableListOf<Entry>()
        val entriesToUpdate = mutableListOf<Entry>()
        val entriesToDelete = mutableListOf<Entry>()

        for (entry in newEntries) {
            if (!isAddedAndUnchanged(entry, currentEntries)) {
                // Check for old version of the new entry:
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
                if (!entry.isStarred && entry.isRead) {
                    entriesToDelete.add(entry)
                }
            }
        }

        // Check if entries are changed at all
        if (entriesToAdd.size + entriesToUpdate.size + entriesToDelete.size > 0) {
            currentFeed?.let { feed->
                listener.onOldAndNewEntriesCompared(
                    entriesToAdd,
                    entriesToUpdate,
                    entriesToDelete,
                    feed.url
                )
            }
        }
    }

    private fun handleFeedUpdate(feed: Feed) {
        currentFeed?.let {
            feed.category = it.category
            feed.unreadCount = it.unreadCount
        }

        if (feed !== currentFeed) {
            listener.onFeedNeedsUpdate(feed)
        }
    }

    private fun getEntryIds(entries: List<Entry>): List<String> {
        return entries.map { entry ->
            entry.url
        }
    }

    // Check a new entry against all current entries to see if the content is the same
    private fun isAddedAndUnchanged(newEntry: Entry, currentEntries: List<Entry>): Boolean {
        var isAddedAndUnchanged = false
        for (currentEntry in currentEntries) {
            if (newEntry.isSameAs(currentEntry)) {
                isAddedAndUnchanged = true
                break
            }
        }

        return isAddedAndUnchanged
    }
}