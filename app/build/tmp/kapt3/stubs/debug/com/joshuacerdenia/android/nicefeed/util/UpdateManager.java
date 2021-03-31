package com.joshuacerdenia.android.nicefeed.util;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001&B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000bJ\u001c\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00062\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\u0010\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u000bH\u0002J\u0016\u0010\u001d\u001a\u00020\u00162\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\u0010\u0010\u001f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\u0007H\u0002J\u0014\u0010!\u001a\u00020\u00162\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u000e\u0010\"\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000bJ\u000e\u0010#\u001a\u00020\u00162\u0006\u0010$\u001a\u00020%R\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068BX\u0082\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\"\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\n\u001a\u0004\u0018\u00010\u000b@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/util/UpdateManager;", "", "receiver", "Lcom/joshuacerdenia/android/nicefeed/util/UpdateManager$UpdateReceiver;", "(Lcom/joshuacerdenia/android/nicefeed/util/UpdateManager$UpdateReceiver;)V", "currentEntries", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/Entry;", "getCurrentEntries", "()Ljava/util/List;", "<set-?>", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/Feed;", "currentFeed", "getCurrentFeed", "()Lcom/joshuacerdenia/android/nicefeed/data/model/feed/Feed;", "keepOldUnreadEntries", "", "getKeepOldUnreadEntries", "()Z", "setKeepOldUnreadEntries", "(Z)V", "forceUpdateFeed", "", "feed", "getEntryIds", "", "entries", "handleFeedUpdate", "newFeed", "handleNewEntries", "newEntries", "isUnique", "newEntry", "setInitialEntries", "setInitialFeed", "submitUpdates", "feedWithEntries", "Lcom/joshuacerdenia/android/nicefeed/data/model/cross/FeedWithEntries;", "UpdateReceiver", "app_debug"})
public final class UpdateManager {
    private boolean keepOldUnreadEntries = true;
    @org.jetbrains.annotations.Nullable()
    private com.joshuacerdenia.android.nicefeed.data.model.feed.Feed currentFeed;
    private java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> currentEntries;
    private final com.joshuacerdenia.android.nicefeed.util.UpdateManager.UpdateReceiver receiver = null;
    
    public final boolean getKeepOldUnreadEntries() {
        return false;
    }
    
    public final void setKeepOldUnreadEntries(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.joshuacerdenia.android.nicefeed.data.model.feed.Feed getCurrentFeed() {
        return null;
    }
    
    private final java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> getCurrentEntries() {
        return null;
    }
    
    public final void setInitialFeed(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.feed.Feed feed) {
    }
    
    public final void setInitialEntries(@org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entries) {
    }
    
    public final void submitUpdates(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries feedWithEntries) {
    }
    
    public final void forceUpdateFeed(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.feed.Feed feed) {
    }
    
    private final void handleNewEntries(java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> newEntries) {
    }
    
    private final boolean isUnique(com.joshuacerdenia.android.nicefeed.data.model.entry.Entry newEntry) {
        return false;
    }
    
    private final java.util.List<java.lang.String> getEntryIds(java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entries) {
        return null;
    }
    
    private final void handleFeedUpdate(com.joshuacerdenia.android.nicefeed.data.model.feed.Feed newFeed) {
    }
    
    public UpdateManager(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.util.UpdateManager.UpdateReceiver receiver) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J:\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH&J\u0018\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H&\u00a8\u0006\u0011"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/util/UpdateManager$UpdateReceiver;", "", "onFeedNeedsUpdate", "", "feed", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/Feed;", "onOldAndNewEntriesCompared", "feedId", "", "entriesToAdd", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/Entry;", "entriesToUpdate", "entriesToDelete", "onUnreadEntriesCounted", "unreadCount", "", "app_debug"})
    public static abstract interface UpdateReceiver {
        
        public abstract void onUnreadEntriesCounted(@org.jetbrains.annotations.NotNull()
        java.lang.String feedId, int unreadCount);
        
        public abstract void onFeedNeedsUpdate(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.feed.Feed feed);
        
        public abstract void onOldAndNewEntriesCompared(@org.jetbrains.annotations.NotNull()
        java.lang.String feedId, @org.jetbrains.annotations.NotNull()
        java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToAdd, @org.jetbrains.annotations.NotNull()
        java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToUpdate, @org.jetbrains.annotations.NotNull()
        java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToDelete);
    }
}