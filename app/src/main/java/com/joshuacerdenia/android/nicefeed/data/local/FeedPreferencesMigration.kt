package com.joshuacerdenia.android.nicefeed.data.local

import android.content.Context

// Class for managing migration from NiceFeedPreferences to FeedPreferences.
class FeedPreferencesMigration(context: Context) {

    private val oldPrefs = context.getSharedPreferences(OLD_NAME, Context.MODE_PRIVATE)

    fun migrate() {
        // Get all values using old keys.
        val lastViewedFeedId = oldPrefs.getString(KEY_FEED_ID, null)
        val feedManagerOrder = oldPrefs.getInt(KEY_FEED_MANAGER_ORDER, 0)
        val feedListOrder = oldPrefs.getInt(KEY_SORT_FEEDS, 0)
        val entryListOrder = oldPrefs.getInt(KEY_SORT_ENTRIES, 0)
        val shouldAutoUpdate = oldPrefs.getBoolean(KEY_AUTO_UPDATE, true)
        val lastPolledIndex = oldPrefs.getInt(KEY_LAST_POLLED_INDEX, 0)
        val shouldPoll = oldPrefs.getBoolean(KEY_POLLING, true)
        val textSize = oldPrefs.getInt(KEY_TEXT_SIZE, 0)
        val font = oldPrefs.getInt(KEY_FONT, 0)
        val minCategories = oldPrefs.getStringSet(KEY_MIN_CATEGORIES, emptySet())
        val theme = oldPrefs.getInt(KEY_THEME, 0)
        val shouldViewInBrowser = oldPrefs.getBoolean(KEY_VIEW_IN_BROWSER, false)
        val isBannerEnabled = oldPrefs.getBoolean(KEY_BANNER, true)
        val shouldSyncInBackground = oldPrefs.getBoolean(KEY_SYNC_IN_BG, false)
        val shouldKeepOldUnreadEntries = oldPrefs.getBoolean(KEY_KEEP_ENTRIES, true)

        // If values are not default values, update FeedPreferences.
        if (lastViewedFeedId != null) FeedPreferences.lastViewedFeedId = lastViewedFeedId
        if (feedManagerOrder != 0) FeedPreferences.feedManagerOrder = feedManagerOrder
        if (feedListOrder != 0) FeedPreferences.feedListOrder = feedListOrder
        if (entryListOrder != 0) FeedPreferences.entryListOrder = entryListOrder
        if (!shouldAutoUpdate) FeedPreferences.shouldAutoUpdate = false
        if (lastPolledIndex != 0) FeedPreferences.lastPolledIndex = lastPolledIndex
        if (!shouldPoll) FeedPreferences.shouldPoll = false
        if (textSize != 0) FeedPreferences.textSize = textSize
        if (font != 0) FeedPreferences.font = font
        if (!minCategories.isNullOrEmpty()) FeedPreferences.minimizedCategories = minCategories
        if (theme != 0) FeedPreferences.theme = theme
        if (shouldViewInBrowser) FeedPreferences.shouldViewInBrowser = true
        if (!isBannerEnabled) FeedPreferences.isBannerEnabled = false
        if (shouldSyncInBackground) FeedPreferences.shouldSyncInBackground = true
        if (!shouldKeepOldUnreadEntries) FeedPreferences.shouldKeepOldUnreadEntries = false

        // Clear data from old shared preferences.
        oldPrefs.edit().clear().apply()
    }

    companion object {

        // Old keys:
        private const val OLD_NAME = "NICE_FEED_PREFS"
        private const val KEY_FEED_ID = "KEY_FEED_ID"
        private const val KEY_FEED_MANAGER_ORDER = "KEY_FEED_MANAGER_ORDER"
        private const val KEY_SORT_FEEDS = "KEY_SORT_FEEDS"
        private const val KEY_SORT_ENTRIES = "KEY_SORT_ENTRIES"
        private const val KEY_AUTO_UPDATE = "KEY_AUTO_UPDATE"
        private const val KEY_LAST_POLLED_INDEX = "KEY_LAST_POLLED_INDEX"
        private const val KEY_POLLING = "KEY_POLLING"
        private const val KEY_TEXT_SIZE = "KEY_TEXT_SIZE"
        private const val KEY_FONT = "KEY_FONT"
        private const val KEY_MIN_CATEGORIES = "KEY_MIN_CATEGORIES"
        private const val KEY_THEME = "KEY_THEME"
        private const val KEY_VIEW_IN_BROWSER = "KEY_VIEW_IN_BROWSER"
        private const val KEY_BANNER = "KEY_BANNER"
        private const val KEY_SYNC_IN_BG = "KEY_SYNC_IN_BG"
        private const val KEY_KEEP_ENTRIES = "KEY_KEEP_ENTRIES"
    }
}