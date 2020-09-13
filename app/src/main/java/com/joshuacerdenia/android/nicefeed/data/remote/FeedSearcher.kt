package com.joshuacerdenia.android.nicefeed.data.remote

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem
import com.joshuacerdenia.android.nicefeed.data.remote.service.SearchResult
import com.joshuacerdenia.android.nicefeed.data.remote.service.FetcherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLEncoder

private const val TAG = "FeedSearcher"

class FeedSearcher {

    private var feedSearchResultItems: List<SearchResultItem>? = null

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val fetcherService: FetcherService = retrofit.create(FetcherService::class.java)

    fun performSearch(query: String): LiveData<List<SearchResultItem>> {
        val path = generatePath(query)
        val searchRequest: Call<SearchResult> = fetcherService.fetchSearchResult(path)

        return fetchSearchResult(searchRequest)
    }

    private fun generatePath(query: String): String {
        val path = Uri.Builder()
            .path("v3/search/feeds")
            .appendQueryParameter("count", "50")
            .appendQueryParameter("locale", "en") // Change depending on locale?
            .appendQueryParameter("query", URLEncoder.encode(query, "UTF-8"))
            .build()
            .toString()

        Log.d(TAG, "URL generated: $BASE_URL$path")
        return path
    }

    private fun fetchSearchResult(searchRequest: Call<SearchResult>):
            MutableLiveData<List<SearchResultItem>> {

        val searchResultLiveData = MutableLiveData<List<SearchResultItem>>()

        searchRequest.enqueue(object : Callback<SearchResult> {
            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                Log.d(TAG, "No response received", t)
            }

            override fun onResponse(
                call: Call<SearchResult>,
                response: Response<SearchResult>
            ) {
                val feedSearchResult: SearchResult? = response.body()
                feedSearchResultItems = feedSearchResult?.items
                Log.d(TAG, "Response received!")

                searchResultLiveData.value = feedSearchResultItems
            }
        })

        return searchResultLiveData
    }

    companion object {
        private const val BASE_URL = "https://cloud.feedly.com/"
    }
}