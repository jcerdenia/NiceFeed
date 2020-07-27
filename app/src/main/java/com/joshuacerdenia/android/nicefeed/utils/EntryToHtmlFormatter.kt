package com.joshuacerdenia.android.nicefeed.utils

import android.util.Base64
import android.util.Base64.encodeToString
import com.joshuacerdenia.android.nicefeed.data.model.Entry

private const val CSS = "<style>" +
        //"* {margin: 0; padding: 0}" +
        "body {font-family:serif; word-wrap:break-word}" +
        //"#supertitle {color:gray; word-wrap:break-word}" +
        //"div {padding:0}" +
        "#title, #author, #supertitle {font-family:Roboto, sans-serif}" +
        "img, figure {display:block; margin-left:auto; margin-right:auto; height:auto; max-width:100%}" +
        "iframe {width:100%}" +
        "</style>"

class EntryToHtmlFormatter(entry: Entry) {

    /*
    private val date = entry.date?.let {
        DateFormat.getDateTimeInstance(MEDIUM, SHORT).format(it)
    } ?: "No Date" */

    //private val supertitle = "<span id=\"supertitle\">$date – ${entry.website.simplified()}</span>"
    private val title = "<h2 id=\"title\">${entry.title}</h2>"
    private val author = "<p id=\"author\">${entry.author}</p>"
    private val content = entry.content ?: entry.description ?: "No content"

    fun getHtml(): String {
        val html = StringBuilder(CSS)
            .append("<body>")
            //.append(supertitle)
            .append(title)
            .append(author)
            .append(content)
            .append("</body")
            .toString()

        //Log.d("EntryToHtmlTransformer", "Printing: $html")
        return encodeToString(html.toByteArray(), Base64.NO_PADDING)
    }
}