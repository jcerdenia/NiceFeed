package com.joshuacerdenia.android.nicefeed.ui.dialog;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0012\u0010\u000f\u001a\u00020\f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J&\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u001a\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u00132\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u0010\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/dialog/InputUrlFragment;", "Lcom/google/android/material/bottomsheet/BottomSheetDialogFragment;", "()V", "callbacks", "Lcom/joshuacerdenia/android/nicefeed/ui/FeedRequestCallbacks;", "progressBar", "Landroid/widget/ProgressBar;", "subscribeButton", "Landroid/widget/Button;", "urlEditText", "Landroid/widget/EditText;", "onCancel", "", "dialog", "Landroid/content/DialogInterface;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onViewCreated", "view", "submitFeedUrl", "url", "", "Companion", "app_debug"})
public final class InputUrlFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    private android.widget.EditText urlEditText;
    private android.widget.Button subscribeButton;
    private android.widget.ProgressBar progressBar;
    private com.joshuacerdenia.android.nicefeed.ui.FeedRequestCallbacks callbacks;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TAG = "InputUrlFragment";
    private static final java.lang.String ARG_LAST_URL = "ARG_LAST_URL";
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.ui.dialog.InputUrlFragment.Companion Companion = null;
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
    
    private final void submitFeedUrl(java.lang.String url) {
    }
    
    @java.lang.Override()
    public void onCancel(@org.jetbrains.annotations.NotNull()
    android.content.DialogInterface dialog) {
    }
    
    public InputUrlFragment() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/dialog/InputUrlFragment$Companion;", "", "()V", "ARG_LAST_URL", "", "TAG", "newInstance", "Lcom/joshuacerdenia/android/nicefeed/ui/dialog/InputUrlFragment;", "lastAttemptedUrl", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.joshuacerdenia.android.nicefeed.ui.dialog.InputUrlFragment newInstance(@org.jetbrains.annotations.NotNull()
        java.lang.String lastAttemptedUrl) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}