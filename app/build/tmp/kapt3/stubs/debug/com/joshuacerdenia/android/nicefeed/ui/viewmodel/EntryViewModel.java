package com.joshuacerdenia.android.nicefeed.ui.viewmodel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010*\u001a\u00020+2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u000e\u0010,\u001a\u00020+2\u0006\u0010-\u001a\u00020\u0010J\u0006\u0010.\u001a\u00020+J\u000e\u0010/\u001a\u00020+2\u0006\u0010(\u001a\u00020\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\"\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\t\u001a\u0004\u0018\u00010\n@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0019\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0006\"\u0004\b\u001f\u0010\bR&\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140!X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u000e\u0010&\u001a\u00020\'X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010(\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0016\u00a8\u00060"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/EntryViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "bannerIsEnabled", "", "getBannerIsEnabled", "()Z", "setBannerIsEnabled", "(Z)V", "<set-?>", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/Entry;", "entry", "getEntry", "()Lcom/joshuacerdenia/android/nicefeed/data/model/entry/Entry;", "entryIdLiveData", "Landroidx/lifecycle/MutableLiveData;", "", "entryLiveData", "Landroidx/lifecycle/LiveData;", "font", "", "getFont", "()I", "setFont", "(I)V", "htmlLiveData", "Landroidx/lifecycle/MediatorLiveData;", "getHtmlLiveData", "()Landroidx/lifecycle/MediatorLiveData;", "isExcerpt", "isInitialLoading", "setInitialLoading", "lastPosition", "Lkotlin/Pair;", "getLastPosition", "()Lkotlin/Pair;", "setLastPosition", "(Lkotlin/Pair;)V", "repo", "Lcom/joshuacerdenia/android/nicefeed/data/NiceFeedRepository;", "textSize", "getTextSize", "drawHtml", "", "getEntryById", "entryId", "saveChanges", "setTextSize", "app_debug"})
public final class EntryViewModel extends androidx.lifecycle.ViewModel {
    private final com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository repo = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> entryIdLiveData = null;
    private final androidx.lifecycle.LiveData<com.joshuacerdenia.android.nicefeed.data.model.entry.Entry> entryLiveData = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MediatorLiveData<java.lang.String> htmlLiveData = null;
    @org.jetbrains.annotations.NotNull()
    private kotlin.Pair<java.lang.Integer, java.lang.Integer> lastPosition;
    private int textSize = 0;
    private int font = 0;
    private boolean bannerIsEnabled = true;
    private boolean isInitialLoading = true;
    @org.jetbrains.annotations.Nullable()
    private com.joshuacerdenia.android.nicefeed.data.model.entry.Entry entry;
    private boolean isExcerpt = false;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MediatorLiveData<java.lang.String> getHtmlLiveData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.Pair<java.lang.Integer, java.lang.Integer> getLastPosition() {
        return null;
    }
    
    public final void setLastPosition(@org.jetbrains.annotations.NotNull()
    kotlin.Pair<java.lang.Integer, java.lang.Integer> p0) {
    }
    
    public final int getTextSize() {
        return 0;
    }
    
    public final int getFont() {
        return 0;
    }
    
    public final void setFont(int p0) {
    }
    
    public final boolean getBannerIsEnabled() {
        return false;
    }
    
    public final void setBannerIsEnabled(boolean p0) {
    }
    
    public final boolean isInitialLoading() {
        return false;
    }
    
    public final void setInitialLoading(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.joshuacerdenia.android.nicefeed.data.model.entry.Entry getEntry() {
        return null;
    }
    
    public final void getEntryById(@org.jetbrains.annotations.NotNull()
    java.lang.String entryId) {
    }
    
    public final void setTextSize(int textSize) {
    }
    
    private final void drawHtml(com.joshuacerdenia.android.nicefeed.data.model.entry.Entry entry) {
    }
    
    public final void saveChanges() {
    }
    
    public EntryViewModel() {
        super();
    }
}