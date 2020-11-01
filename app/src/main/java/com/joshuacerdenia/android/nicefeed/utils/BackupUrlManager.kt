package com.joshuacerdenia.android.nicefeed.utils

import android.util.Log

private const val TAG = "FeedParser"

/*  This object generates variations of a base URL to be used for requesting a feed,
    in case the original URL doesn't work the first time.
*/
object BackupUrlManager {

    private const val COUNTER_MAX = 4
    private var attemptCount = 0
        get() = if (field > COUNTER_MAX) 0 else field

    private var url: String? = null // Base URL
    private var urlPlusFeed: String? = null
    private var urlPlusRss: String? = null
    private var urlPlusRssXml: String? = null

    fun setBase(url: String?) {
        attemptCount = 0
        this.url = url
        if (url != null) {
            urlPlusFeed = "$url/feed"
            urlPlusRss = "$url/rss"
            urlPlusRssXml = "$url/rss.xml"
            // Expandable to other possible suffixes, just increase max count
        } else resetValues()
    }

    fun getNextUrl(): String? {
        Log.d(TAG, "Backup URL tried, count: $attemptCount")
        attemptCount += 1
        return when (attemptCount - 1) {
            0 -> url
            1 -> urlPlusFeed
            2 -> urlPlusRss
            3 -> urlPlusRssXml
            else -> {
                setBase(null)
                null
            }
        }
    }

    fun reset() {
        setBase(null)
    }

    private fun resetValues() {
        urlPlusFeed = null
        urlPlusRss = null
        urlPlusRssXml = null
    }
}