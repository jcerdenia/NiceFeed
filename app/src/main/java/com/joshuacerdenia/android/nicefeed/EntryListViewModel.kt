package com.joshuacerdenia.android.nicefeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

private const val TAG = "MainViewModel"

class EntryListViewModel: ViewModel() {

    private val repository = Repository.get()

    private val feedParser = FeedParser()
    val feedRequestLiveData: LiveData<FeedWithEntries>? = feedParser.feedRequestLiveData

    var isManagingFeeds = false
    var newEntriesHaveBeenHandled = false


    val feedListLiveData = repository.getFeeds()
    val entryListLiveData = repository.getEntries()

    private val websiteLiveData = MutableLiveData<String>()
    val feedWithEntriesLiveData: LiveData<FeedWithEntries> =
        Transformations.switchMap(websiteLiveData) { website ->
            repository.getFeedWithEntries(website)
        }

    fun requestFeed(url: String) {
        newEntriesHaveBeenHandled = false
        feedParser.requestFeed(url)
    }

    fun getFeedWithEntries(website: String) {
        websiteLiveData.value = website
    }

    fun saveFeed(feed: Feed) {
        repository.addFeed(feed)
    }

    fun saveFeedWithEntries (feedWithEntries: FeedWithEntries) {
        repository.addFeedWithEntries(feedWithEntries)
    }

    fun updateFeed(feed: Feed) {
        repository.updateFeed(feed)
    }

    fun deleteFeed(feed: Feed) {
        repository.deleteFeed(feed)
    }

    fun addEntry(entry: Entry) {
        repository.addEntry(entry)
    }

    fun saveEntries(entries: List<Entry>) {
        repository.addEntries(entries)
    }

    fun updateEntry(entry: Entry) {
        repository.updateEntry(entry)
    }

    fun updateEntries(entries: List<Entry>) {
        repository.updateEntries(entries)
    }

    fun deleteEntries(entries: List<Entry>) {
        repository.deleteEntries(entries)
    }

    fun deleteFeedAndEntries(feed: Feed, entries: List<Entry>) {
        repository.deleteFeedAndEntries(feed, entries)
    }
}