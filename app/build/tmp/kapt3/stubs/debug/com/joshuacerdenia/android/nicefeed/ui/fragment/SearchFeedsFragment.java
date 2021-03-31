package com.joshuacerdenia.android.nicefeed.ui.fragment;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u0000 .2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001.B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J&\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001b\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0010\u0010\"\u001a\u00020\u00152\u0006\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020\u0015H\u0016J\u001a\u0010&\u001a\u00020\u00152\u0006\u0010\'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010(H\u0016J\u001a\u0010*\u001a\u00020\u00152\u0006\u0010+\u001a\u00020\u001e2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\b\u0010,\u001a\u00020\u0015H\u0002J\b\u0010-\u001a\u00020\u0015H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/fragment/SearchFeedsFragment;", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedAddingFragment;", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedSearchAdapter$OnItemClickListener;", "Lcom/joshuacerdenia/android/nicefeed/ui/FeedRequestCallbacks;", "()V", "adapter", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedSearchAdapter;", "fragment", "noItemsTextView", "Landroid/widget/TextView;", "progressBar", "Landroid/widget/ProgressBar;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "searchView", "Landroidx/appcompat/widget/SearchView;", "toolbar", "Landroidx/appcompat/widget/Toolbar;", "viewModel", "Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/SearchFeedsViewModel;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "menu", "Landroid/view/Menu;", "inflater", "Landroid/view/MenuInflater;", "onCreateView", "Landroid/view/View;", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onItemClicked", "searchResultItem", "Lcom/joshuacerdenia/android/nicefeed/data/model/SearchResultItem;", "onRequestDismissed", "onRequestSubmitted", "url", "", "backup", "onViewCreated", "view", "setupRecyclerView", "setupToolbar", "Companion", "app_debug"})
public final class SearchFeedsFragment extends com.joshuacerdenia.android.nicefeed.ui.fragment.FeedAddingFragment implements com.joshuacerdenia.android.nicefeed.ui.adapter.FeedSearchAdapter.OnItemClickListener, com.joshuacerdenia.android.nicefeed.ui.FeedRequestCallbacks {
    private com.joshuacerdenia.android.nicefeed.ui.viewmodel.SearchFeedsViewModel viewModel;
    private androidx.appcompat.widget.Toolbar toolbar;
    private android.widget.ProgressBar progressBar;
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private android.widget.TextView noItemsTextView;
    private androidx.appcompat.widget.SearchView searchView;
    private com.joshuacerdenia.android.nicefeed.ui.adapter.FeedSearchAdapter adapter;
    private final com.joshuacerdenia.android.nicefeed.ui.fragment.SearchFeedsFragment fragment = null;
    private static final java.lang.String ARG_INITIAL_QUERY = "ARG_INITIAL_QUERY";
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.ui.fragment.SearchFeedsFragment.Companion Companion = null;
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
    
    private final void setupRecyclerView() {
    }
    
    private final void setupToolbar() {
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onCreateOptionsMenu(@org.jetbrains.annotations.NotNull()
    android.view.Menu menu, @org.jetbrains.annotations.NotNull()
    android.view.MenuInflater inflater) {
    }
    
    @java.lang.Override()
    public void onRequestSubmitted(@org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.Nullable()
    java.lang.String backup) {
    }
    
    @java.lang.Override()
    public void onRequestDismissed() {
    }
    
    @java.lang.Override()
    public void onItemClicked(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem searchResultItem) {
    }
    
    public SearchFeedsFragment() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/fragment/SearchFeedsFragment$Companion;", "", "()V", "ARG_INITIAL_QUERY", "", "newInstance", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/SearchFeedsFragment;", "initialQuery", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.joshuacerdenia.android.nicefeed.ui.fragment.SearchFeedsFragment newInstance(@org.jetbrains.annotations.Nullable()
        java.lang.String initialQuery) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}