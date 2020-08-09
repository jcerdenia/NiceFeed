package com.joshuacerdenia.android.nicefeed.data.local

import android.content.Context
import android.content.SharedPreferences
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFeedManagerFragment.Companion.SORT_BY_ADDED
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFilterEntriesFragment.Companion.FILTER_ALL
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFilterEntriesFragment.Companion.SORT_BY_PUBLISHED

object UserPreferences {

    private const val SHARED_PREFS = "SHARED_PREFS"
    private const val KEY_FEED_ID = "KEY_WEBSITE"
    private const val KEY_MANAGER_SORT = "KEY_MANAGER_SORT"
    private const val KEY_FILTER_ENTRIES = "KEY_FILTER_ENTRIES"
    private const val KEY_SORT_ENTRIES = "KEY_SORT_ENTRIES"
    private const val KEY_AUTO_UPDATE = "KEY_AUTO_UPDATE"

    fun getSavedFeedId(context: Context): String? {
        return getDefaultSharedPrefs(context).getString(KEY_FEED_ID, null)
    }

    fun saveFeedId(context: Context, feedId: String) {
        getDefaultSharedPrefs(context).edit().putString(KEY_FEED_ID, feedId).apply()
    }

    fun getFeedManagerSortPref(context: Context): Int {
        return getDefaultSharedPrefs(context).getInt(KEY_MANAGER_SORT, SORT_BY_ADDED)
    }

    fun saveFeedManagerSortPref(context: Context, sorter: Int) {
        getDefaultSharedPrefs(context).edit().putInt(KEY_MANAGER_SORT, sorter).apply()
    }

    fun getEntriesFilterPref(context: Context): Int {
        return getDefaultSharedPrefs(context).getInt(KEY_FILTER_ENTRIES, FILTER_ALL)
    }

    fun saveEntriesFilterPref(context: Context, filter: Int) {
        getDefaultSharedPrefs(context).edit().putInt(KEY_FILTER_ENTRIES, filter).apply()
    }

    fun getEntriesSortPref(context: Context): Int {
        return getDefaultSharedPrefs(context).getInt(KEY_SORT_ENTRIES, SORT_BY_PUBLISHED)
    }

    fun saveEntriesSortPref(context: Context, sorter: Int) {
        getDefaultSharedPrefs(context).edit().putInt(KEY_SORT_ENTRIES, sorter).apply()
    }

    fun getAutoUpdatePref(context: Context): Boolean {
        return getDefaultSharedPrefs(context).getBoolean(KEY_AUTO_UPDATE, true)
    }

    fun saveAutoUpdatePref(context: Context, isOn: Boolean) {
        getDefaultSharedPrefs(context).edit().putBoolean(KEY_AUTO_UPDATE, isOn).apply()
    }

    private fun getDefaultSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    }
}