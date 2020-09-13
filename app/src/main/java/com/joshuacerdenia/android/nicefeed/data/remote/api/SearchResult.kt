package com.joshuacerdenia.android.nicefeed.data.remote.api

import com.google.gson.annotations.SerializedName
import com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem

class SearchResult {
    @SerializedName("results")
    lateinit var items: List<SearchResultItem>
}