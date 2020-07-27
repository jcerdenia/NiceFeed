package com.joshuacerdenia.android.nicefeed.ui

import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem

class FeedSearchViewModel: AddFeedViewModel() {

    var newQuery: String = ""
    var initialQueryIsMade = false
    var itemBeingLoaded: SearchResultItem? = null
    var itemSelectionEnabled = true

    private val mutableQuery = MutableLiveData<String>()
    val searchResultLiveData: LiveData<List<SearchResultItem>> =
        Transformations.switchMap(mutableQuery) { query ->
            repository.performSearch(query)
        }

    fun performSearch(query: String) {
        mutableQuery.value = query
    }
}