package com.joshuacerdenia.android.nicefeed.utils

import android.content.Context
import android.util.Base64
import android.util.Base64.encodeToString
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences.TEXT_SIZE_LARGE
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences.TEXT_SIZE_LARGER
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import java.text.DateFormat

class EntryToHtmlFormatter(context: Context) {

    private val linkColor = "#444E64"
    private val fontSize = when (NiceFeedPreferences.getTextSize(context)) {
        TEXT_SIZE_LARGE -> "large"
        TEXT_SIZE_LARGER -> "x-large"
        else -> "medium"
    }
    private val style = "<style>" +
            "* {max-width:100%}" +
            "body {font-size:$fontSize; font-family:Roboto, sans-serif; word-wrap:break-word}" +
            "#subtitle {color:gray}" +
            "a {color:$linkColor}" +
            "img, figure {display:block; margin-left:auto; margin-right:auto; height:auto; max-width:100%}" +
            "iframe {width:100%}" +
            "</style>"

    fun format(entry: Entry): String {
        val title = "<h2 id=\"title\">${entry.title}</h2>"
        val formattedDate = entry.date?.let { date ->
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(date)
        } ?: ""
        val subtitle = if (entry.author != null) {
            "<p id=\"subtitle\">$formattedDate – ${entry.author}</p>"
        } else {
            "<p id=\"subtitle\">$formattedDate</p>"
        }
        val content = entry.content ?: ""
        val html = StringBuilder(style)
            .append("<body>")
            .append(title)
            .append(subtitle)
            .append(content)
            .append("</body")
            .toString()

        return encodeToString(html.toByteArray(), Base64.NO_PADDING)
    }
}