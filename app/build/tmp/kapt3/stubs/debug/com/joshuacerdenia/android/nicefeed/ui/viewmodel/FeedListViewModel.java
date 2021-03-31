package com.joshuacerdenia.android.nicefeed.ui.viewmodel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010#\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010 \u001a\u00020!H\u0002J\u001c\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0011H\u0002J*\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00112\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040%H\u0002J\u000e\u0010&\u001a\u00020!2\u0006\u0010\'\u001a\u00020\u0016J\u0016\u0010(\u001a\u00020!2\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010%J$\u0010)\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00112\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00112\u0006\u0010\'\u001a\u00020\u0016H\u0002J\u000e\u0010*\u001a\u00020!2\u0006\u0010+\u001a\u00020\u0004R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR,\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\n2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\n@BX\u0086\u000e\u00a2\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u00110\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/FeedListViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "activeFeedId", "", "getActiveFeedId", "()Ljava/lang/String;", "setActiveFeedId", "(Ljava/lang/String;)V", "<set-?>", "", "categories", "getCategories", "()[Ljava/lang/String;", "[Ljava/lang/String;", "feedListLiveData", "Landroidx/lifecycle/MediatorLiveData;", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/FeedMenuItem;", "getFeedListLiveData", "()Landroidx/lifecycle/MediatorLiveData;", "feedOrder", "", "minimizedCategories", "", "getMinimizedCategories", "()Ljava/util/Set;", "repo", "Lcom/joshuacerdenia/android/nicefeed/data/NiceFeedRepository;", "sourceFeedsLiveData", "Landroidx/lifecycle/LiveData;", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedLight;", "arrangeMenu", "", "getOrderedCategories", "feeds", "organizeFeedsAndCategories", "", "setFeedOrder", "order", "setMinimizedCategories", "sortFeeds", "toggleCategoryDropDown", "category", "app_debug"})
public final class FeedListViewModel extends androidx.lifecycle.ViewModel {
    private final com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository repo = null;
    private final androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedLight>> sourceFeedsLiveData = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String activeFeedId;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String[] categories = {};
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> minimizedCategories = null;
    private int feedOrder = 0;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MediatorLiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.FeedMenuItem>> feedListLiveData = null;
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getActiveFeedId() {
        return null;
    }
    
    public final void setActiveFeedId(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String[] getCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> getMinimizedCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MediatorLiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.FeedMenuItem>> getFeedListLiveData() {
        return null;
    }
    
    public final void setMinimizedCategories(@org.jetbrains.annotations.Nullable()
    java.util.Set<java.lang.String> categories) {
    }
    
    public final void toggleCategoryDropDown(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
    }
    
    public final void setFeedOrder(int order) {
    }
    
    private final void arrangeMenu() {
    }
    
    private final java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedLight> sortFeeds(java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedLight> feeds, int order) {
        return null;
    }
    
    private final java.util.List<com.joshuacerdenia.android.nicefeed.data.model.FeedMenuItem> organizeFeedsAndCategories(java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedLight> feeds, java.util.Set<java.lang.String> minimizedCategories) {
        return null;
    }
    
    private final java.util.List<java.lang.String> getOrderedCategories(java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedLight> feeds) {
        return null;
    }
    
    public FeedListViewModel() {
        super();
    }
}