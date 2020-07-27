package com.joshuacerdenia.android.nicefeed.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.utils.BackupUrlManager
import com.prof.rssparser.Channel
import com.prof.rssparser.Parser
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "FeedParser"
private const val MAX_ENTRIES = 500 // Arbitrary

class FeedParser: ViewModel() {

    private val parser = Parser.Builder().build()
    private val converter = ChannelConverter()
    private val backupUrlManager = BackupUrlManager()

    private val _feedRequestLiveData = MutableLiveData<FeedWithEntries>()
    val feedRequestLiveData: LiveData<FeedWithEntries>?
        get() = _feedRequestLiveData

    fun requestFeed(vararg url: String) {
        // Automatically makes several requests with different possible URLs

        val mainUrl = url.first()
        backupUrlManager.setBase(if (url.size > 1) {
            url.last() // Only up to two initial urls are expected
        } else {
            null
        })

        Log.d(TAG, "Requesting $mainUrl...")

        viewModelScope.launch {
            try {
                val channel = parser.getChannel(mainUrl)
                val feedWithEntries = converter.makeFeedWithEntries(mainUrl, channel)

                _feedRequestLiveData.postValue(
                    if (feedWithEntries.feed.website.isNotEmpty()) {
                        feedWithEntries
                    } else {
                        null
                    }
                )

            } catch (e: Exception) {
                e.printStackTrace()

                // If the initial request fails, try backup URL in different variations
                if (backupUrlManager.getUrl() != null) {
                    requestFeed(backupUrlManager.getUrl()!!) // Try the next URL
                    backupUrlManager.countUp() // Ticked when a request fails
                } else {
                    _feedRequestLiveData.postValue(null)
                    Log.d(TAG, "Request failed")
                }
            }
        }
    }

    private class ChannelConverter {
        // Converts RSS Parser library data classes to my own

        fun makeFeedWithEntries(url: String, channel: Channel): FeedWithEntries {
            val entries = mapEntries(channel)
            val feed = Feed(
                website = channel.link ?: "",
                url = url, // The url that successfully completes the request is applied
                title = channel.title,
                description = channel.description,
                updated = parseDate(channel.lastBuildDate),
                imageUrl = channel.image?.url,
                unreadCount = entries.size
            )

            Log.d(TAG, "${entries.size} entries obtained")
            return FeedWithEntries(feed, entries)
        }

        private fun mapEntries(channel: Channel): List<Entry> {
            val entries = mutableListOf<Entry>()
            for (article in channel.articles) {
                if (entries.size < MAX_ENTRIES) {
                    val entry = Entry()
                    entry.apply {
                        guid = article.guid ?: article.link ?: ""
                        //this.url = article.link // Possibly not needed?
                        website = channel.link
                        title = article.title
                        description = article.description
                        author = article.author ?: channel.title
                        content = article.content
                        date = parseDate(article.pubDate)
                        image = article.image
                    }
                    entries.add(entry)
                } else {
                    break
                }
            }
            return entries
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