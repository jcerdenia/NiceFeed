package com.joshuacerdenia.android.nicefeed.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.joshuacerdenia.android.nicefeed.data.model.FeedManageable
import com.joshuacerdenia.android.nicefeed.utils.extensions.sortedByCategory
import com.rometools.opml.feed.opml.Opml
import com.rometools.opml.feed.opml.Outline
import com.rometools.opml.io.impl.OPML20Generator
import com.rometools.rome.io.WireFeedOutput
import java.io.OutputStreamWriter
import java.net.URL
import java.util.*
import java.util.concurrent.Executors

class OpmlExporter(
    context: Context,
    private val listener: ExportResultListener
) {

    private val contentResolver = context.contentResolver
    private val executor = Executors.newSingleThreadExecutor()
    private var feeds = listOf<FeedManageable>()
        get() = field.sortedByCategory()
    private var categories = arrayOf<String>()

    interface ExportResultListener {
        fun onExportAttempted(isSuccessful: Boolean, fileName: String?)
    }

    fun submitFeeds(feeds: List<FeedManageable>) {
        this.feeds = feeds
        categories = feeds.map { feed -> feed.category }.toSet().toTypedArray()
    }

    fun submitUri(uri: Uri) {
        val outputStream = contentResolver.openOutputStream(uri)
        if (outputStream != null) {
            executor.execute {
                try {
                    OutputStreamWriter(outputStream, Charsets.UTF_8).use { writer ->
                        if (feeds.isNotEmpty()) exportOpml(writer)
                    }

                    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                        if (cursor.moveToFirst()) {
                            val fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            listener.onExportAttempted(true, fileName)
                        }
                    }
                } catch (e: Exception) {
                    listener.onExportAttempted(false, null)
                }
            }
        } else {
            listener.onExportAttempted(false, null)
        }
    }

    private fun exportOpml(writer: OutputStreamWriter) {
        val opml = Opml().apply {
            feedType = OPML20Generator().type
            encoding = "utf-8"
            created = Date()
            outlines = categories.map { category ->
                Outline(category, null, null).apply {
                    children = feeds.filter { feed -> feed.category == category }.map { feed ->
                        Outline(feed.title, URL(feed.url), URL(feed.website))
                    }
                }
            }
        }

        WireFeedOutput().output(opml, writer)
    }
}