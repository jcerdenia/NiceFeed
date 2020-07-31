package com.joshuacerdenia.android.nicefeed.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.rometools.opml.feed.opml.Opml
import com.rometools.rome.io.WireFeedInput
import java.io.BufferedInputStream
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringReader
import java.util.concurrent.Executors

private const val TAG = "OpmlUtil"

class OpmlUtil(
    private val context: Context?,
    private val listener: OnOpmlParsedListener
) {

    private val executor = Executors.newSingleThreadExecutor()
    private val contentResolver: ContentResolver? = context?.contentResolver

    interface OnOpmlParsedListener {
        fun onOpmlParsed(feeds: List<Feed>)
    }

    fun importOpml(uri: Uri) {
        executor.execute {
            try {
                Log.d(TAG, "Trying... Btw context is not null? ${context != null}")

                val reader = InputStreamReader(contentResolver?.openInputStream(uri)!!)
                parseOpml(reader)

                Log.d(TAG, "Import successful! Parsing...")

                //contentResolver?.openInputStream(uri)?.let {
                //InputStreamReader(it).use { reader -> parseOpml(reader)}
                //Log.d(TAG, "Import successful! Parsing...")
                //}
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "Fuck!")

                /*
                try {
                    val content = BufferedInputStream(
                        contentResolver?.openInputStream(uri)!!
                    ).bufferedReader().use { it.readText() }

                    val fixedReader = StringReader(
                        content.replace("<opml version=['\"][0-9]\\.[0-9]['\"]>".toRegex(), "<opml>")
                    )

                    parseOpml(fixedReader)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d(TAG, "An error has occurred... of fucking course") */
            }
        }
    }

    private fun parseOpml(opmlReader: Reader) {
        val feeds = mutableListOf<Feed>()
        val opml = WireFeedInput().build(opmlReader) as Opml

        Log.d(TAG, "OKay, we're actually trying to parse now... ${opml.outlines}")

        for (outline in opml.outlines) {
            if (outline.xmlUrl != null || outline.children.isNotEmpty()) {
                val feed = Feed(
                    website = outline.children[0].htmlUrl ?: outline.children[0].xmlUrl ?: "",
                    url = outline.children[0].xmlUrl ?: "",
                    title = outline.children[0].title ?: outline.children[0].xmlUrl.substringAfter("://"),
                    unreadCount = 0
                )

                Log.d(TAG, "Okay, got this Feed: $feed")

                if (feed.website.isNotEmpty() && feed.url.isNotEmpty()) {
                    feeds.add(feed)
                }
            }
        }

        Log.d(TAG, "Parsed ${feeds.size} feeds")
        listener.onOpmlParsed(feeds)
    }
}