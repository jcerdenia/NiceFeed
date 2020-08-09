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
import com.joshuacerdenia.android.nicefeed.utils.simplified
import com.prof.rssparser.Channel
import com.prof.rssparser.Parser
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "FeedParser"
private const val NO_TITLE = "No Title"

class FeedParser: ViewModel() {

    private val parser = Parser.Builder().build()
    private val mapper = ChannelMapper()
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
                val feedWithEntries = mapper.makeFeedWithEntries(mainUrl, channel)
                _feedRequestLiveData.postValue(feedWithEntries)

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

    private class ChannelMapper {
        // Maps RSS Parser library data classes to my own

        val maxEntries = 300 // Arbitrary

        fun makeFeedWithEntries(url: String, channel: Channel): FeedWithEntries {
            val entries = mapEntries(channel, url)
            val feed = Feed(
                url = url, // The url that successfully completes the request is applied
                website = channel.link ?: url,
                title = channel.title ?: channel.link?.simplified() ?: url.simplified(),
                description = channel.description,
                imageUrl = channel.image?.url,
                unreadCount = entries.size
            )

            Log.d(TAG, "${entries.size} entries retrieved")
            return FeedWithEntries(feed, entries)
        }

        private fun mapEntries(channel: Channel, url: String): List<Entry> {
            val entries = mutableListOf<Entry>()
            for (article in channel.articles) {
                if (entries.size < maxEntries) {
                    val entry = Entry(
                        url = article.link ?: article.guid ?: "",
                        website = channel.link ?: url,
                        title = article.title ?: NO_TITLE,
                        author = article.author ?: channel.title,
                        content = article.content ?: article.description,
                        date = parseDate(article.pubDate),
                        image = article.image
                    )

                    if (entry.url.isNotEmpty()) {
                        entries.add(entry)
                    }
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