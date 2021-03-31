package com.joshuacerdenia.android.nicefeed.ui.dialog;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 %2\u00020\u0001:\u0002$%B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0014H\u0002J\u0012\u0010\u0016\u001a\u00020\u00122\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J&\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\u001a\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u001a2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\u0012\u0010!\u001a\u00020\u00122\b\u0010\"\u001a\u0004\u0018\u00010#H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/dialog/EditFeedFragment;", "Lcom/google/android/material/bottomsheet/BottomSheetDialogFragment;", "()V", "callback", "Lcom/joshuacerdenia/android/nicefeed/ui/dialog/EditFeedFragment$Callback;", "categoryEditText", "Landroid/widget/AutoCompleteTextView;", "descriptionTextView", "Landroid/widget/TextView;", "doneButton", "Landroid/widget/Button;", "imageView", "Landroid/widget/ImageView;", "titleEditText", "Landroid/widget/EditText;", "undoButton", "urlTextView", "fillEditables", "", "title", "", "category", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onViewCreated", "view", "submit", "feed", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedManageable;", "Callback", "Companion", "app_debug"})
public final class EditFeedFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    private android.widget.ImageView imageView;
    private android.widget.EditText titleEditText;
    private android.widget.TextView urlTextView;
    private android.widget.AutoCompleteTextView categoryEditText;
    private android.widget.TextView descriptionTextView;
    private android.widget.Button undoButton;
    private android.widget.Button doneButton;
    private com.joshuacerdenia.android.nicefeed.ui.dialog.EditFeedFragment.Callback callback;
    private static final java.lang.String ARG_FEED = "ARG_FEED";
    private static final java.lang.String ARG_CATEGORIES = "ARG_CATEGORIES";
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.ui.dialog.EditFeedFragment.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void fillEditables(java.lang.String title, java.lang.String category) {
    }
    
    private final void submit(com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable feed) {
    }
    
    public EditFeedFragment() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\t"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/dialog/EditFeedFragment$Callback;", "", "onFeedInfoSubmitted", "", "title", "", "category", "isChanged", "", "app_debug"})
    public static abstract interface Callback {
        
        public abstract void onFeedInfoSubmitted(@org.jetbrains.annotations.NotNull()
        java.lang.String title, @org.jetbrains.annotations.NotNull()
        java.lang.String category, boolean isChanged);
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J!\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u000b\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/dialog/EditFeedFragment$Companion;", "", "()V", "ARG_CATEGORIES", "", "ARG_FEED", "newInstance", "Lcom/joshuacerdenia/android/nicefeed/ui/dialog/EditFeedFragment;", "feed", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedManageable;", "categories", "", "(Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedManageable;[Ljava/lang/String;)Lcom/joshuacerdenia/android/nicefeed/ui/dialog/EditFeedFragment;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.joshuacerdenia.android.nicefeed.ui.dialog.EditFeedFragment newInstance(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable feed, @org.jetbrains.annotations.NotNull()
        java.lang.String[] categories) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}