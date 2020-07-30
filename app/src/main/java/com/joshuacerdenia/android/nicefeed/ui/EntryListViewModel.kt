package com.joshuacerdenia.android.nicefeed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.FeedParser
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries

private const val TAG = "MainViewModel"

class EntryListViewModel: ViewModel() {

    private val repository = NiceFeedRepository.get()
    private val parser = FeedParser()
    private val feedIdLiveData = MutableLiveData<String>()

    var refreshHasBeenManaged = false
    var hasAutoRefreshed = false

    val requestResultLiveData: LiveData<FeedWithEntries>? = parser.feedRequestLiveData
    val feedWithEntriesLiveData: LiveData<FeedWithEntries> = Transformations.switchMap(feedIdLiveData) {
        repository.getFeedWithEntriesById(it)
    }

    val entriesLiveData: LiveData<List<Entry>> = Transformations.switchMap(feedIdLiveData) {
        repository.getEntriesByFeedId(it)
    }

    fun requestFeed(url: String) {
        refreshHasBeenManaged = false
        parser.requestFeed(url)
    }

    fun getFeedWithEntriesById(feedId: String) {
        feedIdLiveData.value = feedId
    }

    fun getEntriesByFeedId(feedId: String) {
        feedIdLiveData.value = feedId
    }

    fun addEntries(entries: List<Entry>) {
        repository.addEntries(entries)
    }

    fun refreshEntries(
        toAdd: List<Entry>,
        toSave: List<Entry>,
        toDelete: List<Entry>
    ) {
        repository.refreshEntries(toAdd, toSave, toDelete)
    }

    fun updateFeed(feed: Feed) {
        repository.updateFeed(feed)
    }

    fun updateFeedUnreadCountById(id: String, count: Int) {
        repository.updateFeedUnreadCountById(id, count)
    }

    fun updateEntryIsReadAndFeedUnreadCount(id: String, isRead: Boolean, operator: Int) {
        repository.updateEntryIsReadAndFeedUnreadCount(id, isRead, operator)
    }

    fun updateEntry(entry: Entry) {
        repository.updateEntry(entry)
    }

    fun updateEntries(entries: List<Entry>) {
        repository.updateEntries(entries)
    }

    fun deleteFeedAndEntries(feed: Feed, entries: List<Entry>) {
        repository.deleteFeedAndEntries(feed, entries)
    }

    fun deleteEntries(entries: List<Entry>) {
        repository.deleteEntries(entries)
    }
}