package com.joshuacerdenia.android.nicefeed.ui.adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0004\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0003\u001a\u001b\u001cB\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u000f\u001a\u00020\u00102\n\u0010\u0011\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u0012\u001a\u00020\nH\u0016J\u001c\u0010\u0013\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\nH\u0016J\u0018\u0010\u0017\u001a\u00020\u00102\u000e\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0019H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u001d"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/TopicAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/joshuacerdenia/android/nicefeed/data/model/TopicBlock;", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/TopicAdapter$TopicHolder;", "context", "Landroid/content/Context;", "listener", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/TopicAdapter$OnItemClickListener;", "(Landroid/content/Context;Lcom/joshuacerdenia/android/nicefeed/ui/adapter/TopicAdapter$OnItemClickListener;)V", "numOfItems", "", "getNumOfItems", "()I", "setNumOfItems", "(I)V", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "submitList", "list", "", "DiffCallback", "OnItemClickListener", "TopicHolder", "app_debug"})
public final class TopicAdapter extends androidx.recyclerview.widget.ListAdapter<com.joshuacerdenia.android.nicefeed.data.model.TopicBlock, com.joshuacerdenia.android.nicefeed.ui.adapter.TopicAdapter.TopicHolder> {
    private int numOfItems = 0;
    private final android.content.Context context = null;
    private final com.joshuacerdenia.android.nicefeed.ui.adapter.TopicAdapter.OnItemClickListener listener = null;
    
    public final int getNumOfItems() {
        return 0;
    }
    
    public final void setNumOfItems(int p0) {
    }
    
    @java.lang.Override()
    public void submitList(@org.jetbrains.annotations.Nullable()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.TopicBlock> list) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.joshuacerdenia.android.nicefeed.ui.adapter.TopicAdapter.TopicHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.ui.adapter.TopicAdapter.TopicHolder holder, int position) {
    }
    
    public TopicAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.ui.adapter.TopicAdapter.OnItemClickListener listener) {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/TopicAdapter$OnItemClickListener;", "", "onTopicSelected", "", "topic", "", "app_debug"})
    public static abstract interface OnItemClickListener {
        
        public abstract void onTopicSelected(@org.jetbrains.annotations.NotNull()
        java.lang.String topic);
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u0007J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u0004H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/TopicAdapter$TopicHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroid/view/View$OnClickListener;", "view", "Landroid/view/View;", "(Lcom/joshuacerdenia/android/nicefeed/ui/adapter/TopicAdapter;Landroid/view/View;)V", "topicBlock", "Lcom/joshuacerdenia/android/nicefeed/data/model/TopicBlock;", "topicTextView", "Landroid/widget/TextView;", "bind", "", "onClick", "v", "app_debug"})
    public final class TopicHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements android.view.View.OnClickListener {
        private com.joshuacerdenia.android.nicefeed.data.model.TopicBlock topicBlock;
        private final android.widget.TextView topicTextView = null;
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.TopicBlock topicBlock) {
        }
        
        @java.lang.Override()
        public void onClick(@org.jetbrains.annotations.NotNull()
        android.view.View v) {
        }
        
        public TopicHolder(@org.jetbrains.annotations.NotNull()
        android.view.View view) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/TopicAdapter$DiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/joshuacerdenia/android/nicefeed/data/model/TopicBlock;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
    static final class DiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.joshuacerdenia.android.nicefeed.data.model.TopicBlock> {
        
        @java.lang.Override()
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.TopicBlock oldItem, @org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.TopicBlock newItem) {
            return false;
        }
        
        @java.lang.Override()
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.TopicBlock oldItem, @org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.TopicBlock newItem) {
            return false;
        }
        
        public DiffCallback() {
            super();
        }
    }
}