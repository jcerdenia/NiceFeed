package com.joshuacerdenia.android.nicefeed.util;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u0004\u0018\u00010\tJ\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010\u0010\u001a\u00020\u000fH\u0002J\u0010\u0010\u0011\u001a\u00020\u000f2\b\u0010\b\u001a\u0004\u0018\u00010\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u00020\u00048BX\u0082\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/util/BackupUrlManager;", "", "()V", "COUNTER_MAX", "", "attemptCount", "getAttemptCount", "()I", "url", "", "urlPlusFeed", "urlPlusRss", "urlPlusRssXml", "getNextUrl", "reset", "", "resetValues", "setBase", "app_debug"})
public final class BackupUrlManager {
    private static final int COUNTER_MAX = 4;
    private static int attemptCount = 0;
    private static java.lang.String url;
    private static java.lang.String urlPlusFeed;
    private static java.lang.String urlPlusRss;
    private static java.lang.String urlPlusRssXml;
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.util.BackupUrlManager INSTANCE = null;
    
    private final int getAttemptCount() {
        return 0;
    }
    
    public final void setBase(@org.jetbrains.annotations.Nullable()
    java.lang.String url) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNextUrl() {
        return null;
    }
    
    public final void reset() {
    }
    
    private final void resetValues() {
    }
    
    private BackupUrlManager() {
        super();
    }
}