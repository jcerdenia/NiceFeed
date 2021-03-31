package com.joshuacerdenia.android.nicefeed.data.local;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u001c\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$J\u000e\u0010%\u001a\u00020\"2\u0006\u0010#\u001a\u00020$J\u000e\u0010&\u001a\u00020\"2\u0006\u0010#\u001a\u00020$J\u000e\u0010\'\u001a\u00020\u00042\u0006\u0010#\u001a\u00020$J\u000e\u0010(\u001a\u00020\u00042\u0006\u0010#\u001a\u00020$J\u000e\u0010)\u001a\u00020\u00042\u0006\u0010#\u001a\u00020$J\u000e\u0010*\u001a\u00020\u00042\u0006\u0010#\u001a\u00020$J\u000e\u0010+\u001a\u00020\u00042\u0006\u0010#\u001a\u00020$J\u0010\u0010,\u001a\u0004\u0018\u00010\u000b2\u0006\u0010#\u001a\u00020$J\u0016\u0010-\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010.2\u0006\u0010#\u001a\u00020$J\u000e\u0010/\u001a\u00020\"2\u0006\u0010#\u001a\u00020$J\u0010\u00100\u001a\u0002012\u0006\u0010#\u001a\u00020$H\u0002J\u000e\u00102\u001a\u00020\u00042\u0006\u0010#\u001a\u00020$J\u000e\u00103\u001a\u00020\u00042\u0006\u0010#\u001a\u00020$J\u000e\u00104\u001a\u00020\"2\u0006\u0010#\u001a\u00020$J\u0016\u00105\u001a\u0002062\u0006\u0010#\u001a\u00020$2\u0006\u00107\u001a\u00020\"J\u0016\u00108\u001a\u0002062\u0006\u0010#\u001a\u00020$2\u0006\u00109\u001a\u00020\u0004J\u0016\u0010:\u001a\u0002062\u0006\u0010#\u001a\u00020$2\u0006\u0010;\u001a\u00020\u0004J\u0016\u0010<\u001a\u0002062\u0006\u0010#\u001a\u00020$2\u0006\u00109\u001a\u00020\u0004J\u0016\u0010=\u001a\u0002062\u0006\u0010#\u001a\u00020$2\u0006\u0010>\u001a\u00020\u0004J\u0016\u0010?\u001a\u0002062\u0006\u0010#\u001a\u00020$2\u0006\u0010@\u001a\u00020\u0004J\u0018\u0010A\u001a\u0002062\u0006\u0010#\u001a\u00020$2\b\u0010B\u001a\u0004\u0018\u00010\u000bJ\u001c\u0010C\u001a\u0002062\u0006\u0010#\u001a\u00020$2\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u000b0.J\u0016\u0010E\u001a\u0002062\u0006\u0010#\u001a\u00020$2\u0006\u0010F\u001a\u00020\"J\u0016\u0010G\u001a\u0002062\u0006\u0010#\u001a\u00020$2\u0006\u0010H\u001a\u00020\u0004J\u0016\u0010I\u001a\u0002062\u0006\u0010#\u001a\u00020$2\u0006\u0010J\u001a\u00020\u0004J\u0016\u0010K\u001a\u0002062\u0006\u0010#\u001a\u00020$2\u0006\u0010L\u001a\u00020\"J\u0016\u0010M\u001a\u0002062\u0006\u0010#\u001a\u00020$2\u0006\u0010N\u001a\u00020\"J\u0016\u0010O\u001a\u0002062\u0006\u0010#\u001a\u00020$2\u0006\u00107\u001a\u00020\"J\u0016\u0010P\u001a\u0002062\u0006\u0010#\u001a\u00020$2\u0006\u00107\u001a\u00020\"J\u000e\u0010Q\u001a\u00020\"2\u0006\u0010#\u001a\u00020$R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006R"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/data/local/NiceFeedPreferences;", "", "()V", "ENTRY_ORDER_TITLE", "", "ENTRY_ORDER_UNREAD", "FEED_ORDER_TITLE", "FEED_ORDER_UNREAD", "FONT_SANS", "FONT_SERIF", "KEY_AUTO_UPDATE", "", "KEY_BANNER", "KEY_FEED_ID", "KEY_FEED_MANAGER_ORDER", "KEY_FONT", "KEY_KEEP_ENTRIES", "KEY_LAST_POLLED_INDEX", "KEY_MIN_CATEGORIES", "KEY_POLLING", "KEY_SORT_ENTRIES", "KEY_SORT_FEEDS", "KEY_SYNC_IN_BG", "KEY_TEXT_SIZE", "KEY_THEME", "KEY_VIEW_IN_BROWSER", "NICE_FEED_PREFS", "TEXT_SIZE_LARGE", "TEXT_SIZE_LARGER", "TEXT_SIZE_NORMAL", "THEME_DARK", "THEME_DEFAULT", "THEME_LIGHT", "bannerIsEnabled", "", "context", "Landroid/content/Context;", "getAutoUpdateSetting", "getBrowserSetting", "getEntriesOrder", "getFeedManagerOrder", "getFeedsOrder", "getFont", "getLastPolledIndex", "getLastViewedFeedId", "getMinimizedCategories", "", "getPollingSetting", "getPrefs", "Landroid/content/SharedPreferences;", "getTextSize", "getTheme", "keepOldUnreadEntries", "saveAutoUpdateSetting", "", "isOn", "saveEntriesOrder", "order", "saveFeedManagerOrder", "sorter", "saveFeedsOrder", "saveFont", "font", "saveLastPolledIndex", "index", "saveLastViewedFeedId", "feedId", "saveMinimizedCategories", "categories", "savePollingSetting", "isPolling", "saveTextSize", "textSize", "saveTheme", "theme", "setBannerIsEnabled", "isEnabled", "setBrowserSetting", "shouldViewInBrowser", "setKeepOldUnreadEntries", "setSyncInBackground", "syncInBackground", "app_debug"})
public final class NiceFeedPreferences {
    private static final java.lang.String NICE_FEED_PREFS = "NICE_FEED_PREFS";
    private static final java.lang.String KEY_FEED_ID = "KEY_FEED_ID";
    private static final java.lang.String KEY_FEED_MANAGER_ORDER = "KEY_FEED_MANAGER_ORDER";
    private static final java.lang.String KEY_SORT_FEEDS = "KEY_SORT_FEEDS";
    private static final java.lang.String KEY_SORT_ENTRIES = "KEY_SORT_ENTRIES";
    private static final java.lang.String KEY_AUTO_UPDATE = "KEY_AUTO_UPDATE";
    private static final java.lang.String KEY_LAST_POLLED_INDEX = "KEY_LAST_POLLED_INDEX";
    private static final java.lang.String KEY_POLLING = "KEY_POLLING";
    private static final java.lang.String KEY_TEXT_SIZE = "KEY_TEXT_SIZE";
    private static final java.lang.String KEY_FONT = "KEY_FONT";
    private static final java.lang.String KEY_MIN_CATEGORIES = "KEY_MIN_CATEGORIES";
    private static final java.lang.String KEY_THEME = "KEY_THEME";
    private static final java.lang.String KEY_VIEW_IN_BROWSER = "KEY_VIEW_IN_BROWSER";
    private static final java.lang.String KEY_BANNER = "KEY_BANNER";
    private static final java.lang.String KEY_SYNC_IN_BG = "KEY_SYNC_IN_BG";
    private static final java.lang.String KEY_KEEP_ENTRIES = "KEY_KEEP_ENTRIES";
    public static final int TEXT_SIZE_NORMAL = 0;
    public static final int TEXT_SIZE_LARGE = 1;
    public static final int TEXT_SIZE_LARGER = 2;
    private static final int FONT_SANS = 0;
    public static final int FONT_SERIF = 1;
    private static final int THEME_DEFAULT = 0;
    public static final int THEME_LIGHT = 1;
    public static final int THEME_DARK = 2;
    private static final int FEED_ORDER_TITLE = 0;
    public static final int FEED_ORDER_UNREAD = 1;
    private static final int ENTRY_ORDER_TITLE = 0;
    public static final int ENTRY_ORDER_UNREAD = 1;
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences INSTANCE = null;
    
