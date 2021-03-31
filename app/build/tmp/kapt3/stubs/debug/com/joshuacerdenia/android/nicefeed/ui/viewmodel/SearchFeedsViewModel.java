package com.joshuacerdenia.android.nicefeed.ui.viewmodel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\u000e\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u0006R\u001d\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001d\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\bR\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/SearchFeedsViewModel;", "Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/FeedAddingViewModel;", "()V", "feedIdsLiveData", "Landroidx/lifecycle/LiveData;", "", "", "getFeedIdsLiveData", "()Landroidx/lifecycle/LiveData;", "initialQueryIsMade", "", "getInitialQueryIsMade", "()Z", "setInitialQueryIsMade", "(Z)V", "mutableQuery", "Landroidx/lifecycle/MutableLiveData;", "newQuery", "getNewQuery", "()Ljava/lang/String;", "setNewQuery", "(Ljava/lang/String;)V", "searchResultLiveData", "Lcom/joshuacerdenia/android/nicefeed/data/model/SearchResultItem;", "getSearchResultLiveData", "searcher", "Lcom/joshuacerdenia/android/nicefeed/data/remote/FeedSearcher;", "onFeedIdsRetrieved", "", "feedIds", "performSearch", "query", "app_debug"})
public final class SearchFeedsViewModel extends com.joshuacerdenia.android.nicefeed.ui.viewmodel.FeedAddingViewModel {
    private final com.joshuacerdenia.android.nicefeed.data.remote.FeedSearcher searcher = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String newQuery = "";
    private boolean initialQueryIsMade = false;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<java.lang.String>> feedIdsLiveData = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> mutableQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem>> searchResultLiveData = null;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNewQuery() {
        return null;
    }
    
    public final void setNewQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final boolean getInitialQueryIsMade() {
        return false;
    }
    
    public final void setInitialQueryIsMade(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<java.lang.String>> getFeedIdsLiveData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem>> getSearchResultLiveData() {
        return null;
    }
    
    public final void onFeedIdsRetrieved(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> feedIds) {
    }
    
    public final void performSearch(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public SearchFeedsViewModel() {
        super();
    }
}