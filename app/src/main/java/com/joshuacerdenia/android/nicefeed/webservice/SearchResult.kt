package com.joshuacerdenia.android.nicefeed.webservice

import com.google.gson.annotations.SerializedName
import com.joshuacerdenia.android.nicefeed.SearchResultItem

class SearchResult {
    @SerializedName("results")
    lateinit var items: List<SearchResultItem>
}