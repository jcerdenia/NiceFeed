package com.joshuacerdenia.android.nicefeed.ui.adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0003\u0014\u0015\u0016B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\f\u001a\u00020\r2\n\u0010\u000e\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\bH\u0016J\u001c\u0010\u0010\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\bH\u0016R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/EntryListAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryLight;", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/EntryListAdapter$EntryHolder;", "listener", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/EntryListAdapter$OnEntrySelected;", "(Lcom/joshuacerdenia/android/nicefeed/ui/adapter/EntryListAdapter$OnEntrySelected;)V", "<set-?>", "", "lastClickedPosition", "getLastClickedPosition", "()I", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "DiffCallback", "EntryHolder", "OnEntrySelected", "app_debug"})
public final class EntryListAdapter extends androidx.recyclerview.widget.ListAdapter<com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight, com.joshuacerdenia.android.nicefeed.ui.adapter.EntryListAdapter.EntryHolder> {
    private int lastClickedPosition = 0;
    private final com.joshuacerdenia.android.nicefeed.ui.adapter.EntryListAdapter.OnEntrySelected listener = null;
    
    public final int getLastClickedPosition() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.joshuacerdenia.android.nicefeed.ui.adapter.EntryListAdapter.EntryHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.ui.adapter.EntryListAdapter.EntryHolder holder, int position) {
    }
    
    public EntryListAdapter(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.ui.adapter.EntryListAdapter.OnEntrySelected listener) {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&J\u001a\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&\u00a8\u0006\u000b"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/EntryListAdapter$OnEntrySelected;", "", "onEntryClicked", "", "entryId", "", "view", "Landroid/view/View;", "onEntryLongClicked", "entry", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryLight;", "app_debug"})
    public static abstract interface OnEntrySelected {
        
        public abstract void onEntryClicked(@org.jetbrains.annotations.NotNull()
        java.lang.String entryId, @org.jetbrains.annotations.Nullable()
        android.view.View view);
        
        public abstract void onEntryLongClicked(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight entry, @org.jetbrains.annotations.Nullable()
        android.view.View view);
        
        @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 3)
        public static final class DefaultImpls {
        }
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u0005H\u0016J\u0012\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0005H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/EntryListAdapter$EntryHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroid/view/View$OnClickListener;", "Landroid/view/View$OnLongClickListener;", "view", "Landroid/view/View;", "listener", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/EntryListAdapter$OnEntrySelected;", "(Lcom/joshuacerdenia/android/nicefeed/ui/adapter/EntryListAdapter;Landroid/view/View;Lcom/joshuacerdenia/android/nicefeed/ui/adapter/EntryListAdapter$OnEntrySelected;)V", "container", "Landroidx/constraintlayout/widget/ConstraintLayout;", "entry", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryLight;", "getEntry", "()Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryLight;", "setEntry", "(Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryLight;)V", "imageView", "Landroid/widget/ImageView;", "infoTextView", "Landroid/widget/TextView;", "starView", "titleTextView", "bind", "", "onClick", "v", "onLongClick", "", "app_debug"})
    public final class EntryHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements android.view.View.OnClickListener, android.view.View.OnLongClickListener {
        public com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight entry;
        private final androidx.constraintlayout.widget.ConstraintLayout container = null;
        private final android.widget.TextView titleTextView = null;
        private final android.widget.TextView infoTextView = null;
        private final android.widget.ImageView imageView = null;
        private final android.widget.ImageView starView = null;
        private final com.joshuacerdenia.android.nicefeed.ui.adapter.EntryListAdapter.OnEntrySelected listener = null;
        
        @org.jetbrains.annotations.NotNull()
        public final com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight getEntry() {
            return null;
        }
        
        public final void setEntry(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight p0) {
        }
        
        @android.annotation.SuppressLint(value = {"SetTextI18n"})
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight entry) {
        }
        
        @java.lang.Override()
        public void onClick(@org.jetbrains.annotations.NotNull()
        android.view.View v) {
        }
        
        @java.lang.Override()
        public boolean onLongClick(@org.jetbrains.annotations.Nullable()
        android.view.View v) {
            return false;
        }
        
        public EntryHolder(@org.jetbrains.annotations.NotNull()
        android.view.View view, @org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.ui.adapter.EntryListAdapter.OnEntrySelected listener) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/adapter/EntryListAdapter$DiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryLight;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
    static final class DiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight> {
        
        @java.lang.Override()
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight oldItem, @org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight newItem) {
            return false;
        }
        
        @java.lang.Override()
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight oldItem, @org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight newItem) {
            return false;
        }
        
        public DiffCallback() {
            super();
        }
    }
}