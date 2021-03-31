package com.joshuacerdenia.android.nicefeed.data.local.database;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0017J\u0016\u0010\u0002\u001a\u00020\u00032\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007H\'J!\u0010\u000b\u001a\u00020\u00032\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\f\"\u00020\u0005H\'\u00a2\u0006\u0002\u0010\rJ\u001e\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007H\'J\b\u0010\u0010\u001a\u00020\u0003H\'\u00a8\u0006\u0011"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/data/local/database/FeedEntryCrossRefsDao;", "", "addFeedEntryCrossRefs", "", "feedId", "", "entries", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/Entry;", "crossRefs", "Lcom/joshuacerdenia/android/nicefeed/data/model/cross/FeedEntryCrossRef;", "deleteCrossRefsByFeed", "", "([Ljava/lang/String;)V", "deleteFeedEntryCrossRefs", "entryIds", "deleteLeftoverCrossRefs", "app_debug"})
public abstract interface FeedEntryCrossRefsDao {
    
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.IGNORE)
    public abstract void addFeedEntryCrossRefs(@org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.cross.FeedEntryCrossRef> crossRefs);
    
    @androidx.room.Transaction()
    public abstract void addFeedEntryCrossRefs(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entries);
    
    @androidx.room.Query(value = "DELETE FROM FeedEntryCrossRef WHERE feedUrl = :feedId AND entryUrl IN (:entryIds)")
    public abstract void deleteFeedEntryCrossRefs(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> entryIds);
    
    @androidx.room.Query(value = "DELETE FROM FeedEntryCrossRef WHERE feedUrl IN (:feedId)")
    public abstract void deleteCrossRefsByFeed(@org.jetbrains.annotations.NotNull()
    java.lang.String... feedId);
    
    @androidx.room.Query(value = "DELETE FROM FeedEntryCrossRef WHERE feedUrl NOT IN (SELECT url FROM Feed)")
    public abstract void deleteLeftoverCrossRefs();
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 3)
    public final class DefaultImpls {
        
        @androidx.room.Transaction()
        public static void addFeedEntryCrossRefs(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.local.database.FeedEntryCrossRefsDao $this, @org.jetbrains.annotations.NotNull()
        java.lang.String feedId, @org.jetbrains.annotations.NotNull()
        java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entries) {
        }
    }
}