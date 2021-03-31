package com.joshuacerdenia.android.nicefeed.data.remote;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0002J\"\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002J\u001a\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u00152\u0006\u0010\f\u001a\u00020\u000bR\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \u0007*\u0004\u0018\u00010\t0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/data/remote/FeedSearcher;", "", "networkMonitor", "Lcom/joshuacerdenia/android/nicefeed/util/NetworkMonitor;", "(Lcom/joshuacerdenia/android/nicefeed/util/NetworkMonitor;)V", "feedlyApi", "Lcom/joshuacerdenia/android/nicefeed/data/remote/api/FeedlyApi;", "kotlin.jvm.PlatformType", "retrofit", "Lretrofit2/Retrofit;", "createQueryString", "", "query", "fetchSearchResult", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/SearchResultItem;", "request", "Lretrofit2/Call;", "Lcom/joshuacerdenia/android/nicefeed/data/remote/api/SearchResult;", "getFeedList", "Landroidx/lifecycle/LiveData;", "Companion", "app_debug"})
public final class FeedSearcher {
    private final retrofit2.Retrofit retrofit = null;
    private final com.joshuacerdenia.android.nicefeed.data.remote.api.FeedlyApi feedlyApi = null;
    private final com.joshuacerdenia.android.nicefeed.util.NetworkMonitor networkMonitor = null;
    private static final int RESULTS_COUNT = 100;
    private static final java.lang.String BASE_URL = "https://cloud.feedly.com/";
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.data.remote.FeedSearcher.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem>> getFeedList(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    private final java.lang.String createQueryString(java.lang.String query) {
        return null;
    }
    
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem>> fetchSearchResult(retrofit2.Call<com.joshuacerdenia.android.nicefeed.data.remote.api.SearchResult> request) {
        return null;
    }
    
    public FeedSearcher(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.util.NetworkMonitor networkMonitor) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/data/remote/FeedSearcher$Companion;", "", "()V", "BASE_URL", "", "RESULTS_COUNT", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}