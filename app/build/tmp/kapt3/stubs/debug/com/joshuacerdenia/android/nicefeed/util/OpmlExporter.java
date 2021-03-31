package com.joshuacerdenia.android.nicefeed.util;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u001bB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\u0014\u0010\u0017\u001a\u00020\u00142\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fJ\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aH\u0002R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\nR\u0016\u0010\u000b\u001a\n \r*\u0004\u0018\u00010\f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8BX\u0082\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/util/OpmlExporter;", "", "context", "Landroid/content/Context;", "listener", "Lcom/joshuacerdenia/android/nicefeed/util/OpmlExporter$ExportResultListener;", "(Landroid/content/Context;Lcom/joshuacerdenia/android/nicefeed/util/OpmlExporter$ExportResultListener;)V", "categories", "", "", "[Ljava/lang/String;", "contentResolver", "Landroid/content/ContentResolver;", "kotlin.jvm.PlatformType", "feeds", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedManageable;", "getFeeds", "()Ljava/util/List;", "executeExport", "", "uri", "Landroid/net/Uri;", "submitFeeds", "writeOpml", "writer", "Ljava/io/OutputStreamWriter;", "ExportResultListener", "app_debug"})
public final class OpmlExporter {
    private final android.content.ContentResolver contentResolver = null;
    private java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable> feeds;
    private java.lang.String[] categories = {};
    private final com.joshuacerdenia.android.nicefeed.util.OpmlExporter.ExportResultListener listener = null;
    
    private final java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable> getFeeds() {
        return null;
    }
    
    public final void submitFeeds(@org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable> feeds) {
    }
    
    public final void executeExport(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    private final void writeOpml(java.io.OutputStreamWriter writer) {
    }
    
    public OpmlExporter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.util.OpmlExporter.ExportResultListener listener) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&\u00a8\u0006\b"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/util/OpmlExporter$ExportResultListener;", "", "onExportAttempted", "", "isSuccessful", "", "fileName", "", "app_debug"})
    public static abstract interface ExportResultListener {
        
        public abstract void onExportAttempted(boolean isSuccessful, @org.jetbrains.annotations.Nullable()
        java.lang.String fileName);
    }
}