package com.joshuacerdenia.android.nicefeed.data.local

import android.content.Context
import android.content.SharedPreferences

object FeedPreferences {

    private lateinit var prefs: SharedPreferences
    private const val NAME = "FeedPreferences"

    private const val LAST_VIEWED_FEED_ID = "last_viewed_feed_id"
    private const val FEED_MANAGER_ORDER = "feed_manager_order"
    private const val FEED_LIST_ORDER = "feed_list_order"
    private const val ENTRY_LIST_ORDER = "entry_list_order"
    private const val MINIMIZED_CATEGORIES = "minimized_categories"
    private const val SHOULD_VIEW_IN_BROWSER = "should_view_in_browser"
    private const val THEME = "theme"
    private const val FONT = "font"
    private const val TEXT_SIZE = "text_size"
    private const val IS_BANNER_ENABLED = "is_banner_enabled"
    private const val SHOULD_AUTO_UPDATE = "should_auto_update"
    private const val SHOULD_POLL = "should_poll"
    private const val LAST_POLLED_INDEX = "last_polled_index"
    private const val SHOULD_SYNC_IN_BACKGROUND = "should_sync_in_background"
    private const val SHOULD_KEEP_OLD_UNREAD_ENTRIES = "should_keep_old_unread_entries"

    // Call on app start.
    fun init(context: Context) {
        prefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        FeedPreferencesMigration(context).migrate()
    }

    private inline fun SharedPreferences.edit(
        performEdit: (SharedPreferences.Editor) -> Unit
    ) {
        val editor = this.edit()
        performEdit(editor)
        editor.apply()
    }

    var lastViewedFeedId: String?
        get() = prefs.getString(LAST_VIEWED_FEED_ID, null)
        set(value) = prefs.edit { it.putString(LAST_VIEWED_FEED_ID, value) }

    var feedManagerOrder: Int
        get() = prefs.getInt(FEED_MANAGER_ORDER, 0)
        set(value) = prefs.edit { it.putInt(FEED_MANAGER_ORDER, value) }

    var feedListOrder: Int
        get() = prefs.getInt(FEED_LIST_ORDER, 0)
        set(value) = prefs.edit { it.putInt(FEED_LIST_ORDER, value) }

    var entryListOrder: Int
        get() = prefs.getInt(ENTRY_LIST_ORDER, 0)
        set(value) = prefs.edit { it.putInt(ENTRY_LIST_ORDER, value) }

    var minimizedCategories: Set<String>
        get() = prefs.getStringSet(MINIMIZED_CATEGORIES, emptySet()) as Set<String>
        set(value) = prefs.edit { it.putStringSet(MINIMIZED_CATEGORIES, value) }

    var shouldViewInBrowser: Boolean
        get() = prefs.getBoolean(SHOULD_VIEW_IN_BROWSER, false)
        set(value) = prefs.edit { it.putBoolean(SHOULD_VIEW_IN_BROWSER, value) }

    var theme: Int
        get() = prefs.getInt(THEME, 0)
        set(value) = prefs.edit { it.putInt(THEME, value) }

    var font: Int
        get() = prefs.getInt(FONT, 0)
        set(value) = prefs.edit { it.putInt(FONT, value) }

    var textSize: Int
        get() = prefs.getInt(TEXT_SIZE, 0)
        set(value) = prefs.edit { it.putInt(TEXT_SIZE, value) }

    var isBannerEnabled: Boolean
        get() = prefs.getBoolean(IS_BANNER_ENABLED, true)
        set(value) = prefs.edit { it.putBoolean(IS_BANNER_ENABLED, value) }

    var shouldAutoUpdate: Boolean
        get() = prefs.getBoolean(SHOULD_AUTO_UPDATE, true)
        set(value) = prefs.edit { it.putBoolean(SHOULD_AUTO_UPDATE, value) }

    var shouldPoll: Boolean
        get() = prefs.getBoolean(SHOULD_POLL, true)
        set(value) = prefs.edit { it.putBoolean(SHOULD_POLL, value) }

    var lastPolledIndex: Int
        get() = prefs.getInt(LAST_POLLED_INDEX, 0)
        set(value) = prefs.edit { it.putInt(LAST_POLLED_INDEX, value) }

    var shouldSyncInBackground: Boolean
        get() = prefs.getBoolean(SHOULD_SYNC_IN_BACKGROUND, false)
        set(value) = prefs.edit { it.putBoolean(SHOULD_SYNC_IN_BACKGROUND, value) }

    var shouldKeepOldUnreadEntries: Boolean
        get() = prefs.getBoolean(SHOULD_KEEP_OLD_UNREAD_ENTRIES, false)
        set(value) = prefs.edit { it.putBoolean(SHOULD_KEEP_OLD_UNREAD_ENTRIES, value) }
}