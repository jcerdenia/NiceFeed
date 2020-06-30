package com.joshuacerdenia.android.nicefeed

import android.util.Log

object BackupUrl {

    private const val COUNTER_MAX = 3
    private var attemptCount = 0

    private var url: String? = null

    private var urlPlusFeed: String? = null
    private var urlPlusRss: String? = null

    fun getUrl(): String? {
        Log.d("MainViewModel", "Backup URL tried, count: $attemptCount")

        return when (attemptCount) {
            0 -> url
            1 -> urlPlusFeed
            2 -> urlPlusRss
            else -> {
                attemptCount = 0
                setUrl(null)
                null
            }
        }
    }

    fun setUrl(url: String?) {
        this.url = url

        if (url != null) {
            urlPlusFeed = "$url/feed"
            urlPlusRss = "$url/rss"
            // Expandable to other possible suffixes, just increase max count

        } else {
            urlPlusFeed = null
            urlPlusRss = null
            attemptCount = 0
        }
    }

    fun countUp() {
        attemptCount += 1
        if (attemptCount == COUNTER_MAX) {
            attemptCount = 0
            setUrl(null)
        }
    }
}