package com.joshuacerdenia.android.nicefeed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof.rssparser.Channel
import com.prof.rssparser.Parser
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "FeedParser"
private const val MAX_ENTRIES = 500 // Arbitrary

class FeedParser: ViewModel() {

    private val parser = Parser.Builder().build()
    private val channelConverter = ChannelConverter()

    private val _feedRequestLiveData = MutableLiveData<FeedWithEntries>()
    val feedRequestLiveData: LiveData<FeedWithEntries>?
        get() = _feedRequestLiveData

    fun requestFeed(url: String) {
        // Automatically makes several requests with different possible URLs

        Log.d(TAG, "Requesting: $url")

        viewModelScope.launch {
            try {
                val channel = parser.getChannel(url)
                val feedWithEntries = channelConverter.makeFeedWithEntries(url, channel)

                _feedRequestLiveData.postValue(
                    if (feedWithEntries.feed.website.isNotEmpty()) {
                        feedWithEntries
                    } else {
                        null
                    }
                )

                BackupUrls.setBase(null) // Reset if request succeeds

            } catch (e: Exception) {
                e.printStackTrace()

                // If the initial request fails, try backup URL in different variations
                if (BackupUrls.get() != null) {
                    requestFeed(BackupUrls.get()!!) // Keep trying until the request succeeds
                    BackupUrls.countUp() // Ticked when a request fails
                } else {
                    _feedRequestLiveData.postValue(null)
                }
            }
        }
    }

    private class ChannelConverter {
        // Converts RSS Parser library data classes to my own

        fun makeFeedWithEntries(url: String, channel: Channel): FeedWithEntries {
            val entries = mutableListOf<Entry>()
            val feed = Feed()

            for (article in channel.articles) {
                if (entries.size < MAX_ENTRIES) {
                    val entry = Entry()
                    entry.apply {
                        guid = article.guid ?: article.link ?: ""
                        this.url = article.link // Possibly not needed?
                        website = channel.link
                        title = article.title
                        description = article.description
                        content = article.content
                        date = parseDate(article.pubDate)
                        image = article.image
                    }
                    entries.add(entry)
                }
            }

            feed.apply {
                website = channel.link ?: ""
                this.url = url // The URL that successfully completes the request is applied
                title = channel.title
                description = channel.description
                updated = parseDate(channel.lastBuildDate)
                imageUrl = channel.image?.url
                unreadCount = entries.size
            }

            Log.d(TAG, "${entries.size} entries obtained")
            return FeedWithEntries(feed, entries)
        }

        private fun parseDate(stringDate: String?): Date? {
            val pattern = "EEE, d MMM yyyy HH:mm:ss Z"
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)

            return if (stringDate != null) {
                simpleDateFormat.parse(stringDate)
            } else {
                null
            }
        }
    }
}