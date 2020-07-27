package com.joshuacerdenia.android.nicefeed.utils

import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries

/* Compares recently requested data from the web with current data saved locally;
Outputs which entries to add, update, and delete; as well as updated feed data, if any */

class RefreshManager(private val listener: OnRefreshedListener) {

    var currentFeed: Feed? = null
    var currentEntries = listOf<Entry>()
        get() = field.sortedByDatePublished()

    interface OnRefreshedListener {
        fun onCurrentEntriesUpdated()
        fun onRefreshedFeed(feed: Feed)
        fun onRefreshedEntries(
            toSave: List<Entry>,
            toUpdate: List<Entry>,
            toDelete: List<Entry>
        )
    }

    fun submitCurrent(current: FeedWithEntries) {
        currentFeed = current.feed
        currentEntries = current.entries
        listener.onCurrentEntriesUpdated()
    }

    fun submitNew(new: FeedWithEntries) {
        handleFeed(new.feed)
        handleNewEntries(new.entries)
    }

    private fun handleFeed(feed: Feed) {
        currentFeed?.let {
            feed.category = it.category
            feed.unreadCount = it.unreadCount
        }

        if (feed != currentFeed) {
            listener.onRefreshedFeed(feed)
        }
    }

    private fun handleNewEntries(newEntries: List<Entry>) {
        val newEntriesByGuid = getEntriesByGuid(newEntries)
        val currentEntriesByGuid = getEntriesByGuid(currentEntries)

        val entriesToSave = mutableListOf<Entry>()
        val entriesToUpdate = mutableListOf<Entry>()
        val entriesToDelete = mutableListOf<Entry>()

        for (entry in newEntries) {
            if (!isAddedAndUnchanged(entry, currentEntries)) {
                if (currentEntriesByGuid.contains(entry.guid)) {
                    // i.e., if an old version of the entry exists
                    val currentItemIndex = currentEntriesByGuid.indexOf(entry.guid)
                    entry.isStarred = currentEntries[currentItemIndex].isStarred
                    entriesToUpdate.add(entry)
                } else {
                    entriesToSave.add(entry)
                }
            }
        }

        for (entry in currentEntries) {
            if (!newEntriesByGuid.contains(entry.guid) && !entry.isStarred) {
                entriesToDelete.add(entry)
            }
        }

        listener.onRefreshedEntries(entriesToSave, entriesToUpdate, entriesToDelete)
    }

    private fun getEntriesByGuid(entries: List<Entry>): List<String> {
        val entriesByGuid = mutableListOf<String>()
        for (entry in entries) {
            entriesByGuid.add(entry.guid)
        }

        return entriesByGuid
    }

    private fun isAddedAndUnchanged(entry: Entry, currentEntries: List<Entry>): Boolean {
        // Checks a new entry against all current entries to see if the content is the same
        var isAddedAndUnchanged = false
        for (currentEntry in currentEntries) {
            if (entry.isTheSameAs(currentEntry)) {
                isAddedAndUnchanged = true
                break
            }
        }

        return isAddedAndUnchanged
    }
}