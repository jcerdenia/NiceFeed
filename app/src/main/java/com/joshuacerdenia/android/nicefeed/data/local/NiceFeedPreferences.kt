package com.joshuacerdenia.android.nicefeed.data.local

import android.content.Context
import android.content.SharedPreferences
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFeedManagerFragment.Companion.SORT_BY_ADDED

object NiceFeedPreferences {

    private const val SHARED_PREFS = "SHARED_PREFS"
    private const val KEY_FEED_ID = "KEY_WEBSITE"
    private const val KEY_FEED_MANAGER_ORDER = "KEY_FEED_MANAGER_ORDER"
    private const val KEY_SORT_FEEDS = "KEY_SORT_FEEDS"
    private const val KEY_SORT_ENTRIES = "KEY_SORT_ENTRIES"
    private const val KEY_AUTO_UPDATE = "KEY_AUTO_UPDATE"
    private const val KEY_LAST_POLLED_INDEX = "KEY_LAST_POLLED_INDEX"
    private const val KEY_POLLING = "KEY_POLLING"
    private const val KEY_TEXT_SIZE = "KEY_TEXT_SIZE"
    private const val KEY_MIN_CATEGORIES = "KEY_MIN_CATEGORIES"

    const val TEXT_SIZE_NORMAL = 0
    const val TEXT_SIZE_LARGE = 1
    const val TEXT_SIZE_LARGER = 2

    private fun getDefaultSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    }

    fun getLastViewedFeedId(context: Context): String? {
        return getDefaultSharedPrefs(context).getString(KEY_FEED_ID, null)
    }

    fun saveLastViewedFeedId(context: Context, feedId: String?) {
        getDefaultSharedPrefs(context).edit().putString(KEY_FEED_ID, feedId).apply()
    }

    fun getFeedManagerOrder(context: Context): Int {
        return getDefaultSharedPrefs(context).getInt(KEY_FEED_MANAGER_ORDER, SORT_BY_ADDED)
    }

    fun saveFeedManagerOrder(context: Context, sorter: Int) {
        getDefaultSharedPrefs(context).edit().putInt(KEY_FEED_MANAGER_ORDER, sorter).apply()
    }

    fun getFeedsOrder(context: Context): Int {
        return getDefaultSharedPrefs(context).getInt(KEY_SORT_FEEDS, 0)
    }
    
    fun saveFeedsOrder(context: Context, order: Int) {
        getDefaultSharedPrefs(context).edit().putInt(KEY_SORT_FEEDS, order).apply()
    }

    fun getEntriesOrder(context: Context): Int {
        return getDefaultSharedPrefs(context).getInt(KEY_SORT_ENTRIES, 0)
    }

    fun saveEntriesOrder(context: Context, order: Int) {
        getDefaultSharedPrefs(context).edit().putInt(KEY_SORT_ENTRIES, order).apply()
    }

    fun getAutoUpdateSetting(context: Context): Boolean {
        return getDefaultSharedPrefs(context).getBoolean(KEY_AUTO_UPDATE, true)
    }

    fun saveAutoUpdateSetting(context: Context, isOn: Boolean) {
        getDefaultSharedPrefs(context).edit().putBoolean(KEY_AUTO_UPDATE, isOn).apply()
    }

    fun getLastPolledIndex(context: Context): Int {
        return getDefaultSharedPrefs(context).getInt(KEY_LAST_POLLED_INDEX, 0)
    }

    fun saveLastPolledIndex(context: Context, index: Int) {
        getDefaultSharedPrefs(context).edit().putInt(KEY_LAST_POLLED_INDEX, index).apply()
    }

    fun getPollingSetting(context: Context): Boolean {
        return getDefaultSharedPrefs(context).getBoolean(KEY_POLLING, true)
    }

    fun savePollingSetting(context: Context, isPolling: Boolean) {
        getDefaultSharedPrefs(context).edit().putBoolean(KEY_POLLING, isPolling).apply()
    }

    fun getTextSize(context: Context): Int {
        return getDefaultSharedPrefs(context).getInt(KEY_TEXT_SIZE, TEXT_SIZE_NORMAL)
    }

    fun saveTextSize(context: Context, textSize: Int) {
        getDefaultSharedPrefs(context).edit().putInt(KEY_TEXT_SIZE, textSize).apply()
    }

    fun getMinimizedCategories(context: Context): Set<String>? {
        return getDefaultSharedPrefs(context).getStringSet(KEY_MIN_CATEGORIES, emptySet())
    }

    fun saveMinimizedCategories(context: Context, categories: Set<String>) {
        getDefaultSharedPrefs(context).edit().putStringSet(KEY_MIN_CATEGORIES, categories).apply()
    }
}