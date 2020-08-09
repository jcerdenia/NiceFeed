package com.joshuacerdenia.android.nicefeed.utils

import android.util.Log
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed

/* Compares recently requested data from the web with current data saved locally;
Outputs which entries to add, update, and delete; as well as updated feed data, if any */

private const val TAG = "RefreshHelper"

class RefreshHelper(
    private val listener: OnRefreshedListener
) {

    var currentFeed: Feed? = null
    var currentEntries = listOf<Entry>()
        get() = field.sortedByDatePublished()

    interface OnRefreshedListener {
        fun onCurrentEntriesChanged()
        fun onFeedNeedsRefresh(feed: Feed)
        fun onEntriesNeedRefresh(
            toAdd: List<Entry>,
            toUpdate: List<Entry>,
            toDelete: List<Entry>,
            feedId: String
        )
    }

    fun submitInitialEntries(entries: List<Entry>) {
        currentEntries = entries
        listener.onCurrentEntriesChanged()
    }

    fun submitNewData(feed: Feed, entries: List<Entry>) {
        handleFeed(feed)
        handleNewEntries(entries)
    }

    private fun handleNewEntries(newEntries: List<Entry>) {
        Log.d(TAG, "Handling the new entries motherfucker")
        val newEntryIds = getEntryIds(newEntries)
        val currentEntryIds = getEntryIds(currentEntries)

        val entriesToAdd = mutableListOf<Entry>()
        val entriesToUpdate = mutableListOf<Entry>()
        val entriesToDelete = mutableListOf<Entry>()

        for (entry in newEntries) {
            if (!isAddedAndUnchanged(entry, currentEntries)) {
                if (currentEntryIds.contains(entry.url)) {
                    // i.e., if an old version of the entry exists
                    val currentItemIndex = currentEntryIds.indexOf(entry.url)
                    entry.isStarred = currentEntries[currentItemIndex].isStarred
                    entriesToUpdate.add(entry)
                } else {
                    entriesToAdd.add(entry)
                    Log.d(TAG, "Found new entry to add")
                }
            }
        }

        for (entry in currentEntries) {
            if (!newEntryIds.contains(entry.url) && !entry.isStarred) {
                entriesToDelete.add(entry)
            }
        }

        if (entriesToAdd.size + entriesToUpdate.size + entriesToDelete.size > 0) {
            // i.e., if entries are changed at all
            currentFeed?.let { feed->
                listener.onEntriesNeedRefresh(
                    entriesToAdd,
                    entriesToUpdate,
                    entriesToDelete,
                    feed.url)
            }
        }
    }

    private fun handleFeed(feed: Feed) {
        currentFeed?.let {
            feed.category = it.category
            feed.unreadCount = it.unreadCount
        }

        if (feed !== currentFeed) {
            listener.onFeedNeedsRefresh(feed)
        }
    }

    private fun getEntryIds(entries: List<Entry>): List<String> {
        val entriesByGuid = mutableListOf<String>()
        for (entry in entries) {
            entriesByGuid.add(entry.url)
        }

        return entriesByGuid
    }

    private fun isAddedAndUnchanged(entry: Entry, currentEntries: List<Entry>): Boolean {
        // Check a new entry against all current entries to see if the content is the same
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