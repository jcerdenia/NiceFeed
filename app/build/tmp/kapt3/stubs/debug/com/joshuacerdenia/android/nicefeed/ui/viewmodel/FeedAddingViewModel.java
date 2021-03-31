package com.joshuacerdenia.android.nicefeed.ui.viewmodel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0012J\u0006\u0010+\u001a\u00020)J\b\u0010,\u001a\u00020)H\u0002J\u001a\u0010-\u001a\u00020)2\u0006\u0010.\u001a\u00020\u000b2\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\u000bR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0019\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\bR\u001a\u0010\u0017\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u001e\u001a\u00020\u001f\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001a\u0010\"\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0006\"\u0004\b$\u0010\bR\u001a\u0010%\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0006\"\u0004\b\'\u0010\b\u00a8\u00060"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/FeedAddingViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "alreadyAddedNoticeEnabled", "", "getAlreadyAddedNoticeEnabled", "()Z", "setAlreadyAddedNoticeEnabled", "(Z)V", "currentFeedIds", "", "", "getCurrentFeedIds", "()Ljava/util/List;", "setCurrentFeedIds", "(Ljava/util/List;)V", "feedRequestLiveData", "Landroidx/lifecycle/LiveData;", "Lcom/joshuacerdenia/android/nicefeed/data/model/cross/FeedWithEntries;", "getFeedRequestLiveData", "()Landroidx/lifecycle/LiveData;", "isActiveRequest", "setActiveRequest", "lastInputUrl", "getLastInputUrl", "()Ljava/lang/String;", "setLastInputUrl", "(Ljava/lang/String;)V", "parser", "Lcom/joshuacerdenia/android/nicefeed/data/remote/FeedParser;", "repo", "Lcom/joshuacerdenia/android/nicefeed/data/NiceFeedRepository;", "getRepo", "()Lcom/joshuacerdenia/android/nicefeed/data/NiceFeedRepository;", "requestFailedNoticeEnabled", "getRequestFailedNoticeEnabled", "setRequestFailedNoticeEnabled", "subscriptionLimitNoticeEnabled", "getSubscriptionLimitNoticeEnabled", "setSubscriptionLimitNoticeEnabled", "addFeedWithEntries", "", "feedWithEntries", "cancelRequest", "onFeedRequested", "requestFeed", "url", "backup", "app_debug"})
public abstract class FeedAddingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository repo = null;
    private final com.joshuacerdenia.android.nicefeed.data.remote.FeedParser parser = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries> feedRequestLiveData = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<java.lang.String> currentFeedIds;
    private boolean isActiveRequest = false;
    private boolean requestFailedNoticeEnabled = false;
    private boolean alreadyAddedNoticeEnabled = false;
    private boolean subscriptionLimitNoticeEnabled = false;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String lastInputUrl = "";
    
    @org.jetbrains.annotations.NotNull()
    public final com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository getRepo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries> getFeedRequestLiveData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getCurrentFeedIds() {
        return null;
    }
    
    public final void setCurrentFeedIds(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> p0) {
    }
    
    public final boolean isActiveRequest() {
        return false;
    }
    
    public final void setActiveRequest(boolean p0) {
    }
    
    public final boolean getRequestFailedNoticeEnabled() {
        return false;
    }
    
    public final void setRequestFailedNoticeEnabled(boolean p0) {
    }
    
    public final boolean getAlreadyAddedNoticeEnabled() {
        return false;
    }
    
    public final void setAlreadyAddedNoticeEnabled(boolean p0) {
    }
    
    public final boolean getSubscriptionLimitNoticeEnabled() {
        return false;
    }
    
    public final void setSubscriptionLimitNoticeEnabled(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLastInputUrl() {
        return null;
    }
    
    public final void setLastInputUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final void requestFeed(@org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.Nullable()
    java.lang.String backup) {
    }
    
    private final void onFeedRequested() {
    }
    
    public final void addFeedWithEntries(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries feedWithEntries) {
    }
    
    public final void cancelRequest() {
    }
    
    public FeedAddingViewModel() {
        super();
    }
}