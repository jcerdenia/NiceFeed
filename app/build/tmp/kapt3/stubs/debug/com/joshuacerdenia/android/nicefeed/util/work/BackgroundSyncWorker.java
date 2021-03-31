package com.joshuacerdenia.android.nicefeed.util.work;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0011\u0010\u000f\u001a\u00020\u0010H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J*\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001c"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/util/work/BackgroundSyncWorker;", "Landroidx/work/CoroutineWorker;", "context", "Landroid/content/Context;", "workerParams", "Landroidx/work/WorkerParameters;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;)V", "parser", "Lcom/joshuacerdenia/android/nicefeed/data/remote/FeedParser;", "getParser", "()Lcom/joshuacerdenia/android/nicefeed/data/remote/FeedParser;", "repo", "Lcom/joshuacerdenia/android/nicefeed/data/NiceFeedRepository;", "getRepo", "()Lcom/joshuacerdenia/android/nicefeed/data/NiceFeedRepository;", "doWork", "Landroidx/work/ListenableWorker$Result;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "handleRetrievedData", "", "fwe", "Lcom/joshuacerdenia/android/nicefeed/data/model/cross/FeedWithEntries;", "storedEntries", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryToggleable;", "newEntries", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/Entry;", "Companion", "app_debug"})
public class BackgroundSyncWorker extends androidx.work.CoroutineWorker {
    @org.jetbrains.annotations.NotNull()
    private final com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.joshuacerdenia.android.nicefeed.data.remote.FeedParser parser = null;
    private final android.content.Context context = null;
    private static final java.lang.String WORK_NAME = "com.joshuacerdenia.android.nicefeed.utils.work.BackgroundSyncWorker";
    private static final androidx.work.Constraints constraints = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.util.work.BackgroundSyncWorker.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository getRepo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.joshuacerdenia.android.nicefeed.data.remote.FeedParser getParser() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object doWork(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.work.ListenableWorker.Result> p0) {
        return null;
    }
    
    public final void handleRetrievedData(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries fwe, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.EntryToggleable> storedEntries, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> newEntries) {
    }
    
    public BackgroundSyncWorker(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    androidx.work.WorkerParameters workerParams) {
        super(null, null);
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/util/work/BackgroundSyncWorker$Companion;", "", "()V", "WORK_NAME", "", "constraints", "Landroidx/work/Constraints;", "cancel", "", "context", "Landroid/content/Context;", "runOnce", "start", "app_debug"})
    public static final class Companion {
        
        public final void start(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
        
        public final void runOnce(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
        
        public final void cancel(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
        
        private Companion() {
            super();
        }
    }
}