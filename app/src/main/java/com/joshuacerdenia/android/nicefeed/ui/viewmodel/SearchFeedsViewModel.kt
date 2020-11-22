package com.joshuacerdenia.android.nicefeed.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem
import com.joshuacerdenia.android.nicefeed.data.remote.FeedSearcher

class SearchFeedsViewModel: FeedAddingViewModel() {

    private val searcher = FeedSearcher(repo.networkMonitor)

    var newQuery: String = ""
    var initialQueryIsMade = false

    val feedIdsLiveData = repo.getFeedIds()
    private val mutableQuery = MutableLiveData<String>()
    val searchResultLiveData: LiveData<List<SearchResultItem>> = Transformations.switchMap(mutableQuery) { query ->
        searcher.getFeedList(query)
    }

    fun onFeedIdsRetrieved(feedIds: List<String>) {
        currentFeedIds = feedIds
    }

    fun performSearch(query: String) {
        mutableQuery.value = query.trim()
    }
}