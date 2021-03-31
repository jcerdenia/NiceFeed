package com.joshuacerdenia.android.nicefeed.ui.adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0003\u0010\u0011\u0012B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001c\u0010\f\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000bH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedSearchAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/joshuacerdenia/android/nicefeed/data/model/SearchResultItem;", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedSearchAdapter$FeedHolder;", "listener", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedSearchAdapter$OnItemClickListener;", "(Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedSearchAdapter$OnItemClickListener;)V", "onBindViewHolder", "", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "DiffCallback", "FeedHolder", "OnItemClickListener", "app_debug"})
public final class FeedSearchAdapter extends androidx.recyclerview.widget.ListAdapter<com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem, com.joshuacerdenia.android.nicefeed.ui.adapter.FeedSearchAdapter.FeedHolder> {
    private final com.joshuacerdenia.android.nicefeed.ui.adapter.FeedSearchAdapter.OnItemClickListener listener = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.joshuacerdenia.android.nicefeed.ui.adapter.FeedSearchAdapter.FeedHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.ui.adapter.FeedSearchAdapter.FeedHolder holder, int position) {
    }
    
    public FeedSearchAdapter(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.ui.adapter.FeedSearchAdapter.OnItemClickListener listener) {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedSearchAdapter$OnItemClickListener;", "", "onItemClicked", "", "searchResultItem", "Lcom/joshuacerdenia/android/nicefeed/data/model/SearchResultItem;", "app_debug"})
    public static abstract interface OnItemClickListener {
        
        public abstract void onItemClicked(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem searchResultItem);
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\rJ\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0004H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedSearchAdapter$FeedHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroid/view/View$OnClickListener;", "view", "Landroid/view/View;", "listener", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedSearchAdapter$OnItemClickListener;", "(Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedSearchAdapter;Landroid/view/View;Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedSearchAdapter$OnItemClickListener;)V", "imageView", "Landroid/widget/ImageView;", "infoTextView", "Landroid/widget/TextView;", "searchResultItem", "Lcom/joshuacerdenia/android/nicefeed/data/model/SearchResultItem;", "titleTextView", "bind", "", "onClick", "v", "app_debug"})
    public final class FeedHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements android.view.View.OnClickListener {
        private com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem searchResultItem;
        private final android.widget.TextView titleTextView = null;
        private final android.widget.TextView infoTextView = null;
        private final android.widget.ImageView imageView = null;
        private final com.joshuacerdenia.android.nicefeed.ui.adapter.FeedSearchAdapter.OnItemClickListener listener = null;
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem searchResultItem) {
        }
        
        @java.lang.Override()
        public void onClick(@org.jetbrains.annotations.NotNull()
        android.view.View v) {
        }
        
        public FeedHolder(@org.jetbrains.annotations.NotNull()
        android.view.View view, @org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.ui.adapter.FeedSearchAdapter.OnItemClickListener listener) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedSearchAdapter$DiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/joshuacerdenia/android/nicefeed/data/model/SearchResultItem;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
    static final class DiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem> {
        
        @java.lang.Override()
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem oldItem, @org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem newItem) {
            return false;
        }
        
        @java.lang.Override()
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem oldItem, @org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem newItem) {
            return false;
        }
        
        public DiffCallback() {
            super();
        }
    }
}