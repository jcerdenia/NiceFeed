package com.joshuacerdenia.android.nicefeed.ui

import androidx.lifecycle.*
import com.joshuacerdenia.android.nicefeed.data.remote.FeedParser
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class EntryListViewModel: ViewModel() {

    private val repository = NiceFeedRepository.get()
    private val parser = FeedParser()
    private val feedIdLiveData = MutableLiveData<String>()

    var currentQuery: String? = null
    var refreshHasBeenManaged = false
    var shouldAutoRefresh = true

    val requestResultLiveData: LiveData<FeedWithEntries?> = parser.feedRequestLiveData

    val feedLiveData: LiveData<Feed> = Transformations.switchMap(feedIdLiveData) { feedId ->
        repository.getFeedById(feedId)
    }

    val entriesLiveData: LiveData<List<Entry>> = Transformations.switchMap(feedIdLiveData) { feedId ->
        repository.getEntriesByFeedId(feedId)
    }

    fun requestFeedUpdate(url: String) {
        refreshHasBeenManaged = false
        viewModelScope.launch {
            parser.requestFeed(url)
        }
    }

    fun getFeedAndEntriesById(feedId: String) {
        feedIdLiveData.value = feedId
    }

    fun getEntries() {
        // TODO: Get recent entries
    }

    fun getStarredEntries() {
        // TODO: Get starred entries
    }

    fun updateFeed(feed: Feed) {
        repository.updateFeed(feed)
    }

    fun updateFeedUnreadCountById(id: String, count: Int) {
        repository.updateFeedUnreadCountById(id, count)
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

    fun deleteFeedAndEntriesByFeedId(feedId: String) {
        repository.deleteFeedsAndEntriesByIds(listOf(feedId))
    }

    fun refreshEntries(toAdd: List<Entry>, toSave: List<Entry>, toDelete: List<Entry>, feedId: String) {
        repository.refreshEntries(toAdd, toSave, toDelete, feedId)
    }
}