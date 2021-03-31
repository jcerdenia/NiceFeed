package com.joshuacerdenia.android.nicefeed.data.local.database;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\bf\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\"\u00020\u0006H\'\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\'J\u0018\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\'J!\u0010\u000f\u001a\u00020\u00032\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0005\"\u00020\nH\'\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00122\u0006\u0010\t\u001a\u00020\nH\'J\u0014\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00140\u0012H\'J\u0014\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00140\u0012H\'J\u0010\u0010\u0017\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\nH\'J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u0014H\'J\u0014\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00140\u0012H\'J\u0014\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u00140\u0012H\'J\u0010\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0006H\'J)\u0010\u001e\u001a\u00020\u00032\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0005\"\u00020\n2\u0006\u0010\u001f\u001a\u00020\nH\'\u00a2\u0006\u0002\u0010 J\u0018\u0010!\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\nH\'J\u0018\u0010#\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010$\u001a\u00020\nH\'J \u0010%\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010$\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\nH\u0017J\u0018\u0010&\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\'\u001a\u00020\fH\'\u00a8\u0006("}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/data/local/database/FeedsDao;", "", "addFeeds", "", "feed", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/Feed;", "([Lcom/joshuacerdenia/android/nicefeed/data/model/feed/Feed;)V", "addToFeedUnreadCount", "feedId", "", "addend", "", "addToFeedUnreadCountByEntry", "entryId", "deleteFeeds", "([Ljava/lang/String;)V", "getFeed", "Landroidx/lifecycle/LiveData;", "getFeedIds", "", "getFeedIdsWithCategories", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedIdWithCategory;", "getFeedTitleSynchronously", "getFeedUrlsSynchronously", "getFeedsLight", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedLight;", "getFeedsManageable", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedManageable;", "updateFeed", "updateFeedCategory", "category", "([Ljava/lang/String;Ljava/lang/String;)V", "updateFeedImage", "feedImage", "updateFeedTitle", "title", "updateFeedTitleAndCategory", "updateFeedUnreadCount", "count", "app_debug"})
public abstract interface FeedsDao {
    
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.IGNORE)
    public abstract void addFeeds(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.feed.Feed... feed);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM Feed WHERE url = :feedId")
    public abstract androidx.lifecycle.LiveData<com.joshuacerdenia.android.nicefeed.data.model.feed.Feed> getFeed(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT url, title, imageUrl, category, unreadCount FROM Feed")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedLight>> getFeedsLight();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT url, title, website, imageUrl, description, category FROM Feed")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable>> getFeedsManageable();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT url FROM Feed")
    public abstract androidx.lifecycle.LiveData<java.util.List<java.lang.String>> getFeedIds();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT url, category FROM Feed")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedIdWithCategory>> getFeedIdsWithCategories();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT url FROM Feed")
    public abstract java.util.List<java.lang.String> getFeedUrlsSynchronously();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT title FROM Feed WHERE url = :feedId")
    public abstract java.lang.String getFeedTitleSynchronously(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId);
    
    @androidx.room.Update()
    public abstract void updateFeed(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.feed.Feed feed);
    
    @androidx.room.Transaction()
    public abstract void updateFeedTitleAndCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String category);
    
    @androidx.room.Query(value = "UPDATE Feed SET title = :title WHERE url = :feedId")
    public abstract void updateFeedTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, @org.jetbrains.annotations.NotNull()
    java.lang.String title);
    
    @androidx.room.Query(value = "UPDATE Feed SET category = :category WHERE url IN (:feedId)")
    public abstract void updateFeedCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String[] feedId, @org.jetbrains.annotations.NotNull()
    java.lang.String category);
    
    @androidx.room.Query(value = "UPDATE Feed SET imageUrl = :feedImage WHERE url = :feedId")
    public abstract void updateFeedImage(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, @org.jetbrains.annotations.NotNull()
    java.lang.String feedImage);
    
    @androidx.room.Query(value = "UPDATE Feed SET unreadCount = :count WHERE url = :feedId")
    public abstract void updateFeedUnreadCount(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, int count);
    
    @androidx.room.Query(value = "UPDATE Feed SET unreadCount = (unreadCount + :addend) WHERE url = :feedId")
    public abstract void addToFeedUnreadCount(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, int addend);
    
    @androidx.room.Query(value = "UPDATE Feed SET unreadCount = (unreadCount + :addend) WHERE url IN (SELECT url FROM FeedEntryCrossRef AS _junction INNER JOIN Feed ON (_junction.feedUrl = Feed.url) WHERE _junction.entryUrl = (:entryId))")
    public abstract void addToFeedUnreadCountByEntry(@org.jetbrains.annotations.NotNull()
    java.lang.String entryId, int addend);
    
    @androidx.room.Query(value = "DELETE FROM Feed WHERE url IN (:feedId)")
    public abstract void deleteFeeds(@org.jetbrains.annotations.NotNull()
    java.lang.String... feedId);
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 3)
    public final class DefaultImpls {
        
        @androidx.room.Transaction()
        public static void updateFeedTitleAndCategory(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.local.database.FeedsDao $this, @org.jetbrains.annotations.NotNull()
        java.lang.String feedId, @org.jetbrains.annotations.NotNull()
        java.lang.String title, @org.jetbrains.annotations.NotNull()
        java.lang.String category) {
        }
    }
}