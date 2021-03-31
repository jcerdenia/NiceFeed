package com.joshuacerdenia.android.nicefeed.ui.viewmodel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b#\u0018\u0000 X2\u00020\u00012\u00020\u0002:\u0001XB\u0005\u00a2\u0006\u0002\u0010\u0003J\u0016\u00102\u001a\u00020\u001a2\u000e\b\u0002\u00103\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u0016\u00104\u001a\u00020\u001a2\u000e\b\u0002\u00103\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u0006\u00105\u001a\u000206J\u0006\u00107\u001a\u000206J$\u00108\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00062\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00062\u0006\u0010\u0016\u001a\u00020\u0015H\u0002J\b\u00109\u001a\u0004\u0018\u00010\u0011J\u000e\u0010:\u001a\u0002062\u0006\u0010;\u001a\u00020\u000eJ\u000e\u0010<\u001a\u0002062\u0006\u0010=\u001a\u00020\u001aJ\u0006\u0010>\u001a\u000206J\u0010\u0010?\u001a\u0002062\u0006\u0010@\u001a\u00020\u0011H\u0016J\u0010\u0010A\u001a\u0002062\b\u0010@\u001a\u0004\u0018\u00010\u0011J:\u0010B\u001a\u0002062\u0006\u0010;\u001a\u00020\u000e2\f\u0010C\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00062\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00062\f\u0010E\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006H\u0016J\u0018\u0010F\u001a\u0002062\u0006\u0010;\u001a\u00020\u000e2\u0006\u0010G\u001a\u00020\u0015H\u0016J\u000e\u0010H\u001a\u0002062\u0006\u0010I\u001a\u00020+J$\u0010J\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00062\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00062\u0006\u0010!\u001a\u00020\u000eH\u0002J\u000e\u0010K\u001a\u0002062\u0006\u0010L\u001a\u00020\u000eJ\u000e\u0010M\u001a\u0002062\u0006\u0010\u0016\u001a\u00020\u0015J\u000e\u0010N\u001a\u0002062\u0006\u0010\u001e\u001a\u00020\u0015J$\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u001e\u001a\u00020\u0015H\u0002J\u0006\u0010P\u001a\u000206J\u000e\u0010Q\u001a\u0002062\u0006\u0010!\u001a\u00020\u000eJ\u0016\u0010R\u001a\u0002062\u0006\u0010S\u001a\u00020\u000e2\u0006\u0010T\u001a\u00020\u001aJ\u0016\u0010U\u001a\u0002062\u0006\u0010S\u001a\u00020\u000e2\u0006\u0010V\u001a\u00020\u001aJ\u000e\u0010W\u001a\u0002062\u0006\u0010@\u001a\u00020\u0011R\u001d\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u0015@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010!\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000e@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u000e\u0010$\u001a\u00020%X\u0082\u0004\u00a2\u0006\u0002\n\u0000R(\u0010&\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u000b \'*\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u00060\u00060\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010*\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010+0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0013R\u0011\u0010-\u001a\u00020.\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u000e\u00101\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006Y"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/EntryListViewModel;", "Landroidx/lifecycle/ViewModel;", "Lcom/joshuacerdenia/android/nicefeed/util/UpdateManager$UpdateReceiver;", "()V", "entriesLightLiveData", "Landroidx/lifecycle/MediatorLiveData;", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryLight;", "getEntriesLightLiveData", "()Landroidx/lifecycle/MediatorLiveData;", "entriesLiveData", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/Entry;", "feedIdLiveData", "Landroidx/lifecycle/MutableLiveData;", "", "feedLiveData", "Landroidx/lifecycle/LiveData;", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/Feed;", "getFeedLiveData", "()Landroidx/lifecycle/LiveData;", "<set-?>", "", "filter", "getFilter", "()I", "isAutoUpdating", "", "()Z", "setAutoUpdating", "(Z)V", "order", "parser", "Lcom/joshuacerdenia/android/nicefeed/data/remote/FeedParser;", "query", "getQuery", "()Ljava/lang/String;", "repo", "Lcom/joshuacerdenia/android/nicefeed/data/NiceFeedRepository;", "sourceEntriesLiveData", "kotlin.jvm.PlatformType", "updateManager", "Lcom/joshuacerdenia/android/nicefeed/util/UpdateManager;", "updateResultLiveData", "Lcom/joshuacerdenia/android/nicefeed/data/model/cross/FeedWithEntries;", "getUpdateResultLiveData", "updateValues", "Lcom/joshuacerdenia/android/nicefeed/data/model/UpdateValues;", "getUpdateValues", "()Lcom/joshuacerdenia/android/nicefeed/data/model/UpdateValues;", "updateWasRequested", "allIsRead", "entries", "allIsStarred", "clearQuery", "", "deleteFeedAndEntries", "filterEntries", "getCurrentFeed", "getFeedWithEntries", "feedId", "keepOldUnreadEntries", "isKeeping", "markAllCurrentEntriesAsRead", "onFeedNeedsUpdate", "feed", "onFeedRetrieved", "onOldAndNewEntriesCompared", "entriesToAdd", "entriesToUpdate", "entriesToDelete", "onUnreadEntriesCounted", "unreadCount", "onUpdatesDownloaded", "feedWithEntries", "queryEntries", "requestUpdate", "url", "setFilter", "setOrder", "sortEntries", "starAllCurrentEntries", "submitQuery", "updateEntryIsRead", "entryId", "isRead", "updateEntryIsStarred", "isStarred", "updateFeed", "Companion", "app_debug"})
public final class EntryListViewModel extends androidx.lifecycle.ViewModel implements com.joshuacerdenia.android.nicefeed.util.UpdateManager.UpdateReceiver {
    private final com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository repo = null;
    private final com.joshuacerdenia.android.nicefeed.data.remote.FeedParser parser = null;
    private final com.joshuacerdenia.android.nicefeed.util.UpdateManager updateManager = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> feedIdLiveData = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.joshuacerdenia.android.nicefeed.data.model.feed.Feed> feedLiveData = null;
    private final androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry>> sourceEntriesLiveData = null;
    private final androidx.lifecycle.MediatorLiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry>> entriesLiveData = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MediatorLiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight>> entriesLightLiveData = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries> updateResultLiveData = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String query = "";
    private int order = 0;
    private int filter = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.joshuacerdenia.android.nicefeed.data.model.UpdateValues updateValues = null;
    private boolean updateWasRequested = false;
    private boolean isAutoUpdating = true;
    private static final int MAX_NEW_ENTRIES = 50;
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.ui.viewmodel.EntryListViewModel.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.joshuacerdenia.android.nicefeed.data.model.feed.Feed> getFeedLiveData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MediatorLiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight>> getEntriesLightLiveData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries> getUpdateResultLiveData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getQuery() {
        return null;
    }
    
