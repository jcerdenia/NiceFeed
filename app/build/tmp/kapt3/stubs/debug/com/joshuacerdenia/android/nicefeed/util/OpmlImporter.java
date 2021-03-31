package com.joshuacerdenia.android.nicefeed.util;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0011B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u000e\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0010R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/util/OpmlImporter;", "", "context", "Landroid/content/Context;", "listener", "Lcom/joshuacerdenia/android/nicefeed/util/OpmlImporter$OnOpmlParsedListener;", "(Landroid/content/Context;Lcom/joshuacerdenia/android/nicefeed/util/OpmlImporter$OnOpmlParsedListener;)V", "contentResolver", "Landroid/content/ContentResolver;", "kotlin.jvm.PlatformType", "parseOpml", "", "opmlReader", "Ljava/io/Reader;", "submitUri", "uri", "Landroid/net/Uri;", "OnOpmlParsedListener", "app_debug"})
public final class OpmlImporter {
    private final android.content.ContentResolver contentResolver = null;
    private final com.joshuacerdenia.android.nicefeed.util.OpmlImporter.OnOpmlParsedListener listener = null;
    
    public final void submitUri(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    private final void parseOpml(java.io.Reader opmlReader) {
    }
    
    public OpmlImporter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.util.OpmlImporter.OnOpmlParsedListener listener) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\b\u0010\u0007\u001a\u00020\u0003H&\u00a8\u0006\b"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/util/OpmlImporter$OnOpmlParsedListener;", "", "onOpmlParsed", "", "feeds", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/Feed;", "onParseOpmlFailed", "app_debug"})
    public static abstract interface OnOpmlParsedListener {
        
        public abstract void onOpmlParsed(@org.jetbrains.annotations.NotNull()
        java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.Feed> feeds);
        
        public abstract void onParseOpmlFailed();
    }
}