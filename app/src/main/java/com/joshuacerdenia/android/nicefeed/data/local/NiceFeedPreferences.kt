package com.joshuacerdenia.android.nicefeed.data.local

import android.content.Context
import android.content.SharedPreferences
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFeedManagerFragment.Companion.SORT_BY_ADDED

object NiceFeedPreferences {

    private const val NICE_FEED_PREFS = "NICE_FEED_PREFS"
    private const val KEY_IS_EMPTY = "KEY_IS_EMPTY"
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
    private const val KEY_VIEW_AS_WEB_PAGE = "KEY_VIEW_AS_WEB_PAGE"
    private const val KEY_SYNC_IN_BG = "KEY_SYNC_IN_BG"

    const val TEXT_SIZE_NORMAL = 0
    const val TEXT_SIZE_LARGE = 1
    const val TEXT_SIZE_LARGER = 2
    private const val FONT_SANS = 0
    const val FONT_SERIF = 1
    private const val THEME_DEFAULT = 0
    const val THEME_LIGHT = 1
    const val THEME_DARK = 2
    private const val FEED_ORDER_TITLE = 0
    const val FEED_ORDER_UNREAD = 1
    private const val ENTRY_ORDER_TITLE = 0
    const val ENTRY_ORDER_UNREAD = 1

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(NICE_FEED_PREFS, Context.MODE_PRIVATE)
    }

    fun isEmpty(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_IS_EMPTY, true)
    }

    fun isEmpty(context: Context, isEmpty: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_IS_EMPTY, isEmpty).apply()
    }

    fun getLastViewedFeedId(context: Context): String? {
        return getPrefs(context).getString(KEY_FEED_ID, null)
    }

    fun saveLastViewedFeedId(context: Context, feedId: String?) {
        getPrefs(context).edit().putString(KEY_FEED_ID, feedId).apply()
    }

    fun getFeedManagerOrder(context: Context): Int {
        return getPrefs(context).getInt(KEY_FEED_MANAGER_ORDER, SORT_BY_ADDED)
    }

    fun saveFeedManagerOrder(context: Context, sorter: Int) {
        getPrefs(context).edit().putInt(KEY_FEED_MANAGER_ORDER, sorter).apply()
    }

    fun getFeedsOrder(context: Context): Int {
        return getPrefs(context).getInt(KEY_SORT_FEEDS, FEED_ORDER_TITLE)
    }
    
    fun saveFeedsOrder(context: Context, order: Int) {
        getPrefs(context).edit().putInt(KEY_SORT_FEEDS, order).apply()
    }

    fun getEntriesOrder(context: Context): Int {
        return getPrefs(context).getInt(KEY_SORT_ENTRIES, ENTRY_ORDER_TITLE)
    }

    fun saveEntriesOrder(context: Context, order: Int) {
        getPrefs(context).edit().putInt(KEY_SORT_ENTRIES, order).apply()
    }

    fun getAutoUpdateSetting(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_AUTO_UPDATE, true)
    }

    fun saveAutoUpdateSetting(context: Context, isOn: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_AUTO_UPDATE, isOn).apply()
    }

    fun getLastPolledIndex(context: Context): Int {
        return getPrefs(context).getInt(KEY_LAST_POLLED_INDEX, 0)
    }

    fun saveLastPolledIndex(context: Context, index: Int) {
        getPrefs(context).edit().putInt(KEY_LAST_POLLED_INDEX, index).apply()
    }

    fun getPollingSetting(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_POLLING, true)
    }

    fun savePollingSetting(context: Context, isPolling: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_POLLING, isPolling).apply()
    }

    fun getTextSize(context: Context): Int {
        return getPrefs(context).getInt(KEY_TEXT_SIZE, TEXT_SIZE_NORMAL)
    }

    fun saveTextSize(context: Context, textSize: Int) {
        getPrefs(context).edit().putInt(KEY_TEXT_SIZE, textSize).apply()
    }

    fun getFont(context: Context): Int {
        return getPrefs(context).getInt(KEY_FONT, FONT_SANS)
    }

    fun saveFont(context: Context, font: Int) {
        getPrefs(context).edit().putInt(KEY_FONT, font).apply()
    }

    fun getMinimizedCategories(context: Context): Set<String>? {
        return getPrefs(context).getStringSet(KEY_MIN_CATEGORIES, emptySet())
    }

    fun saveMinimizedCategories(context: Context, categories: Set<String>) {
        getPrefs(context).edit().putStringSet(KEY_MIN_CATEGORIES, categories).apply()
    }

    fun getTheme(context: Context): Int {
        return getPrefs(context).getInt(KEY_THEME, THEME_DEFAULT)
    }

    fun saveTheme(context: Context, theme: Int) {
        getPrefs(context).edit().putInt(KEY_THEME, theme).apply()
    }

    fun getBrowserSetting(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_VIEW_IN_BROWSER, false)
    }

    fun setBrowserSetting(context: Context, shouldViewInBrowser: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_VIEW_IN_BROWSER, shouldViewInBrowser).apply()
    }

    fun bannerIsEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_BANNER, true)
    }

    fun setBannerIsEnabled(context: Context, isEnabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_BANNER, isEnabled).apply()
    }

    fun syncInBackground(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_SYNC_IN_BG, false)
    }

    fun setSyncInBackground(context: Context, isOn: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_SYNC_IN_BG, isOn).apply()
    }

    // Not yet used. Eventually a setting to view entry as web page in WebView.
    fun loadAsWebPage(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_VIEW_AS_WEB_PAGE, false)
    }

    // This one too.
    fun setLoadAsWebPage(context: Context, viewAsWebPage: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_VIEW_AS_WEB_PAGE, viewAsWebPage).apply()
    }
}