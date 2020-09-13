package com.joshuacerdenia.android.nicefeed.data.remote.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface FeedlyApi {

    @GET
    fun fetchSearchResult(@Url url: String): Call<SearchResult>
}