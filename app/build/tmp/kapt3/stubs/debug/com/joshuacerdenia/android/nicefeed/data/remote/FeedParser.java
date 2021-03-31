package com.joshuacerdenia.android.nicefeed.data.remote;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u0000 \u00192\u00020\u0001:\u0002\u0018\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0019\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u001b\u0010\u0014\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J%\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0012H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\t8F\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001a"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/data/remote/FeedParser;", "", "networkMonitor", "Lcom/joshuacerdenia/android/nicefeed/util/NetworkMonitor;", "(Lcom/joshuacerdenia/android/nicefeed/util/NetworkMonitor;)V", "_feedRequestLiveData", "Landroidx/lifecycle/MutableLiveData;", "Lcom/joshuacerdenia/android/nicefeed/data/model/cross/FeedWithEntries;", "feedRequestLiveData", "Landroidx/lifecycle/LiveData;", "getFeedRequestLiveData", "()Landroidx/lifecycle/LiveData;", "rssParser", "Lcom/prof/rssparser/Parser;", "cancelRequest", "", "executeRequest", "url", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFeedSynchronously", "requestFeed", "backup", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "ChannelMapper", "Companion", "app_debug"})
public final class FeedParser {
    private com.prof.rssparser.Parser rssParser;
    private final androidx.lifecycle.MutableLiveData<com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries> _feedRequestLiveData = null;
    private final com.joshuacerdenia.android.nicefeed.util.NetworkMonitor networkMonitor = null;
    private static final java.lang.String TAG = "FeedParser";
    private static final java.lang.String UNTITLED = "Untitled";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FLAG_EXCERPT = "com.joshuacerdenia.android.nicefeed.excerpt ";
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.data.remote.FeedParser.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries> getFeedRequestLiveData() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getFeedSynchronously(@org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries> p1) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object requestFeed(@org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.Nullable()
    java.lang.String backup, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> p2) {
        return null;
    }
    
    public final void cancelRequest() {
    }
    
    public FeedParser(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.util.NetworkMonitor networkMonitor) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bJ\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u0004H\u0002J\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0004H\u0002J\u000e\u0010\u0012\u001a\u00020\u0004*\u0004\u0018\u00010\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/data/remote/FeedParser$ChannelMapper;", "", "()V", "DATE_PATTERN", "", "MAX_ENTRIES", "", "makeFeedWithEntries", "Lcom/joshuacerdenia/android/nicefeed/data/model/cross/FeedWithEntries;", "url", "channel", "Lcom/prof/rssparser/Channel;", "mapEntries", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/Entry;", "parseDate", "Ljava/util/Date;", "stringDate", "flagAsExcerpt", "app_debug"})
    static final class ChannelMapper {
        private static final int MAX_ENTRIES = 300;
        private static final java.lang.String DATE_PATTERN = "EEE, d MMM yyyy HH:mm:ss Z";
        @org.jetbrains.annotations.NotNull()
        public static final com.joshuacerdenia.android.nicefeed.data.remote.FeedParser.ChannelMapper INSTANCE = null;
        
        @org.jetbrains.annotations.NotNull()
        public final com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries makeFeedWithEntries(@org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.NotNull()
        com.prof.rssparser.Channel channel) {
            return null;
        }
        
        private final java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> mapEntries(com.prof.rssparser.Channel channel, java.lang.String url) {
            return null;
        }
        
        private final java.util.Date parseDate(java.lang.String stringDate) {
            return null;
        }
        
        private final java.lang.String flagAsExcerpt(java.lang.String $this$flagAsExcerpt) {
            return null;
        }
        
        private ChannelMapper() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/data/remote/FeedParser$Companion;", "", "()V", "FLAG_EXCERPT", "", "TAG", "UNTITLED", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}