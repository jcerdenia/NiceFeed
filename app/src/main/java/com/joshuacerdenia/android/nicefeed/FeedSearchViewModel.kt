package com.joshuacerdenia.android.nicefeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class FeedSearchViewModel: AddFeedViewModel() {

    var newQuery: String = ""
    var initialQueryIsMade = false

    private val mutableQuery = MutableLiveData<String>()

    val searchResultLiveData: LiveData<List<SearchResultItem>> =
        Transformations.switchMap(mutableQuery) { query ->
            repository.performSearch(query)
        }

    fun performSearch(query: String) {
        mutableQuery.value = query
    }
}