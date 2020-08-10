package com.joshuacerdenia.android.nicefeed.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.FeedMinimal
import com.rometools.opml.feed.opml.Opml
import com.rometools.opml.feed.opml.Outline
import com.rometools.opml.io.impl.OPML20Generator
import com.rometools.rome.io.WireFeedOutput
import java.io.OutputStreamWriter
import java.net.URL
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "OpmlExporter"

class OpmlExporter(
    private val context: Context,
    private val view: View
) {

    private val contentResolver: ContentResolver = context.contentResolver
    private val executor = Executors.newSingleThreadExecutor()
    private var feeds = listOf<FeedMinimal>()
        get() = field.sortedBy { it.category }
    private var categories = arrayOf<String>()

    fun submitFeeds(feeds: List<FeedMinimal>, categories: Array<String>) {
        this.feeds = feeds
        this.categories = categories
    }

    fun submitUri(uri: Uri) {
        val outputStream = contentResolver.openOutputStream(uri)
        if (outputStream != null) {
            executor.execute {
                try {
                    OutputStreamWriter(outputStream, Charsets.UTF_8).use { writer ->
                        if (feeds.isNotEmpty()) {
                            exportOpml(writer)
                        }
                    }

                    contentResolver.query(
                        uri,
                        null,
                        null,
                        null,
                        null
                    )?.use { cursor ->
                        if (cursor.moveToFirst()) {
                            val fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))

                            Snackbar.make(
                                view,
                                context.getString(R.string.exported_opml_message, fileName),
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    showErrorMessage()
                }
            }
        } else {
            showErrorMessage()
        }
    }

    private fun exportOpml(writer: OutputStreamWriter) {
        val opml = Opml().apply {
            feedType = OPML20Generator().type
            encoding = "utf-8"
            created = Date()
            outlines = categories.map { category ->
                Outline(
                    category,
                    null,
                    null
                ).apply {
                    children = feeds.filter { feed ->
                        feed.category == category
                    }.map { feed ->
                        Outline(
                            feed.title,
                            URL(feed.url),
                            URL(feed.website)
                        )
                    }
                }
            }
        }

        WireFeedOutput().output(opml, writer)
    }

    private fun showErrorMessage() {
        Snackbar.make(
            view,
            context.getString(R.string.error_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}