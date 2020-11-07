package com.joshuacerdenia.android.nicefeed.ui

interface FeedRequestCallbacks {

    fun onRequestSubmitted(url: String, backup: String? = null)

    fun onRequestDismissed()
}