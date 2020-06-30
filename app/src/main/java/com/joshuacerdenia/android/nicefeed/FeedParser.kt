package com.joshuacerdenia.android.nicefeed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof.rssparser.Channel
import com.prof.rssparser.Parser
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "FeedParser"

class FeedParser: ViewModel() {

    private val parser = Parser.Builder().build()
    private val channelConverter = ChannelConverter()

    private val _feedRequestLiveData = MutableLiveData<Event<FeedWithEntries>>()
    val feedRequestLiveData: LiveData<Event<FeedWithEntries>>
        get() = _feedRequestLiveData

    fun requestFeed(url: String) {
        // Automatically makes up to 4 requests with 4 different possible URLs

        Log.d(TAG, "Requesting: $url")

        viewModelScope.launch {
            try {
                val channel = parser.getChannel(url)
                val feedWithEntries = channelConverter.makeFeedWithEntries(url, channel)

                _feedRequestLiveData.postValue(
                    if (!feedWithEntries.feed.title.isNullOrEmpty()) {
                        Event(feedWithEntries)
                    } else {
                        null
                    }
                )

                BackupUrl.setUrl(null) // Reset if request succeeds

            } catch (e: Exception) {
                e.printStackTrace()

                // If the initial request fails, try backup URL in different variations
                if (BackupUrl.getUrl() != null) {
                    requestFeed(BackupUrl.getUrl()!!) // Keep trying until the request succeeds
                    BackupUrl.countUp() // Ticked when a request fails
                } else {
                    _feedRequestLiveData.postValue(Event(null))
                }
            }
        }
    }

    private class ChannelConverter {
        // Converts RSS Parser library data classes to my own

        fun makeFeedWithEntries(url: String, channel: Channel): FeedWithEntries {
            val feed = Feed()
            val entries = mutableListOf<Entry>()

            feed.apply {
                website = channel.link ?: ""
                this.url = url // The URL that successfully completes the request is applied
                title = channel.title
                description = channel.description
                updated = formatDate(channel.lastBuildDate)
                imageUrl = channel.image?.url
                unreadCount = channel.articles.size
            }

            for (article in channel.articles) {
                val entry = Entry()
                entry.apply {
                    guid = article.guid ?: article.link ?: ""
                    this.url = article.link // Possibly not needed?
                    website = channel.link
                    title = article.title
                    description = article.description
                    date = formatDate(article.pubDate)
                    image = article.image
                }
                entries.add(entry)
            }

            return FeedWithEntries(feed, entries)
        }

        private fun formatDate(stringDate: String?): String? {
            val pattern = "EEE, d MMM yyyy HH:mm:ss Z"
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)

            return if (stringDate != null) {
                val parsedDate: Date = simpleDateFormat.parse(stringDate)!!
                DateFormat.getDateInstance(DateFormat.LONG).format(parsedDate)
            } else {
                null
            }
        }
    }
}