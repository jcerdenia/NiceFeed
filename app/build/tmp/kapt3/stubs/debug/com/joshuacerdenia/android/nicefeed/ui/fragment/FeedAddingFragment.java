package com.joshuacerdenia.android.nicefeed.ui.fragment;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000 \u00152\u00020\u0001:\u0003\u0014\u0015\u0016B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0010H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\t\u001a\b\u0018\u00010\nR\u00020\u0000X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0017"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedAddingFragment;", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/VisibleFragment;", "()V", "callbacks", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedAddingFragment$Callbacks;", "getCallbacks", "()Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedAddingFragment$Callbacks;", "setCallbacks", "(Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedAddingFragment$Callbacks;)V", "resultManager", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedAddingFragment$RequestResultManager;", "getResultManager", "()Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedAddingFragment$RequestResultManager;", "setResultManager", "(Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedAddingFragment$RequestResultManager;)V", "onAttach", "", "context", "Landroid/content/Context;", "onDetach", "Callbacks", "Companion", "RequestResultManager", "app_debug"})
public abstract class FeedAddingFragment extends com.joshuacerdenia.android.nicefeed.ui.fragment.VisibleFragment {
    @org.jetbrains.annotations.Nullable()
    private com.joshuacerdenia.android.nicefeed.ui.fragment.FeedAddingFragment.Callbacks callbacks;
    @org.jetbrains.annotations.Nullable()
    private com.joshuacerdenia.android.nicefeed.ui.fragment.FeedAddingFragment.RequestResultManager resultManager;
    private static final int SUBSCRIPTION_LIMIT = 1000;
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.ui.fragment.FeedAddingFragment.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.Nullable()
    public final com.joshuacerdenia.android.nicefeed.ui.fragment.FeedAddingFragment.Callbacks getCallbacks() {
        return null;
    }
    
    public final void setCallbacks(@org.jetbrains.annotations.Nullable()
    com.joshuacerdenia.android.nicefeed.ui.fragment.FeedAddingFragment.Callbacks p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.joshuacerdenia.android.nicefeed.ui.fragment.FeedAddingFragment.RequestResultManager getResultManager() {
        return null;
    }
    
    public final void setResultManager(@org.jetbrains.annotations.Nullable()
    com.joshuacerdenia.android.nicefeed.ui.fragment.FeedAddingFragment.RequestResultManager p0) {
    }
    
    @java.lang.Override()
    public void onAttach(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @java.lang.Override()
    public void onDetach() {
    }
    
    public FeedAddingFragment() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u00012\u00020\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0007H&\u00a8\u0006\n"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedAddingFragment$Callbacks;", "Lcom/joshuacerdenia/android/nicefeed/ui/OnToolbarInflated;", "Lcom/joshuacerdenia/android/nicefeed/ui/OnFinished;", "onImportOpmlSelected", "", "onNewFeedAdded", "feedId", "", "onQuerySubmitted", "query", "app_debug"})
    public static abstract interface Callbacks extends com.joshuacerdenia.android.nicefeed.ui.OnToolbarInflated, com.joshuacerdenia.android.nicefeed.ui.OnFinished {
        
        public abstract void onNewFeedAdded(@org.jetbrains.annotations.NotNull()
        java.lang.String feedId);
        
        public abstract void onQuerySubmitted(@org.jetbrains.annotations.NotNull()
        java.lang.String query);
        
        public abstract void onImportOpmlSelected();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0006\u0010\r\u001a\u00020\u000eJ\b\u0010\u000f\u001a\u00020\u000eH\u0002J\u0018\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\fH\u0002J\b\u0010\u0012\u001a\u00020\u000eH\u0002J\b\u0010\u0013\u001a\u00020\u000eH\u0002J\u0010\u0010\u0014\u001a\u00020\u000e2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedAddingFragment$RequestResultManager;", "", "viewModel", "Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/FeedAddingViewModel;", "view", "Landroid/view/View;", "negativeMessageRes", "", "(Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedAddingFragment;Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/FeedAddingViewModel;Landroid/view/View;I)V", "isAlreadyAdded", "", "feedId", "", "onRequestDismissed", "", "showAlreadyAddedNotice", "showFeedAddedNotice", "title", "showLimitReachedNotice", "showRequestFailedNotice", "submitData", "feedWithEntries", "Lcom/joshuacerdenia/android/nicefeed/data/model/cross/FeedWithEntries;", "app_debug"})
    public final class RequestResultManager {
        private final com.joshuacerdenia.android.nicefeed.ui.viewmodel.FeedAddingViewModel viewModel = null;
        private final android.view.View view = null;
        private final int negativeMessageRes = 0;
        
        public final void submitData(@org.jetbrains.annotations.Nullable()
        com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries feedWithEntries) {
        }
        
        public final void onRequestDismissed() {
        }
        
        private final boolean isAlreadyAdded(java.lang.String feedId) {
            return false;
        }
        
        private final void showFeedAddedNotice(java.lang.String feedId, java.lang.String title) {
        }
        
        private final void showAlreadyAddedNotice() {
        }
        
        private final void showRequestFailedNotice() {
        }
        
        private final void showLimitReachedNotice() {
        }
        
        public RequestResultManager(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.ui.viewmodel.FeedAddingViewModel viewModel, @org.jetbrains.annotations.NotNull()
        android.view.View view, int negativeMessageRes) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedAddingFragment$Companion;", "", "()V", "SUBSCRIPTION_LIMIT", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}