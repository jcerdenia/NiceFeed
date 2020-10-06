package com.joshuacerdenia.android.nicefeed.utils

import java.util.*

object RssUrlTransformer {

    private const val BASE_NY_TIMES = "www.nytimes.com"
    private const val ATOM_XML = "atom.xml"

    fun getUrl(link: String): String {
        val url = link.removePrefix("feed/")
            .removeSuffix("/")
            // This is possibly not ideal, but it results in too many failed requests otherwise:
            .replace("http://", "https://")
            .toLowerCase(Locale.ROOT)

        return when {
            url.contains(BASE_NY_TIMES) -> url.replace(BASE_NY_TIMES, "rss.nytimes.com")
            url.contains("feedproxy.google.com") -> "" // We don't want this
            url.contains("youtube.com") -> url
            url.contains("medium.com") -> url
            url.endsWith("blogspot.com") -> "$url/feeds/posts/default?alt=rss"
            url.endsWith("/feeds/posts/default") -> "$url?alt=rss"
            url.endsWith("wordpress.com") -> "$url/feed"
            url.endsWith("tumblr.com") -> "$url/rss"
            url.contains(ATOM_XML) -> url.replace(ATOM_XML, "rss.xml")
            url.endsWith(".xml") -> url
            url.endsWith("/feed") -> url
            url.endsWith("/rss") -> url
            else -> url
        }
    }
}