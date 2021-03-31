package com.joshuacerdenia.android.nicefeed.data.local.database;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\bg\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003J\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0017J!\u0010\u000b\u001a\u00020\u00052\u0012\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000e0\r\"\u00020\u000eH\u0017\u00a2\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0005H\u0017J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\u000eH\u0017J6\u0010\u0013\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000e2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\t2\b\u0010\u0017\u001a\u0004\u0018\u00010\u000eH\u0017J:\u0010\u0018\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000e2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0017J \u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fH\u0017J)\u0010!\u001a\u00020\u00052\u0012\u0010\u001d\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000e0\r\"\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u001fH\u0017\u00a2\u0006\u0002\u0010\"\u00a8\u0006#"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/data/local/database/CombinedDao;", "Lcom/joshuacerdenia/android/nicefeed/data/local/database/FeedsDao;", "Lcom/joshuacerdenia/android/nicefeed/data/local/database/EntriesDao;", "Lcom/joshuacerdenia/android/nicefeed/data/local/database/FeedEntryCrossRefsDao;", "addFeedAndEntries", "", "feed", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/Feed;", "entries", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/Entry;", "deleteFeedAndEntriesById", "feedId", "", "", "([Ljava/lang/String;)V", "deleteLeftoverItems", "getFeedTitleAndEntriesToggleableSynchronously", "Lcom/joshuacerdenia/android/nicefeed/data/model/cross/FeedTitleWithEntriesToggleable;", "handleBackgroundUpdate", "newEntries", "oldEntries", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryToggleable;", "feedImage", "handleEntryUpdates", "entriesToAdd", "entriesToUpdate", "entriesToDelete", "updateEntryAndFeedUnreadCount", "entryId", "isRead", "", "isStarred", "updateEntryIsReadAndFeedUnreadCount", "([Ljava/lang/String;Z)V", "app_debug"})
public abstract interface CombinedDao extends com.joshuacerdenia.android.nicefeed.data.local.database.FeedsDao, com.joshuacerdenia.android.nicefeed.data.local.database.EntriesDao, com.joshuacerdenia.android.nicefeed.data.local.database.FeedEntryCrossRefsDao {
    
    @androidx.room.Transaction()
    public abstract void addFeedAndEntries(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.feed.Feed feed, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entries);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Transaction()
    public abstract com.joshuacerdenia.android.nicefeed.data.model.cross.FeedTitleWithEntriesToggleable getFeedTitleAndEntriesToggleableSynchronously(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId);
    
    @androidx.room.Transaction()
    public abstract void handleEntryUpdates(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToAdd, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToUpdate, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToDelete);
    
    @androidx.room.Transaction()
    public abstract void handleBackgroundUpdate(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> newEntries, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.EntryToggleable> oldEntries, @org.jetbrains.annotations.Nullable()
    java.lang.String feedImage);
    
    @androidx.room.Transaction()
    public abstract void updateEntryAndFeedUnreadCount(@org.jetbrains.annotations.NotNull()
    java.lang.String entryId, boolean isRead, boolean isStarred);
    
    @androidx.room.Transaction()
    public abstract void updateEntryIsReadAndFeedUnreadCount(@org.jetbrains.annotations.NotNull()
    java.lang.String[] entryId, boolean isRead);
    
    @androidx.room.Transaction()
    public abstract void deleteFeedAndEntriesById(@org.jetbrains.annotations.NotNull()
    java.lang.String... feedId);
    
    @androidx.room.Transaction()
    public abstract void deleteLeftoverItems();
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 3)
    public final class DefaultImpls {
        
        @androidx.room.Transaction()
        public static void addFeedAndEntries(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.local.database.CombinedDao $this, @org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.feed.Feed feed, @org.jetbrains.annotations.NotNull()
        java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entries) {
        }
        
        @org.jetbrains.annotations.NotNull()
        @androidx.room.Transaction()
        public static com.joshuacerdenia.android.nicefeed.data.model.cross.FeedTitleWithEntriesToggleable getFeedTitleAndEntriesToggleableSynchronously(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.local.database.CombinedDao $this, @org.jetbrains.annotations.NotNull()
        java.lang.String feedId) {
            return null;
        }
        
        @androidx.room.Transaction()
        public static void handleEntryUpdates(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.local.database.CombinedDao $this, @org.jetbrains.annotations.NotNull()
        java.lang.String feedId, @org.jetbrains.annotations.NotNull()
        java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToAdd, @org.jetbrains.annotations.NotNull()
        java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToUpdate, @org.jetbrains.annotations.NotNull()
        java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToDelete) {
        }
        
        @androidx.room.Transaction()
        public static void handleBackgroundUpdate(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.local.database.CombinedDao $this, @org.jetbrains.annotations.NotNull()
        java.lang.String feedId, @org.jetbrains.annotations.NotNull()
        java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> newEntries, @org.jetbrains.annotations.NotNull()
        java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.EntryToggleable> oldEntries, @org.jetbrains.annotations.Nullable()
        java.lang.String feedImage) {
        }
        
        @androidx.room.Transaction()
        public static void updateEntryAndFeedUnreadCount(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.local.database.CombinedDao $this, @org.jetbrains.annotations.NotNull()
        java.lang.String entryId, boolean isRead, boolean isStarred) {
        }
        
        @androidx.room.Transaction()
        public static void updateEntryIsReadAndFeedUnreadCount(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.local.database.CombinedDao $this, @org.jetbrains.annotations.NotNull()
        java.lang.String[] entryId, boolean isRead) {
        }
        
        @androidx.room.Transaction()
        public static void deleteFeedAndEntriesById(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.local.database.CombinedDao $this, @org.jetbrains.annotations.NotNull()
        java.lang.String... feedId) {
        }
        
        @androidx.room.Transaction()
        public static void deleteLeftoverItems(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.local.database.CombinedDao $this) {
        }
        
        @androidx.room.Transaction()
        @java.lang.Override()
        public static void updateFeedTitleAndCategory(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.local.database.CombinedDao $this, @org.jetbrains.annotations.NotNull()
        java.lang.String feedId, @org.jetbrains.annotations.NotNull()
        java.lang.String title, @org.jetbrains.annotations.NotNull()
        java.lang.String category) {
        }
        
        @androidx.room.Transaction()
        @java.lang.Override()
        public static void addFeedEntryCrossRefs(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.local.database.CombinedDao $this, @org.jetbrains.annotations.NotNull()
        java.lang.String feedId, @org.jetbrains.annotations.NotNull()
        java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entries) {
        }
    }
}