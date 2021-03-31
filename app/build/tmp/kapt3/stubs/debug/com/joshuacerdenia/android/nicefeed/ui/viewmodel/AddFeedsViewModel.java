package com.joshuacerdenia.android.nicefeed.ui.viewmodel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\b\u0018\u0000 *2\u00020\u0001:\u0001*B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001f\u0010 \u001a\u00020!2\u0012\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00190#\"\u00020\u0019\u00a2\u0006\u0002\u0010$J\u001c\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005H\u0002J\u0014\u0010&\u001a\u00020!2\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\b0\u0005J\u0014\u0010(\u001a\u00020!2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00150\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\nR\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00050\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R \u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\n\"\u0004\b\u001b\u0010\fR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00148F\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010\u0017\u00a8\u0006+"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/AddFeedsViewModel;", "Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/FeedAddingViewModel;", "()V", "_topicBlocksLiveData", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/TopicBlock;", "categories", "", "getCategories", "()Ljava/util/List;", "setCategories", "(Ljava/util/List;)V", "colorsResId", "", "defaultTopics", "", "defaultTopicsResId", "getDefaultTopicsResId", "feedIdsWithCategoriesLiveData", "Landroidx/lifecycle/LiveData;", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedIdWithCategory;", "getFeedIdsWithCategoriesLiveData", "()Landroidx/lifecycle/LiveData;", "feedsToImport", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/Feed;", "getFeedsToImport", "setFeedsToImport", "isFirstTimeLoading", "", "topicBlocksLiveData", "getTopicBlocksLiveData", "addFeeds", "", "feed", "", "([Lcom/joshuacerdenia/android/nicefeed/data/model/feed/Feed;)V", "getTopicBlocks", "initDefaultTopics", "topics", "onFeedDataRetrieved", "data", "Companion", "app_debug"})
public final class AddFeedsViewModel extends com.joshuacerdenia.android.nicefeed.ui.viewmodel.FeedAddingViewModel {
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedIdWithCategory>> feedIdsWithCategoriesLiveData = null;
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.TopicBlock>> _topicBlocksLiveData = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.Feed> feedsToImport;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<java.lang.String> categories;
    private boolean isFirstTimeLoading = true;
    private final java.util.List<java.lang.String> defaultTopics = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Integer> defaultTopicsResId = null;
    private final java.util.List<java.lang.Integer> colorsResId = null;
    private static final int MAX_TOPICS = 10;
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.ui.viewmodel.AddFeedsViewModel.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedIdWithCategory>> getFeedIdsWithCategoriesLiveData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.joshuacerdenia.android.nicefeed.data.model.TopicBlock>> getTopicBlocksLiveData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.Feed> getFeedsToImport() {
        return null;
    }
    
    public final void setFeedsToImport(@org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.Feed> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getCategories() {
        return null;
    }
    
    public final void setCategories(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> getDefaultTopicsResId() {
        return null;
    }
    
    public final void initDefaultTopics(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> topics) {
    }
    
    public final void onFeedDataRetrieved(@org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedIdWithCategory> data) {
    }
    
    private final java.util.List<com.joshuacerdenia.android.nicefeed.data.model.TopicBlock> getTopicBlocks(java.util.List<java.lang.String> categories) {
        return null;
    }
    
    public final void addFeeds(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.feed.Feed... feed) {
    }
    
    public AddFeedsViewModel() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/AddFeedsViewModel$Companion;", "", "()V", "MAX_TOPICS", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}