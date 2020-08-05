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

class OpmlImporter(
    context: Context,
    private val listener: OnOpmlParsedListener
) {

    private val contentResolver: ContentResolver = context.contentResolver
    private val executor = Executors.newSingleThreadExecutor()

    interface OnOpmlParsedListener {
        fun onOpmlParsed(feeds: List<Feed>)
        fun onParseOpmlFailed()
    }

    fun submitUri(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        if (inputStream == null) {
            listener.onParseOpmlFailed()
            return
        }

        executor.execute {
            try {
                val reader = InputStreamReader(inputStream)
                parseOpml(reader)

            } catch (e: Exception) {
                e.printStackTrace()

                try {
                    val content = BufferedInputStream(inputStream).bufferedReader().readText()
                    val fixedReader = StringReader(content.replace(
                            "<opml version=['\"][0-9]\\.[0-9]['\"]>".toRegex(),
                            "<opml>"
                    ))

                    parseOpml(fixedReader)

                } catch (e: Exception) {
                    e.printStackTrace()
                    listener.onParseOpmlFailed()
                }
            }
        }
    }

    private fun parseOpml(opmlReader: Reader) {
        val feeds = mutableListOf<Feed>()
        val opml = WireFeedInput().build(opmlReader) as Opml

        for (outline in opml.outlines) {
            Log.d(TAG, "Parsing... $outline")

            if (outline.xmlUrl != null || outline.children.isNotEmpty()) {

                val feed = Feed(
                    url = outline.children[0].xmlUrl
                        ?: "",
                    website = outline.children[0].htmlUrl
                        ?: outline.children[0].xmlUrl
                        ?: "",
                    title = outline.children[0].title
                        ?: outline.children[0].xmlUrl.substringAfter("://"),
                    unreadCount = 0
                )

                if (feed.url.isNotEmpty()) {
                    feeds.add(feed)
                }

                /*
                for (children in outline.children) {
                    val feed = Feed(
                        url = children.xmlUrl ?: "",
                        website = children.htmlUrl ?: children.xmlUrl ?: "",
                        title = children.title ?: children.xmlUrl.substringAfter("://"),
                        unreadCount = 0
                    )*/

            }
        }

        Log.d(TAG, "Parsed ${feeds.size} feeds")
        listener.onOpmlParsed(feeds)

        /*
        opml.outlines.forEach { outline ->
            //Log.d(TAG, "Parsing... $outline")

            if (outline.xmlUrl != null || outline.children.isNotEmpty()) {
                val category = outline.title

                Log.d(TAG, "Parsed category: $category")

                outline.children.filter { it.xmlUrl != null }.forEach {

                    val feed = Feed(
                        url = it.xmlUrl,
                        title = it.title,
                        website = it.htmlUrl,
                        category = category,
                        unreadCount = 0
                    )

                    Log.d(TAG, "Parsed feed: ${feed.title}")
                    feeds.add(feed)
                }
            }
        }

        listener.onOpmlParsed(feeds)*/
    }
}