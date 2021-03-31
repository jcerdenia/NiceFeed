package com.joshuacerdenia.android.nicefeed.ui.adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u00182\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0005\u0017\u0018\u0019\u001a\u001bB\u0017\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\fH\u0016J\u0018\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fH\u0016J\u0010\u0010\u0015\u001a\u00020\u000f2\b\u0010\u0016\u001a\u0004\u0018\u00010\nR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedListAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/joshuacerdenia/android/nicefeed/data/model/FeedMenuItem;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "context", "Landroid/content/Context;", "listener", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedListAdapter$OnItemClickListener;", "(Landroid/content/Context;Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedListAdapter$OnItemClickListener;)V", "activeFeedId", "", "getItemViewType", "", "position", "onBindViewHolder", "", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setActiveFeedId", "feedId", "CategoryHolder", "Companion", "DiffCallback", "FeedHolder", "OnItemClickListener", "app_debug"})
public final class FeedListAdapter extends androidx.recyclerview.widget.ListAdapter<com.joshuacerdenia.android.nicefeed.data.model.FeedMenuItem, androidx.recyclerview.widget.RecyclerView.ViewHolder> {
    private java.lang.String activeFeedId;
    private final android.content.Context context = null;
    private final com.joshuacerdenia.android.nicefeed.ui.adapter.FeedListAdapter.OnItemClickListener listener = null;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.ui.adapter.FeedListAdapter.Companion Companion = null;
    
    public final void setActiveFeedId(@org.jetbrains.annotations.Nullable()
    java.lang.String feedId) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemViewType(int position) {
        return 0;
    }
    
    public FeedListAdapter(@org.jetbrains.annotations.Nullable()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.ui.adapter.FeedListAdapter.OnItemClickListener listener) {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0005H&\u00a8\u0006\b"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedListAdapter$OnItemClickListener;", "", "onCategoryClicked", "", "category", "", "onFeedSelected", "feedId", "app_debug"})
    public static abstract interface OnItemClickListener {
        
        public abstract void onFeedSelected(@org.jetbrains.annotations.NotNull()
        java.lang.String feedId);
        
        public abstract void onCategoryClicked(@org.jetbrains.annotations.NotNull()
        java.lang.String category);
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0004H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedListAdapter$FeedHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroid/view/View$OnClickListener;", "view", "Landroid/view/View;", "(Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedListAdapter;Landroid/view/View;)V", "countTextView", "Landroid/widget/TextView;", "feed", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedLight;", "titleTextView", "bind", "", "isHighlighted", "", "onClick", "v", "app_debug"})
    final class FeedHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements android.view.View.OnClickListener {
        private com.joshuacerdenia.android.nicefeed.data.model.feed.FeedLight feed;
        private final android.widget.TextView titleTextView = null;
        private final android.widget.TextView countTextView = null;
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.feed.FeedLight feed, boolean isHighlighted) {
        }
        
        @java.lang.Override()
        public void onClick(@org.jetbrains.annotations.NotNull()
        android.view.View v) {
        }
        
        public FeedHolder(@org.jetbrains.annotations.NotNull()
        android.view.View view) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0012\u0010\u000f\u001a\u00020\f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0004H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedListAdapter$CategoryHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroid/view/View$OnClickListener;", "view", "Landroid/view/View;", "(Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedListAdapter;Landroid/view/View;)V", "category", "", "categoryTextView", "Landroid/widget/TextView;", "countTextView", "bind", "", "categoryHeader", "Lcom/joshuacerdenia/android/nicefeed/data/model/CategoryHeader;", "onClick", "v", "app_debug"})
    final class CategoryHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements android.view.View.OnClickListener {
        private java.lang.String category;
        private final android.widget.TextView categoryTextView = null;
        private final android.widget.TextView countTextView = null;
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.CategoryHeader categoryHeader) {
        }
        
        @java.lang.Override()
        public void onClick(@org.jetbrains.annotations.Nullable()
        android.view.View v) {
        }
        
        public CategoryHolder(@org.jetbrains.annotations.NotNull()
        android.view.View view) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedListAdapter$DiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/joshuacerdenia/android/nicefeed/data/model/FeedMenuItem;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
    static final class DiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.joshuacerdenia.android.nicefeed.data.model.FeedMenuItem> {
        
        @java.lang.Override()
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.FeedMenuItem oldItem, @org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.FeedMenuItem newItem) {
            return false;
        }
        
        @java.lang.Override()
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.FeedMenuItem oldItem, @org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.FeedMenuItem newItem) {
            return false;
        }
        
        public DiffCallback() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedListAdapter$Companion;", "", "()V", "TYPE_HEADER", "", "TYPE_ITEM", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}