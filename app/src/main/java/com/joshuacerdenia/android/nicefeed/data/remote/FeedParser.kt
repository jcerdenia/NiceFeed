package com.joshuacerdenia.android.nicefeed.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.utils.BackupUrlManager
import com.joshuacerdenia.android.nicefeed.utils.NetworkMonitor
import com.joshuacerdenia.android.nicefeed.utils.extensions.shortened
import com.prof.rssparser.Channel
import com.prof.rssparser.Parser
import java.text.SimpleDateFormat
import java.util.*

private fun String?.flagAsExcerpt() = FeedParser.FLAG_EXCERPT + this

// Responsible for retrieving and parsing RSS feeds

private const val TAG = "FeedParser"

class FeedParser (private val networkMonitor: NetworkMonitor) {

    private val rssParser = Parser.Builder().build()
    private val _feedRequestLiveData = MutableLiveData<FeedWithEntries>()
    val feedRequestLiveData: LiveData<FeedWithEntries?>
        get() = _feedRequestLiveData

    suspend fun getFeedSynchronously(url: String): FeedWithEntries? {
        return if (networkMonitor.isOnline) {
            try {
                val channel = rssParser.getChannel(url)
                ChannelMapper.makeFeedWithEntries(url, channel)
            } catch(e: Exception) {
                e.printStackTrace()
                null
            }
        } else {
            null
        }
    }

    suspend fun requestFeed(url: String, backup: String? = null) {
        if (networkMonitor.isOnline) {
            executeRequest(url, backup)
        } else {
            _feedRequestLiveData.postValue(null)
        }
    }

    private suspend fun executeRequest(url: String, backup: String? = null) {
        // Automatically makes several requests with different possible URLs
        Log.d(TAG, "Requesting $url...")
        BackupUrlManager.setBase(backup)

        try {
            val channel = rssParser.getChannel(url)
            val feedWithEntries = ChannelMapper.makeFeedWithEntries(url, channel)
            _feedRequestLiveData.postValue(feedWithEntries)

        } catch (e: Exception) {
            e.printStackTrace()
            // If the initial request fails, try backup URL in different variations
            val nextUrl = BackupUrlManager.getUrl()
            if (nextUrl != null) {
                requestFeed(nextUrl) // Try the next URL
                BackupUrlManager.countUp() // Ticked when a request fails
            } else {
                _feedRequestLiveData.postValue(null)
                Log.d(TAG, "Request failed")
            }
        }
    }

    // Maps "Channel" data to my own model objects
    private object ChannelMapper {
        private const val MAX_ENTRIES = 300 // Arbitrary
        private const val DATE_PATTERN = "EEE, d MMM yyyy HH:mm:ss Z"

        fun makeFeedWithEntries(url: String, channel: Channel): FeedWithEntries {
            val entries = mapEntries(channel, url)
            val feed = Feed(
                url = url, // The url that successfully completes the request is applied
                website = channel.link ?: url,
                title = channel.title ?: channel.link?.shortened() ?: url.shortened(),
                description = channel.description,
                imageUrl = channel.image?.url ?: channel.image?.link,
                unreadCount = entries.size
            )

            Log.d(TAG, "${entries.size} entries retrieved")
            return FeedWithEntries(feed, entries)
        }

        private fun mapEntries(channel: Channel, url: String): List<Entry> {
            val entries = mutableListOf<Entry>()
            for (article in channel.articles) {
                if (entries.size < MAX_ENTRIES) {
                    val entry = Entry(
                        url = article.link ?: article.guid ?: "",
                        website = channel.link ?: url,
                        title = article.title ?: UNTITLED,
                        author = article.author,
                        content = article.content ?: article.description.flagAsExcerpt(),
                        date = parseDate(article.pubDate),
                        image = article.image
                    )
                    entries.add(entry)
                } else {
                    break
                }
            }
            return entries
        }

        private fun parseDate(stringDate: String?): Date? {
            return if (stringDate != null) {
                SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH).parse(stringDate)
            } else {
                null
            }
        }
    }

    companion object {
        private const val UNTITLED = "Untitled"
        const val FLAG_EXCERPT = "com.joshuacerdenia.android.nicefeed.excerpt "
    }
}