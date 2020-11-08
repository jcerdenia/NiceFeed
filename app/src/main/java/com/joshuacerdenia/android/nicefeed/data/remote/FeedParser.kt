package com.joshuacerdenia.android.nicefeed.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joshuacerdenia.android.nicefeed.data.model.entry.Entry
import com.joshuacerdenia.android.nicefeed.data.model.feed.Feed
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.util.BackupUrlManager
import com.joshuacerdenia.android.nicefeed.util.NetworkMonitor
import com.joshuacerdenia.android.nicefeed.util.extensions.shortened
import com.prof.rssparser.Channel
import com.prof.rssparser.Parser
import java.text.SimpleDateFormat
import java.util.*

// Responsible for retrieving and parsing RSS feeds

class FeedParser (private val networkMonitor: NetworkMonitor) {

    private lateinit var rssParser: Parser
    private val _feedRequestLiveData = MutableLiveData<FeedWithEntries>()
    val feedRequestLiveData: LiveData<FeedWithEntries?>
        get() = _feedRequestLiveData

    suspend fun getFeedSynchronously(url: String): FeedWithEntries? {
        rssParser = Parser.Builder().build()
        return if (networkMonitor.isOnline) {
            try {
                val channel = rssParser.getChannel(url)
                ChannelMapper.makeFeedWithEntries(url, channel)
            } catch(e: Exception) {
                null
            }
        } else null
    }

    suspend fun requestFeed(url: String, backup: String? = null) {
        rssParser = Parser.Builder().build()
        if (networkMonitor.isOnline) {
            BackupUrlManager.setBase(backup)
            executeRequest(url)
        } else _feedRequestLiveData.postValue(null)
    }

    fun cancelRequest() {
        rssParser.cancel()
        BackupUrlManager.reset()
    }

    private suspend fun executeRequest(url: String) {
        // Automatically makes several requests with different possible URLs
        Log.d(TAG, "Requesting $url")

        try {
            val channel = rssParser.getChannel(url)
            val feedWithEntries = ChannelMapper.makeFeedWithEntries(url, channel)
            _feedRequestLiveData.postValue(feedWithEntries)
        } catch (e: Exception) {
            // If the initial request fails, try backup URL in different variations
            BackupUrlManager.getNextUrl()?.let { executeRequest(it) }
                ?: let {
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

            Log.d(TAG, "Retrieved ${entries.size} entries from $url")
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
                } else break
            }
            return entries
        }

        private fun parseDate(stringDate: String?): Date? {
            return if (stringDate != null) {
                SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH).parse(stringDate)
            } else null
        }

        private fun String?.flagAsExcerpt() = FLAG_EXCERPT + this
    }

    companion object {

        private const val TAG = "FeedParser"
        private const val UNTITLED = "Untitled"
        const val FLAG_EXCERPT = "com.joshuacerdenia.android.nicefeed.excerpt "
    }
}