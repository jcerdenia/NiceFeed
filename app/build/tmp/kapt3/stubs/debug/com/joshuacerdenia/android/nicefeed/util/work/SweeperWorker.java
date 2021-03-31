package com.joshuacerdenia.android.nicefeed.util.work;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/util/work/SweeperWorker;", "Landroidx/work/Worker;", "context", "Landroid/content/Context;", "workerParams", "Landroidx/work/WorkerParameters;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;)V", "repo", "Lcom/joshuacerdenia/android/nicefeed/data/NiceFeedRepository;", "doWork", "Landroidx/work/ListenableWorker$Result;", "Companion", "app_debug"})
public final class SweeperWorker extends androidx.work.Worker {
    private final com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository repo = null;
    private static final java.lang.String WORK_NAME = "com.joshuacerdenia.android.nicefeed.utils.work.SweeperWorker";
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.util.work.SweeperWorker.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.work.ListenableWorker.Result doWork() {
        return null;
    }
    
    public SweeperWorker(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    androidx.work.WorkerParameters workerParams) {
        super(null, null);
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/util/work/SweeperWorker$Companion;", "", "()V", "WORK_NAME", "", "start", "", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        public final void start(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
        
        private Companion() {
            super();
        }
    }
}