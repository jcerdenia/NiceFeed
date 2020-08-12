package com.joshuacerdenia.android.nicefeed.data.remote.service

import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface FetcherService {

    @GET
    fun fetchSearchResult(@Url url: String): Call<SearchResult>

    @GET
    fun fetchFeedWithEntries(@Url url: String): Call<FeedWithEntries>
}