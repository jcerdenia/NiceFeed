package com.joshuacerdenia.android.nicefeed.util

import android.util.Base64
import com.joshuacerdenia.android.nicefeed.LINK_COLOR
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.data.model.entry.EntryMinimal

object EntryToHtmlUtil {
    
    private var fontSize = "medium"
    private var fontFamily = "sans-serif"
    private var shouldIncludeHeader = false

    private var title = ""
    private var subtitle = ""

    const val DARK_MODE_JAVASCRIPT = """javascript:(function() {
            const node = document.createElement('style');
            node.type = 'text/css';
            const links = document.links;
            for (let i = 0; i < links.length; i++) { links[i].style.color = '#444E64'; }
            node.innerHTML = 'body { color: white; background-color: transparent; }';
            document.head.appendChild(node);
        })()"""

    fun setFontSize(textSizeKey: Int): EntryToHtmlUtil {
        fontSize = when (textSizeKey) {
            NiceFeedPreferences.TEXT_SIZE_LARGE -> "large"
            NiceFeedPreferences.TEXT_SIZE_LARGER -> "x-large"
            else -> "medium"
        }
        
        return this
    }

    fun setFontFamily(fontKey: Int): EntryToHtmlUtil {
        fontFamily = if (fontKey == NiceFeedPreferences.FONT_SERIF) "serif" else "sans-serif"
        return this
    }

    fun setShouldIncludeHeader(shouldIncludeHeader: Boolean): EntryToHtmlUtil {
        this.shouldIncludeHeader = shouldIncludeHeader
        return this
    }

    fun format(entry: EntryMinimal): String {
        if (shouldIncludeHeader) {
            title = "<h2>${entry.title}</h2>"
            subtitle = when {
                entry.author.isNullOrEmpty() -> "<p id=\"subtitle\">${entry.formattedDate}</p>"
                entry.formattedDate.isNullOrEmpty() -> "<p id=\"subtitle\">${entry.author}</p>"
                else -> "<p id=\"subtitle\">${entry.formattedDate} – ${entry.author}</p>"
            }
        }
        
        return StringBuilder(getStyle())
            .append("<body>")
            .append(title)
            .append(subtitle)
            .append(entry.content.styleRemoved())
            .append("</body>")
            .toString()
            .toByteArray()
            .run { Base64.encodeToString(this, Base64.NO_PADDING) }
    }

    private fun getStyle(): String {
        return """<style>
            * { max-width:100% }
            body {font-size:$fontSize; font-family:$fontFamily; word-wrap:break-word; line-height:1.4}
            h1, h2, h3, h4, h5, h6 {line-height:normal}
            #subtitle {color:gray}
            a:link, a:visited, a:hover, a:active {color:$LINK_COLOR; text-decoration:none; font-weight:bold}
            pre, code {white-space:pre-wrap; word-break:keep-all}
            img, figure {display:block; margin-left:auto; margin-right:auto; height:auto; max-width:100%}
            iframe {width:100%}
            </style>"""
    }
    
    // Remove all <style></style> tags and content in between.
    private fun String.styleRemoved(): String {
        var string = this
        while (string.contains("<style>")) {
            string = string.substringBefore("<style>") + string.substringAfter("</style>")
        }

        return string
    }
}