    private final android.content.SharedPreferences getPrefs(android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLastViewedFeedId(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    public final void saveLastViewedFeedId(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    java.lang.String feedId) {
    }
    
    public final int getFeedManagerOrder(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0;
    }
    
    public final void saveFeedManagerOrder(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int sorter) {
    }
    
    public final int getFeedsOrder(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0;
    }
    
    public final void saveFeedsOrder(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int order) {
    }
    
    public final int getEntriesOrder(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0;
    }
    
    public final void saveEntriesOrder(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int order) {
    }
    
    public final boolean getAutoUpdateSetting(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void saveAutoUpdateSetting(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean isOn) {
    }
    
    public final int getLastPolledIndex(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0;
    }
    
    public final void saveLastPolledIndex(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int index) {
    }
    
    public final boolean getPollingSetting(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void savePollingSetting(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean isPolling) {
    }
    
    public final int getTextSize(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0;
    }
    
    public final void saveTextSize(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int textSize) {
    }
    
    public final int getFont(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0;
    }
    
    public final void saveFont(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int font) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Set<java.lang.String> getMinimizedCategories(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    public final void saveMinimizedCategories(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> categories) {
    }
    
    public final int getTheme(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0;
    }
    
    public final void saveTheme(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int theme) {
    }
    
    public final boolean getBrowserSetting(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void setBrowserSetting(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean shouldViewInBrowser) {
    }
    
    public final boolean bannerIsEnabled(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void setBannerIsEnabled(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean isEnabled) {
    }
    
    public final boolean syncInBackground(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void setSyncInBackground(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean isOn) {
    }
    
    public final boolean keepOldUnreadEntries(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void setKeepOldUnreadEntries(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean isOn) {
    }
    
    private NiceFeedPreferences() {
        super();
    }
}