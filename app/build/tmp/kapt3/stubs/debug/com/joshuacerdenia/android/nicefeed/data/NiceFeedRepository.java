package com.joshuacerdenia.android.nicefeed.data;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u000e\u0018\u0000 J2\u00020\u0001:\u0001JB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u001f\u0010\u0012\u001a\u00020\u000f2\u0012\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u0014\"\u00020\u0015\u00a2\u0006\u0002\u0010\u0016J\u001f\u0010\u0017\u001a\u00020\u000f2\u0012\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00190\u0014\"\u00020\u0019\u00a2\u0006\u0002\u0010\u001aJ\u0006\u0010\u001b\u001a\u00020\u000fJ\u001a\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u001d2\u0006\u0010\u0018\u001a\u00020\u0019J\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u001e2\u0006\u0010\u0018\u001a\u00020\u0019J\u0016\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001f0\u001d2\u0006\u0010#\u001a\u00020\u0019J\u0016\u0010$\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u001d2\u0006\u0010\u0018\u001a\u00020\u0019J\u0012\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u001e0\u001dJ\u0012\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\'0\u001e0\u001dJ\u000e\u0010(\u001a\u00020)2\u0006\u0010\u0018\u001a\u00020\u0019J\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00190\u001eJ\u0012\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020,0\u001e0\u001dJ\u0012\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\u001e0\u001dJ\u001a\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u001d2\u0006\u00100\u001a\u000201J\u0012\u00102\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u001dJ4\u00103\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00192\f\u00104\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\f\u00105\u001a\b\u0012\u0004\u0012\u00020!0\u001e2\b\u00106\u001a\u0004\u0018\u00010\u0019J8\u00107\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00192\f\u00108\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eJ\u001e\u0010;\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u00192\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020=J\'\u0010?\u001a\u00020\u000f2\u0012\u0010#\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00190\u0014\"\u00020\u00192\u0006\u0010<\u001a\u00020=\u00a2\u0006\u0002\u0010@J\'\u0010A\u001a\u00020\u000f2\u0012\u0010#\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00190\u0014\"\u00020\u00192\u0006\u0010>\u001a\u00020=\u00a2\u0006\u0002\u0010@J\u000e\u0010B\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0015J\'\u0010C\u001a\u00020\u000f2\u0012\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00190\u0014\"\u00020\u00192\u0006\u0010D\u001a\u00020\u0019\u00a2\u0006\u0002\u0010EJ\u001e\u0010F\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010G\u001a\u00020\u00192\u0006\u0010D\u001a\u00020\u0019J\u0016\u0010H\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010I\u001a\u000201R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n \u000b*\u0004\u0018\u00010\n0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006K"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/data/NiceFeedRepository;", "", "database", "Lcom/joshuacerdenia/android/nicefeed/data/local/database/NiceFeedDatabase;", "networkMonitor", "Lcom/joshuacerdenia/android/nicefeed/util/NetworkMonitor;", "(Lcom/joshuacerdenia/android/nicefeed/data/local/database/NiceFeedDatabase;Lcom/joshuacerdenia/android/nicefeed/util/NetworkMonitor;)V", "dao", "Lcom/joshuacerdenia/android/nicefeed/data/local/database/CombinedDao;", "executor", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "getNetworkMonitor", "()Lcom/joshuacerdenia/android/nicefeed/util/NetworkMonitor;", "addFeedWithEntries", "", "feedWithEntries", "Lcom/joshuacerdenia/android/nicefeed/data/model/cross/FeedWithEntries;", "addFeeds", "feed", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/Feed;", "([Lcom/joshuacerdenia/android/nicefeed/data/model/feed/Feed;)V", "deleteFeedAndEntriesById", "feedId", "", "([Ljava/lang/String;)V", "deleteLeftoverItems", "getEntriesByFeed", "Landroidx/lifecycle/LiveData;", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/Entry;", "getEntriesToggleableByFeedSynchronously", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryToggleable;", "getEntry", "entryId", "getFeed", "getFeedIds", "getFeedIdsWithCategories", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedIdWithCategory;", "getFeedTitleWithEntriesToggleableSynchronously", "Lcom/joshuacerdenia/android/nicefeed/data/model/cross/FeedTitleWithEntriesToggleable;", "getFeedUrlsSynchronously", "getFeedsLight", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedLight;", "getFeedsManageable", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedManageable;", "getNewEntries", "max", "", "getStarredEntries", "handleBackgroundUpdate", "newEntries", "oldEntries", "feedImage", "handleEntryUpdates", "entriesToAdd", "entriesToUpdate", "entriesToDelete", "updateEntryAndFeedUnreadCount", "isRead", "", "isStarred", "updateEntryIsRead", "([Ljava/lang/String;Z)V", "updateEntryIsStarred", "updateFeed", "updateFeedCategory", "category", "([Ljava/lang/String;Ljava/lang/String;)V", "updateFeedTitleAndCategory", "title", "updateFeedUnreadCount", "count", "Companion", "app_debug"})
public final class NiceFeedRepository {
    private final com.joshuacerdenia.android.nicefeed.data.local.database.CombinedDao dao = null;
    private final java.util.concurrent.ExecutorService executor = null;
    @org.jetbrains.annotations.NotNull()
    private final com.joshuacerdenia.android.nicefeed.util.NetworkMonitor networkMonitor = null;
    private static com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.joshuacerdenia.android.nicefeed.data.model.feed.Feed> getFeed(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedLight>> getFeedsLight() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<java.lang.String>> getFeedIds() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedIdWithCategory>> getFeedIdsWithCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getFeedUrlsSynchronously() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.joshuacerdenia.android.nicefeed.data.model.cross.FeedTitleWithEntriesToggleable getFeedTitleWithEntriesToggleableSynchronously(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable>> getFeedsManageable() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> getEntry(@org.jetbrains.annotations.NotNull()
    java.lang.String entryId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry>> getEntriesByFeed(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry>> getNewEntries(int max) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry>> getStarredEntries() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.EntryToggleable> getEntriesToggleableByFeedSynchronously(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId) {
        return null;
    }
    
    public final void addFeeds(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.feed.Feed... feed) {
    }
    
    public final void addFeedWithEntries(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries feedWithEntries) {
    }
    
    public final void updateFeed(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.feed.Feed feed) {
    }
    
    public final void updateFeedTitleAndCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String category) {
    }
    
    public final void updateFeedCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String[] feedId, @org.jetbrains.annotations.NotNull()
    java.lang.String category) {
    }
    
    public final void updateFeedUnreadCount(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, int count) {
    }
    
    public final void updateEntryAndFeedUnreadCount(@org.jetbrains.annotations.NotNull()
    java.lang.String entryId, boolean isRead, boolean isStarred) {
    }
    
    public final void updateEntryIsStarred(@org.jetbrains.annotations.NotNull()
    java.lang.String[] entryId, boolean isStarred) {
    }
    
    public final void updateEntryIsRead(@org.jetbrains.annotations.NotNull()
    java.lang.String[] entryId, boolean isRead) {
    }
    
    public final void handleEntryUpdates(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToAdd, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToUpdate, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToDelete) {
    }
    
    public final void handleBackgroundUpdate(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> newEntries, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.EntryToggleable> oldEntries, @org.jetbrains.annotations.Nullable()
    java.lang.String feedImage) {
    }
    
    public final void deleteFeedAndEntriesById(@org.jetbrains.annotations.NotNull()
    java.lang.String... feedId) {
    }
    
    public final void deleteLeftoverItems() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.joshuacerdenia.android.nicefeed.util.NetworkMonitor getNetworkMonitor() {
        return null;
    }
    
    private NiceFeedRepository(com.joshuacerdenia.android.nicefeed.data.local.database.NiceFeedDatabase database, com.joshuacerdenia.android.nicefeed.util.NetworkMonitor networkMonitor) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/data/NiceFeedRepository$Companion;", "", "()V", "INSTANCE", "Lcom/joshuacerdenia/android/nicefeed/data/NiceFeedRepository;", "get", "initialize", "", "database", "Lcom/joshuacerdenia/android/nicefeed/data/local/database/NiceFeedDatabase;", "networkMonitor", "Lcom/joshuacerdenia/android/nicefeed/util/NetworkMonitor;", "app_debug"})
    public static final class Companion {
        
        public final void initialize(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.local.database.NiceFeedDatabase database, @org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.util.NetworkMonitor networkMonitor) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository get() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}