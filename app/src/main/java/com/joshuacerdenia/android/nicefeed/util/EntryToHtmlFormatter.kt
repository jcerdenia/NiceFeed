package com.joshuacerdenia.android.nicefeed.util

import android.util.Base64
import android.util.Base64.encodeToString
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences.FONT_SERIF
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences.TEXT_SIZE_LARGE
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences.TEXT_SIZE_LARGER
import com.joshuacerdenia.android.nicefeed.data.model.entry.EntryMinimal
import java.text.DateFormat

// Prepares the contents of an Entry to be loaded into a WebView

class EntryToHtmlFormatter(
    textSizeKey: Int,
    font: Int,
    private val includeHeader: Boolean
) {

    private val fontFamily = if (font == FONT_SERIF) "serif" else "sans-serif"
    private val textSize = when (textSizeKey) {
        TEXT_SIZE_LARGE -> "large"
        TEXT_SIZE_LARGER -> "x-large"
        else -> "medium"
    }

    private val style = OPEN_STYLE_TAG +
            "* {max-width:100%}" +
            "body {font-size:$textSize; font-family:$fontFamily; word-wrap:break-word; line-height:1.4}" +
            "h1, h2, h3, h4, h5, h6 {line-height:normal}" +
            "#subtitle {color:gray}" +
            "a:link, a:visited, a:hover, a:active {color:$LINK_COLOR; text-decoration:none; font-weight:bold}" +
            "pre, code {white-space:pre-wrap; word-break:keep-all}" +
            "img, figure {display:block; margin-left:auto; margin-right:auto; height:auto; max-width:100%}" +
            "iframe {width:100%}" +
            CLOSE_STYLE_TAG

    private var title = ""
    private var subtitle = ""

    // Outputs an HTML string
    fun getHtml(entry: EntryMinimal): String {
        if (includeHeader) {
            title = "<h2>${entry.title}</h2>"
            val formattedDate = entry.date?.let { date ->
                DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(date)
            }
            subtitle = when {
                entry.author.isNullOrEmpty() -> "<p $ID_SUBTITLE>$formattedDate</p>"
                formattedDate.isNullOrEmpty() -> "<p $ID_SUBTITLE>${entry.author}</p>"
                else -> "<p $ID_SUBTITLE>$formattedDate – ${entry.author}</p>"
            }
        }

        val content = entry.content.run (::removeStyle)
        val html = StringBuilder(style)
            .append("<body>")
            .append(title)
            .append(subtitle)
            .append(content)
            .append("</body>")
            .toString()

        return encodeToString(html.toByteArray(), Base64.NO_PADDING)
    }

    private fun removeStyle(content: String): String {
        var base = content
        var editedContent = content
        // Remove all <style></style> tags and content in between
        while (base.contains(OPEN_STYLE_TAG)) {
            editedContent = base.substringBefore(OPEN_STYLE_TAG) + base.substringAfter(CLOSE_STYLE_TAG)
            base = editedContent
        }
        return editedContent
    }

    companion object {
        private const val OPEN_STYLE_TAG = "<style>"
        private const val CLOSE_STYLE_TAG = "</style>"
        private const val LINK_COLOR = "#444E64"
        private const val ID_SUBTITLE = "id=\"subtitle\""
    }
}