package com.joshuacerdenia.android.nicefeed.utils

import android.util.Base64
import android.util.Base64.encodeToString
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences.TEXT_SIZE_LARGE
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences.TEXT_SIZE_LARGER
import com.joshuacerdenia.android.nicefeed.data.model.EntryMinimal
import java.text.DateFormat

class EntryToHtmlFormatter(textSizeKey: Int) {

    private val linkColor = "#444E64" // Change dynamically?
    private val fontSize = when (textSizeKey) {
        TEXT_SIZE_LARGE -> "large"
        TEXT_SIZE_LARGER -> "x-large"
        else -> "medium"
    }
    private val style = OPEN_STYLE_TAG + "* {max-width:100%}" +
            "body {font-size:$fontSize; font-family:$FONT_SANS_SERIF; word-wrap:break-word}" +
            "#subtitle {color:gray}" +
            "a:link, a:visited, a:hover, a:active {color:$linkColor; text-decoration:none; font-weight:bold}" +
            "img, figure {display:block; margin-left:auto; margin-right:auto; height:auto; max-width:100%}" +
            "iframe {width:100%}" + CLOSE_STYLE_TAG

    fun getHtml(entry: EntryMinimal): String {
        val title = "<h2>${entry.title}</h2>"
        val formattedDate = entry.date?.let { date ->
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(date)
        } ?: ""
        val subtitle = if (!entry.author.isNullOrEmpty()) {
            "<p id=\"subtitle\">$formattedDate – ${entry.author}</p>"
        } else {
            "<p id=\"subtitle\">$formattedDate</p>"
        }
        val content = entry.content.run (::removeCss)
        val html = StringBuilder(style)
            .append(OPEN_BODY_TAG)
            .append(title)
            .append(subtitle)
            .append(content)
            .append(CLOSE_BODY_TAG)
            .toString()

        return encodeToString(html.toByteArray(), Base64.NO_PADDING)
    }

    private fun removeCss(content: String): String {
        return if (content.contains(OPEN_CODE_TAG) && content.contains(CLOSE_CODE_TAG)) {
            //  Admittedly, this is a gamble that an entry with <code> and </code> tags will
            //  not be image-heavy, let alone have special style tags, so we can safely ignore it
            content
        } else {
            var baseContent = content
            var editedContent = content

            while (baseContent.contains(OPEN_STYLE_TAG)) {
                editedContent = baseContent.substringBefore(OPEN_STYLE_TAG) +
                        baseContent.substringAfter(CLOSE_STYLE_TAG)
                baseContent = editedContent
            }
            editedContent
        }
    }

    companion object {
        private const val OPEN_STYLE_TAG = "<style>"
        private const val CLOSE_STYLE_TAG = "</style>"
        private const val OPEN_BODY_TAG = "<body>"
        private const val CLOSE_BODY_TAG = "</body>"
        private const val OPEN_CODE_TAG = "<code>"
        private const val CLOSE_CODE_TAG = "</code>"
        private const val FONT_SANS_SERIF = "Roboto, sans-serif"
    }
}