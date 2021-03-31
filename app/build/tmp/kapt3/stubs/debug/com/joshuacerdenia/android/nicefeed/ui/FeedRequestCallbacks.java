package com.joshuacerdenia.android.nicefeed.ui;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u001c\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006H&\u00a8\u0006\b"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/FeedRequestCallbacks;", "", "onRequestDismissed", "", "onRequestSubmitted", "url", "", "backup", "app_debug"})
public abstract interface FeedRequestCallbacks {
    
    public abstract void onRequestSubmitted(@org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.Nullable()
    java.lang.String backup);
    
    public abstract void onRequestDismissed();
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 3)
    public final class DefaultImpls {
    }
}