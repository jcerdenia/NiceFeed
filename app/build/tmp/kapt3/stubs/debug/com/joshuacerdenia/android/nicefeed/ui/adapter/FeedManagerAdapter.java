package com.joshuacerdenia.android.nicefeed.ui.adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0003\u001c\u001d\u001eB\u001b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u0010\u001a\u00020\u00112\n\u0010\u0012\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u001c\u0010\u0015\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0014H\u0016J\u000e\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u001bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001f"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedManagerAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedManageable;", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedManagerAdapter$FeedHolder;", "listener", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedManagerAdapter$ItemCheckBoxListener;", "selectedItems", "", "(Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedManagerAdapter$ItemCheckBoxListener;Ljava/util/List;)V", "checkBoxes", "", "Landroid/widget/CheckBox;", "getSelectedItems", "()Ljava/util/List;", "setSelectedItems", "(Ljava/util/List;)V", "onBindViewHolder", "", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "toggleCheckBoxes", "checkAll", "", "DiffCallback", "FeedHolder", "ItemCheckBoxListener", "app_debug"})
public final class FeedManagerAdapter extends androidx.recyclerview.widget.ListAdapter<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable, com.joshuacerdenia.android.nicefeed.ui.adapter.FeedManagerAdapter.FeedHolder> {
    private final java.util.Set<android.widget.CheckBox> checkBoxes = null;
    private final com.joshuacerdenia.android.nicefeed.ui.adapter.FeedManagerAdapter.ItemCheckBoxListener listener = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable> selectedItems;
    
    public final void toggleCheckBoxes(boolean checkAll) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.joshuacerdenia.android.nicefeed.ui.adapter.FeedManagerAdapter.FeedHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.ui.adapter.FeedManagerAdapter.FeedHolder holder, int position) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable> getSelectedItems() {
        return null;
    }
    
    public final void setSelectedItems(@org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable> p0) {
    }
    
    public FeedManagerAdapter(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.ui.adapter.FeedManagerAdapter.ItemCheckBoxListener listener, @org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable> selectedItems) {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\t"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedManagerAdapter$ItemCheckBoxListener;", "", "onAllItemsChecked", "", "isChecked", "", "onItemClicked", "feed", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedManageable;", "app_debug"})
    public static abstract interface ItemCheckBoxListener {
        
        public abstract void onItemClicked(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable feed, boolean isChecked);
        
        public abstract void onAllItemsChecked(boolean isChecked);
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011J\u0012\u0010\u0012\u001a\u00020\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0004H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedManagerAdapter$FeedHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroid/view/View$OnClickListener;", "view", "Landroid/view/View;", "(Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedManagerAdapter;Landroid/view/View;)V", "categoryTextView", "Landroid/widget/TextView;", "checkBox", "Landroid/widget/CheckBox;", "feed", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedManageable;", "titleTextView", "urlTextView", "bind", "", "isChecked", "", "onClick", "v", "app_debug"})
    public final class FeedHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements android.view.View.OnClickListener {
        private com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable feed;
        private final android.widget.CheckBox checkBox = null;
        private final android.widget.TextView titleTextView = null;
        private final android.widget.TextView urlTextView = null;
        private final android.widget.TextView categoryTextView = null;
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable feed, boolean isChecked) {
        }
        
        @java.lang.Override()
        public void onClick(@org.jetbrains.annotations.Nullable()
        android.view.View v) {
        }
        
        public FeedHolder(@org.jetbrains.annotations.NotNull()
        android.view.View view) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedManagerAdapter$DiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedManageable;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
    static final class DiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable> {
        
        @java.lang.Override()
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable oldItem, @org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable newItem) {
            return false;
        }
        
        @java.lang.Override()
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable oldItem, @org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable newItem) {
            return false;
        }
        
        public DiffCallback() {
            super();
        }
    }
}