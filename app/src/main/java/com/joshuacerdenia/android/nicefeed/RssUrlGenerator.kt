package com.joshuacerdenia.android.nicefeed

class RssUrlGenerator {

    fun getUrl(link: String): String {

        val url = link.removePrefix("feed/")
            .removeSuffix("/")
            .replace("http://", "https://")

        return when {
            url.contains("www.nytimes.com") ->
                url.replace("www.nytimes.com", "rss.nytimes.com")
            url.contains("feedproxy.google.com") -> "" // Use backup URL instead
            url.contains("youtube.com") -> url
            url.contains("medium.com") -> url
            url.endsWith("blogspot.com") -> "$url/feeds/posts/default?alt=rss"
            url.endsWith("/feeds/posts/default") -> "$url?alt=rss"
            url.endsWith("wordpress.com") -> "$url/feed"
            url.endsWith("tumblr.com") -> "$url/rss"
            url.contains("atom.xml") -> url.replace("atom.xml", "rss.xml")
            url.endsWith(".xml") -> url
            url.endsWith("/feed") -> url
            url.endsWith("/rss") -> url
            else -> url
        }
    }
}