package com.joshuacerdenia.android.nicefeed.data.local.database;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\'J\u0016\u0010\u0007\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\'J!\u0010\b\u001a\u00020\u00032\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000bH\'\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\u00020\u00032\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0005H\'J\b\u0010\u000f\u001a\u00020\u0003H\'J\u001c\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00112\u0006\u0010\t\u001a\u00020\u000bH\'J\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00052\u0006\u0010\t\u001a\u00020\u000bH\'J\u0018\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00112\u0006\u0010\u0015\u001a\u00020\u000bH\'J\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00112\u0006\u0010\u0017\u001a\u00020\u0018H\'J\u0014\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0011H\'J\u0016\u0010\u001a\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\'J)\u0010\u001b\u001a\u00020\u00032\u0012\u0010\u0015\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u001dH\'\u00a2\u0006\u0002\u0010\u001eJ)\u0010\u001f\u001a\u00020\u00032\u0012\u0010\u0015\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000b2\u0006\u0010 \u001a\u00020\u001dH\'\u00a2\u0006\u0002\u0010\u001e\u00a8\u0006!"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/data/local/database/EntriesDao;", "", "addEntries", "", "entries", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/Entry;", "deleteEntries", "deleteEntriesByFeed", "feedId", "", "", "([Ljava/lang/String;)V", "deleteEntriesById", "entryIds", "deleteLeftoverEntries", "getEntriesByFeed", "Landroidx/lifecycle/LiveData;", "getEntriesToggleableByFeedSynchronously", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryToggleable;", "getEntry", "entryId", "getNewEntries", "max", "", "getStarredEntries", "updateEntries", "updateEntryIsRead", "isRead", "", "([Ljava/lang/String;Z)V", "updateEntryIsStarred", "isStarred", "app_debug"})
public abstract interface EntriesDao {
    
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.IGNORE)
    public abstract void addEntries(@org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entries);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM Entry WHERE url = :entryId")
    public abstract androidx.lifecycle.LiveData<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> getEntry(@org.jetbrains.annotations.NotNull()
    java.lang.String entryId);
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.SuppressWarnings(value = {"ROOM_CURSOR_MISMATCH"})
    @androidx.room.Query(value = "SELECT url, title, website, date, image, isStarred, isRead FROM Entry WHERE isRead = 0 ORDER BY date DESC LIMIT :max")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry>> getNewEntries(int max);
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.SuppressWarnings(value = {"ROOM_CURSOR_MISMATCH"})
    @androidx.room.Query(value = "SELECT url, title, website, date, image, isStarred, isRead FROM Entry WHERE isStarred = 1")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry>> getStarredEntries();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT Entry.url, title, website, author, date, content, image, isStarred, isRead FROM FeedEntryCrossRef AS _junction INNER JOIN Entry ON (_junction.entryUrl = Entry.url) WHERE _junction.feedUrl = :feedId")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry>> getEntriesByFeed(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT Entry.url, isStarred, isRead FROM FeedEntryCrossRef AS _junction INNER JOIN Entry ON (_junction.entryUrl = Entry.url) WHERE _junction.feedUrl = :feedId")
    public abstract java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.EntryToggleable> getEntriesToggleableByFeedSynchronously(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId);
    
    @androidx.room.Update()
    public abstract void updateEntries(@org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entries);
    
    @androidx.room.Query(value = "UPDATE Entry SET isStarred = :isStarred WHERE url IN (:entryId)")
    public abstract void updateEntryIsStarred(@org.jetbrains.annotations.NotNull()
    java.lang.String[] entryId, boolean isStarred);
    
    @androidx.room.Query(value = "UPDATE Entry SET isRead = :isRead WHERE url IN (:entryId)")
    public abstract void updateEntryIsRead(@org.jetbrains.annotations.NotNull()
    java.lang.String[] entryId, boolean isRead);
    
    @androidx.room.Delete()
    public abstract void deleteEntries(@org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entries);
    
    @androidx.room.Query(value = "DELETE FROM Entry WHERE url IN (SELECT url FROM FeedEntryCrossRef AS _junction INNER JOIN Entry ON (_junction.entryUrl = Entry.url) WHERE _junction.feedUrl IN (:feedId))")
    public abstract void deleteEntriesByFeed(@org.jetbrains.annotations.NotNull()
    java.lang.String... feedId);
    
    @androidx.room.Query(value = "DELETE FROM Entry WHERE url IN (:entryIds)")
    public abstract void deleteEntriesById(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> entryIds);
    
    @androidx.room.Query(value = "DELETE FROM Entry WHERE url NOT IN (SELECT entryUrl FROM FeedEntryCrossRef)")
    public abstract void deleteLeftoverEntries();
}