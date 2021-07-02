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
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jdom2.Element
import java.util.concurrent.TimeUnit

class FeedFetcher {

    private var _feedRequestLiveData = MutableLiveData<FeedWithEntries?>()
    val feedRequestLiveData: LiveData<FeedWithEntries?>
        get() = _feedRequestLiveData

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
        _feedRequestLiveData = MutableLiveData<FeedWithEntries?>()
    }

    fun request(url: String) {
        try {
            createCall(url).execute().use { response ->
                val stream = response.body()?.byteStream()
                val xmlReader = XmlReader(stream)
                val rawFeed = SyndFeedInput().build(xmlReader)

                val feed = parseFeed(url, rawFeed)
                Log.d(TAG, "Got feed: $feed")

                val entries = rawFeed.entries.map { parseEntry(it) }
                Log.d(TAG, "Got ${entries.size} entries")
                Log.d(TAG, "Entry images: ${entries.map { it.image }}")

                _feedRequestLiveData.postValue(FeedWithEntries(feed, entries))
            }
        } catch(e: Error) {
            Log.d(TAG, "Error: $e")
            _feedRequestLiveData.postValue(null)
        }
    }

    private fun parseFeed(url: String, rawFeed: SyndFeed): Feed {
        return Feed(
            url = url,
            title = rawFeed.title,
            website = rawFeed.link,
            description = rawFeed.description,
            imageUrl = rawFeed.image?.url,
            category = "Uncategorized",
            unreadCount = 0
        )
    }
    
    private fun parseEntry(rawEntry: SyndEntry): Entry {
        val content = rawEntry.contents?.joinToString { it.value }

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
                Log.d(TAG, "Found image: ${enclosure.url}")
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
                        Log.d(TAG, "Found image: ${attr.value}")
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

    companion object {

        const val TAG = "FeedFetcher"
    }
}