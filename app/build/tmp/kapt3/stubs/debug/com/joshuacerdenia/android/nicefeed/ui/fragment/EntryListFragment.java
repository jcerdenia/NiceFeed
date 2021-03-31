package com.joshuacerdenia.android.nicefeed.ui.fragment;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u00b8\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0011\u0018\u0000 \\2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u0006:\u0002[\\B\u0005\u00a2\u0006\u0002\u0010\u0007J\u0014\u0010\"\u001a\u00020\u000b2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u000fH\u0002J\b\u0010$\u001a\u00020\u000bH\u0002J\b\u0010%\u001a\u00020\u000bH\u0002J\b\u0010&\u001a\u00020\u000bH\u0002J\u0012\u0010\'\u001a\u00020\u000b2\b\u0010(\u001a\u0004\u0018\u00010)H\u0002J\b\u0010*\u001a\u00020\u000bH\u0002J\u0012\u0010+\u001a\u00020\u000b2\b\u0010,\u001a\u0004\u0018\u00010\u000fH\u0002J\b\u0010-\u001a\u00020.H\u0002J\u0010\u0010/\u001a\u00020.2\u0006\u00100\u001a\u000201H\u0016J\u0012\u00102\u001a\u00020.2\b\u00103\u001a\u0004\u0018\u000104H\u0016J\u0018\u00105\u001a\u00020.2\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u000209H\u0016J&\u0010:\u001a\u0004\u0018\u00010;2\u0006\u00108\u001a\u00020<2\b\u0010=\u001a\u0004\u0018\u00010>2\b\u00103\u001a\u0004\u0018\u000104H\u0016J\b\u0010?\u001a\u00020.H\u0016J\u001a\u0010@\u001a\u00020.2\u0006\u0010A\u001a\u00020\u000f2\b\u0010B\u001a\u0004\u0018\u00010;H\u0016J\u001a\u0010C\u001a\u00020.2\u0006\u0010D\u001a\u00020E2\b\u0010B\u001a\u0004\u0018\u00010;H\u0016J \u0010F\u001a\u00020.2\u0006\u0010G\u001a\u00020\u000f2\u0006\u0010H\u001a\u00020\u000f2\u0006\u0010I\u001a\u00020\u000bH\u0016J\u0010\u0010J\u001a\u00020.2\u0006\u0010K\u001a\u00020LH\u0016J\u0010\u0010M\u001a\u00020\u000b2\u0006\u0010N\u001a\u00020\u0014H\u0016J\u0018\u0010O\u001a\u00020.2\u0006\u0010D\u001a\u00020E2\u0006\u0010P\u001a\u00020LH\u0016J\b\u0010Q\u001a\u00020.H\u0016J\b\u0010R\u001a\u00020.H\u0016J\b\u0010S\u001a\u00020.H\u0016J\b\u0010T\u001a\u00020.H\u0016J\u001a\u0010U\u001a\u00020.2\u0006\u0010B\u001a\u00020;2\b\u00103\u001a\u0004\u0018\u000104H\u0016J\b\u0010V\u001a\u00020.H\u0002J\b\u0010W\u001a\u00020.H\u0002J\b\u0010X\u001a\u00020.H\u0002J\b\u0010Y\u001a\u00020.H\u0002J\b\u0010Z\u001a\u00020.H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006]"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/fragment/EntryListFragment;", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/VisibleFragment;", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/EntryListAdapter$OnEntrySelected;", "Lcom/joshuacerdenia/android/nicefeed/ui/menu/EntryPopupMenu$OnPopupMenuItemClicked;", "Lcom/joshuacerdenia/android/nicefeed/ui/dialog/FilterEntriesFragment$Callbacks;", "Lcom/joshuacerdenia/android/nicefeed/ui/dialog/EditFeedFragment$Callback;", "Lcom/joshuacerdenia/android/nicefeed/ui/dialog/ConfirmActionFragment$OnRemoveConfirmed;", "()V", "adapter", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/EntryListAdapter;", "autoUpdateOnLaunch", "", "callbacks", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/EntryListFragment$Callbacks;", "feedId", "", "fragment", "handler", "Landroid/os/Handler;", "markAllOptionsItem", "Landroid/view/MenuItem;", "masterProgressBar", "Landroid/widget/ProgressBar;", "noItemsTextView", "Landroid/widget/TextView;", "progressBar", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "searchItem", "starAllOptionsItem", "toolbar", "Landroidx/appcompat/widget/Toolbar;", "viewModel", "Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/EntryListViewModel;", "handleCheckForUpdates", "url", "handleFilter", "handleMarkAll", "handleRemoveFeed", "handleShowFeedInfo", "feed", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/Feed;", "handleStarAll", "handleVisitWebsite", "website", "loadEntryOnStart", "", "onAttach", "context", "Landroid/content/Context;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "menu", "Landroid/view/Menu;", "inflater", "Landroid/view/MenuInflater;", "onCreateView", "Landroid/view/View;", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDetach", "onEntryClicked", "entryId", "view", "onEntryLongClicked", "entry", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryLight;", "onFeedInfoSubmitted", "title", "category", "isChanged", "onFilterSelected", "filter", "", "onOptionsItemSelected", "item", "onPopupMenuItemClicked", "action", "onRemoveConfirmed", "onResume", "onStart", "onStop", "onViewCreated", "restoreToolbar", "setupRecyclerView", "setupToolbar", "showUpdateNotice", "toggleOptionsItems", "Callbacks", "Companion", "app_debug"})
public final class EntryListFragment extends com.joshuacerdenia.android.nicefeed.ui.fragment.VisibleFragment implements com.joshuacerdenia.android.nicefeed.ui.adapter.EntryListAdapter.OnEntrySelected, com.joshuacerdenia.android.nicefeed.ui.menu.EntryPopupMenu.OnPopupMenuItemClicked, com.joshuacerdenia.android.nicefeed.ui.dialog.FilterEntriesFragment.Callbacks, com.joshuacerdenia.android.nicefeed.ui.dialog.EditFeedFragment.Callback, com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmActionFragment.OnRemoveConfirmed {
    private com.joshuacerdenia.android.nicefeed.ui.viewmodel.EntryListViewModel viewModel;
    private androidx.appcompat.widget.Toolbar toolbar;
    private android.widget.TextView noItemsTextView;
    private android.widget.ProgressBar masterProgressBar;
    private android.widget.ProgressBar progressBar;
    private android.view.MenuItem searchItem;
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private com.joshuacerdenia.android.nicefeed.ui.adapter.EntryListAdapter adapter;
    private android.view.MenuItem markAllOptionsItem;
    private android.view.MenuItem starAllOptionsItem;
    private boolean autoUpdateOnLaunch = true;
    private java.lang.String feedId;
    private com.joshuacerdenia.android.nicefeed.ui.fragment.EntryListFragment.Callbacks callbacks;
    private final android.os.Handler handler = null;
    private final com.joshuacerdenia.android.nicefeed.ui.fragment.EntryListFragment fragment = null;
    private static final java.lang.String TAG = "EntryListFragment";
    private static final java.lang.String ARG_FEED_ID = "ARG_FEED_ID";
    private static final java.lang.String ARG_ENTRY_ID = "ARG_ENTRY_ID";
    private static final java.lang.String ARG_BLOCK_AUTO_UPDATE = "ARG_BLOCK_AUTO_UPDATE";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FOLDER = "FOLDER";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FOLDER_NEW = "FOLDER_NEW";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FOLDER_STARRED = "FOLDER_STARRED";
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.ui.fragment.EntryListFragment.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public void onAttach(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadEntryOnStart() {
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
    public void onStart() {
    }
    
    private final void restoreToolbar() {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onCreateOptionsMenu(@org.jetbrains.annotations.NotNull()
    android.view.Menu menu, @org.jetbrains.annotations.NotNull()
    android.view.MenuInflater inflater) {
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    private final void toggleOptionsItems() {
    }
    
    private final boolean handleCheckForUpdates(java.lang.String url) {
        return false;
    }
    
    private final void showUpdateNotice() {
    }
    
    private final boolean handleShowFeedInfo(com.joshuacerdenia.android.nicefeed.data.model.feed.Feed feed) {
        return false;
    }
    
    @java.lang.Override()
    public void onFeedInfoSubmitted(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String category, boolean isChanged) {
    }
    
    private final boolean handleFilter() {
        return false;
    }
    
    private final boolean handleMarkAll() {
        return false;
    }
    
    private final boolean handleStarAll() {
        return false;
    }
    
    private final boolean handleVisitWebsite(java.lang.String website) {
        return false;
    }
    
    private final boolean handleRemoveFeed() {
        return false;
    }
    
    @java.lang.Override()
    public void onRemoveConfirmed() {
    }
    
    @java.lang.Override()
    public void onEntryClicked(@org.jetbrains.annotations.NotNull()
    java.lang.String entryId, @org.jetbrains.annotations.Nullable()
    android.view.View view) {
    }
    
    @java.lang.Override()
    public void onEntryLongClicked(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight entry, @org.jetbrains.annotations.Nullable()
    android.view.View view) {
    }
    
    @java.lang.Override()
    public void onPopupMenuItemClicked(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight entry, int action) {
    }
    
    @java.lang.Override()
    public void onFilterSelected(int filter) {
    }
    
    @java.lang.Override()
    public void onStop() {
    }
    
    @java.lang.Override()
    public void onDetach() {
    }
    
    public EntryListFragment() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u00012\u00020\u0002J\u0013\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H&J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0005H&J\b\u0010\f\u001a\u00020\bH&\u00a8\u0006\r"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/fragment/EntryListFragment$Callbacks;", "Lcom/joshuacerdenia/android/nicefeed/ui/OnHomePressed;", "Lcom/joshuacerdenia/android/nicefeed/ui/OnToolbarInflated;", "onCategoriesNeeded", "", "", "()[Ljava/lang/String;", "onEntrySelected", "", "entryId", "onFeedLoaded", "feedId", "onFeedRemoved", "app_debug"})
    public static abstract interface Callbacks extends com.joshuacerdenia.android.nicefeed.ui.OnHomePressed, com.joshuacerdenia.android.nicefeed.ui.OnToolbarInflated {
        
        public abstract void onFeedLoaded(@org.jetbrains.annotations.NotNull()
        java.lang.String feedId);
        
        public abstract void onEntrySelected(@org.jetbrains.annotations.NotNull()
        java.lang.String entryId);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String[] onCategoriesNeeded();
        
        public abstract void onFeedRemoved();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u000f\u001a\u00020\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/fragment/EntryListFragment$Companion;", "", "()V", "ARG_BLOCK_AUTO_UPDATE", "", "ARG_ENTRY_ID", "ARG_FEED_ID", "FOLDER", "FOLDER_NEW", "FOLDER_STARRED", "TAG", "newInstance", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/EntryListFragment;", "feedId", "entryId", "blockAutoUpdate", "", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.joshuacerdenia.android.nicefeed.ui.fragment.EntryListFragment newInstance(@org.jetbrains.annotations.Nullable()
        java.lang.String feedId, @org.jetbrains.annotations.Nullable()
        java.lang.String entryId, boolean blockAutoUpdate) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}