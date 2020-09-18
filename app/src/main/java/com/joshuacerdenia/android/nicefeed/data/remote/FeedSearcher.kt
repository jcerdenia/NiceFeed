package com.joshuacerdenia.android.nicefeed.data.remote

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem
import com.joshuacerdenia.android.nicefeed.data.remote.api.FeedlyApi
import com.joshuacerdenia.android.nicefeed.data.remote.api.SearchResult
import com.joshuacerdenia.android.nicefeed.utils.NetworkMonitor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLEncoder

private const val TAG = "FeedSearcher"

// Search engine

class FeedSearcher(private val networkMonitor: NetworkMonitor) {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val feedlyApi = retrofit.create(FeedlyApi::class.java)

    fun getFeedList(query: String): LiveData<List<SearchResultItem>> {
        return if (networkMonitor.isOnline) {
            val path = generatePath(query)
            val searchRequest: Call<SearchResult> = feedlyApi.fetchSearchResult(path)
            fetchSearchResult(searchRequest)
        } else {
            MutableLiveData(emptyList())
        }
    }

    private fun generatePath(query: String): String {
        return Uri.Builder()
            .path("v3/search/feeds")
            .appendQueryParameter("count", "50")
            .appendQueryParameter("locale", "en") // Change depending on locale?
            .appendQueryParameter("query", URLEncoder.encode(query, "UTF-8"))
            .build()
            .toString()
    }

    private fun fetchSearchResult(
        searchRequest: Call<SearchResult>
    ): MutableLiveData<List<SearchResultItem>> {
        val searchResultLiveData = MutableLiveData<List<SearchResultItem>>()
        val callback = object : Callback<SearchResult> {
            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                // Do nothing
            }

            override fun onResponse(
                call: Call<SearchResult>,
                response: Response<SearchResult>
            ) {
                val feedSearchResult = response.body()
                searchResultLiveData.value = feedSearchResult?.items ?: emptyList()
            }
        }

        searchRequest.enqueue(callback)
        return searchResultLiveData
    }

    companion object {
        private const val BASE_URL = "https://cloud.feedly.com/"
    }
}