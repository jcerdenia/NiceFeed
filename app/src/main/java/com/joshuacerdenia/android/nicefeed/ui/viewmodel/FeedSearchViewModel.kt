package com.joshuacerdenia.android.nicefeed.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem
import com.joshuacerdenia.android.nicefeed.data.remote.FeedSearcher

class FeedSearchViewModel: AddFeedsViewModel() {

    private val searcher = FeedSearcher()

    var newQuery: String = ""
    var initialQueryIsMade = false
    var itemBeingLoaded: SearchResultItem? = null
    var itemSelectionEnabled = true

    private val mutableQuery = MutableLiveData<String>()
    val searchResultLiveData: LiveData<List<SearchResultItem>> =
        Transformations.switchMap(mutableQuery) { query ->
            searcher.performSearch(query)
        }

    fun performSearch(query: String) {
        mutableQuery.value = query
    }
}