    public final int getFilter() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.joshuacerdenia.android.nicefeed.data.model.UpdateValues getUpdateValues() {
        return null;
    }
    
    public final boolean isAutoUpdating() {
        return false;
    }
    
    public final void setAutoUpdating(boolean p0) {
    }
    
    public final void getFeedWithEntries(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId) {
    }
    
    public final void requestUpdate(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
    
    public final void onFeedRetrieved(@org.jetbrains.annotations.Nullable()
    com.joshuacerdenia.android.nicefeed.data.model.feed.Feed feed) {
    }
    
    public final void onUpdatesDownloaded(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries feedWithEntries) {
    }
    
    public final void setFilter(int filter) {
    }
    
    public final void setOrder(int order) {
    }
    
    public final void submitQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void clearQuery() {
    }
    
    public final void starAllCurrentEntries() {
    }
    
    public final void markAllCurrentEntriesAsRead() {
    }
    
    public final void keepOldUnreadEntries(boolean isKeeping) {
    }
    
    public final boolean allIsStarred(@org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight> entries) {
        return false;
    }
    
    public final boolean allIsRead(@org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight> entries) {
        return false;
    }
    
    private final java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> queryEntries(java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entries, java.lang.String query) {
        return null;
    }
    
    private final java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> filterEntries(java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entries, int filter) {
        return null;
    }
    
    private final java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight> sortEntries(java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight> entries, int order) {
        return null;
    }
    
    @java.lang.Override()
    public void onUnreadEntriesCounted(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, int unreadCount) {
    }
    
    public final void updateFeed(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.feed.Feed feed) {
    }
    
    @java.lang.Override()
    public void onFeedNeedsUpdate(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.feed.Feed feed) {
    }
    
    @java.lang.Override()
    public void onOldAndNewEntriesCompared(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToAdd, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToUpdate, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entriesToDelete) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.joshuacerdenia.android.nicefeed.data.model.feed.Feed getCurrentFeed() {
        return null;
    }
    
    public final void updateEntryIsStarred(@org.jetbrains.annotations.NotNull()
    java.lang.String entryId, boolean isStarred) {
    }
    
    public final void updateEntryIsRead(@org.jetbrains.annotations.NotNull()
    java.lang.String entryId, boolean isRead) {
    }
    
    public final void deleteFeedAndEntries() {
    }
    
    public EntryListViewModel() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/EntryListViewModel$Companion;", "", "()V", "MAX_NEW_ENTRIES", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}