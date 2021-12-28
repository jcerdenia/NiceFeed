package com.joshuacerdenia.android.nicefeed.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.data.model.entry.Entry
import com.joshuacerdenia.android.nicefeed.data.model.feed.Feed
import com.joshuacerdenia.android.nicefeed.util.extensions.shortened
import com.rometools.rome.feed.synd.SyndEnclosure
import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jdom2.Element
import java.util.concurrent.TimeUnit

class FeedFetcher {

    private var _feedWithEntriesLive = MutableLiveData<FeedWithEntries?>()
    val feedWithEntriesLive: LiveData<FeedWithEntries?> get() = _feedWithEntriesLive

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    private fun createCall(url: String): Call {
        val request = Request.Builder()
            .url(url)
            .header("User-agent", "Mozilla/5.0 (compatible) AppleWebKit Chrome Safari") // some feeds need this to work properly
            .addHeader("accept", "*/*")
            .build()
        return client.newCall(request)
    }

    fun reset() {
        _feedWithEntriesLive = MutableLiveData<FeedWithEntries?>()
    }

    fun requestSynchronously(url: String): FeedWithEntries? {
        createCall(url).execute().use { response ->
            val stream = response.body()?.byteStream()
            val xmlReader = XmlReader(stream)
            val rawFeed = SyndFeedInput().build(xmlReader)

            val feed = FeedParser.fromRawFeed(url, rawFeed)
            Log.d(TAG, "Got feed: $feed")

            val entries = rawFeed.entries.map { EntryParser.fromRawEntry(it) }
            Log.d(TAG, "Got ${entries.size} entries")
//            Log.d(TAG, "Entry images: ${entries.map { it.image }}")
            return FeedWithEntries(feed, entries)
        }
    }

    suspend fun request(url: String) {
        try {
            val feedWithEntries = withContext(Dispatchers.IO) {
                requestSynchronously(url)
            }
            _feedWithEntriesLive.postValue(feedWithEntries)
        } catch(e: Error) {
            Log.d(TAG, "Error: $e")
            _feedWithEntriesLive.postValue(null)
        }
    }

    private object FeedParser {

        fun fromRawFeed(url: String, rawFeed: SyndFeed): Feed {
            return Feed(
                url = url,
                title = rawFeed.title,
                website = rawFeed.link,
                description = rawFeed.description,
                imageUrl = rawFeed.image?.url,
                unreadCount = rawFeed.entries.size
            )
        }
    }

    private object EntryParser {

        fun fromRawEntry(rawEntry: SyndEntry): Entry {
            val content: String

            if (rawEntry.contents.size != 0) {
                content = rawEntry.contents?.joinToString { it.value }.toString()
            } else {
                content = rawEntry.description.value
            }

            return Entry(
                url = rawEntry.link,
                title = rawEntry.title,
                website = rawEntry.source?.link ?: rawEntry.link.shortened(),
                author = rawEntry.author,
                date = rawEntry.updatedDate ?: rawEntry.publishedDate,
                content = content,
                image = parseImageFromEnclosures(rawEntry.enclosures)
                    ?: parseImageFromForeignMarkup(rawEntry.foreignMarkup)
                    ?: parseImageFromContent(content)
            )
        }

        private fun parseImageFromEnclosures(enclosures: List<SyndEnclosure>): String? {
            Log.d(TAG, "Checking entry enclosures")
            enclosures.forEach { enclosure ->
                if (enclosure.type.contains("image")) {
//                    Log.d(TAG, "Found image: ${enclosure.url}")
                    return enclosure.url
                }
            }

            return null
        }

        private fun parseImageFromForeignMarkup(elements: List<Element>): String? {
            Log.d(TAG, "Checking foreign markup")
            elements.forEach { element ->
                if (element.namespace?.prefix == "media" && element.name == "content") {
                    element.attributes.forEach { attr ->
                        if (attr.name == "url") {
//                            Log.d(TAG, "Found image: ${attr.value}")
                            return attr.value
                        }
                    }
                }
            }

            return null
        }

        private fun parseImageFromContent(content: String?): String? {
            // Find HTML image tag.
            val img = content
                ?.substringAfter("<img", "")
                ?.substringBefore("/>", "")
                ?: ""

            return if (img.isNotEmpty()) {
                img.substringAfter("src=\"").substringBefore("\"")
            } else {
                null
            }
        }
    }

    companion object {
        const val FLAG_EXCERPT = "com.joshuacerdenia.android.nicefeed.excerpt "
        const val TAG = "FeedFetcher"
    }
}