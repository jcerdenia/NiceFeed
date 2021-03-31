package com.joshuacerdenia.android.nicefeed.ui.fragment;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u00d6\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 c2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u00062\u00020\u00072\u00020\b:\u0002bcB\u0005\u00a2\u0006\u0002\u0010\tJ\u0018\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020\'H\u0002J\b\u0010)\u001a\u00020*H\u0002J\b\u0010+\u001a\u00020*H\u0002J\b\u0010,\u001a\u00020*H\u0002J\b\u0010-\u001a\u00020*H\u0002J\b\u0010.\u001a\u00020*H\u0002J\u0010\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020*H\u0016J\u0010\u00102\u001a\u0002002\u0006\u00103\u001a\u000204H\u0016J\u0012\u00105\u001a\u0002002\b\u00106\u001a\u0004\u0018\u000107H\u0016J\u0018\u00108\u001a\u0002002\u0006\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<H\u0016J&\u0010=\u001a\u0004\u0018\u00010>2\u0006\u0010;\u001a\u00020?2\b\u0010@\u001a\u0004\u0018\u00010A2\b\u00106\u001a\u0004\u0018\u000107H\u0016J\b\u0010B\u001a\u000200H\u0016J\u0010\u0010C\u001a\u0002002\u0006\u0010D\u001a\u00020EH\u0016J\u001a\u0010F\u001a\u0002002\u0006\u0010G\u001a\u00020*2\b\u0010H\u001a\u0004\u0018\u00010EH\u0016J\b\u0010I\u001a\u000200H\u0016J \u0010J\u001a\u0002002\u0006\u0010K\u001a\u00020E2\u0006\u0010D\u001a\u00020E2\u0006\u0010L\u001a\u00020*H\u0016J\u0018\u0010M\u001a\u0002002\u0006\u0010N\u001a\u00020O2\u0006\u00101\u001a\u00020*H\u0016J\u0010\u0010P\u001a\u00020*2\u0006\u0010Q\u001a\u00020\u001bH\u0016J\u0010\u0010R\u001a\u0002002\u0006\u0010S\u001a\u00020\'H\u0016J\b\u0010T\u001a\u000200H\u0016J\b\u0010U\u001a\u000200H\u0016J\b\u0010V\u001a\u000200H\u0016J\u001a\u0010W\u001a\u0002002\u0006\u0010X\u001a\u00020>2\b\u00106\u001a\u0004\u0018\u000107H\u0016J\b\u0010Y\u001a\u000200H\u0002J\b\u0010Z\u001a\u000200H\u0002J\u0018\u0010[\u001a\u0002002\u0006\u0010D\u001a\u00020E2\u0006\u0010\\\u001a\u00020\'H\u0002J\u001e\u0010]\u001a\u0002002\b\b\u0002\u0010\\\u001a\u00020\'2\n\b\u0002\u0010K\u001a\u0004\u0018\u00010EH\u0002J\b\u0010^\u001a\u000200H\u0002J\u000e\u0010_\u001a\u0002002\u0006\u0010`\u001a\u00020aR\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006d"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/fragment/ManageFeedsFragment;", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/VisibleFragment;", "Lcom/joshuacerdenia/android/nicefeed/ui/dialog/EditCategoryFragment$Callbacks;", "Lcom/joshuacerdenia/android/nicefeed/ui/dialog/EditFeedFragment$Callback;", "Lcom/joshuacerdenia/android/nicefeed/ui/dialog/ConfirmActionFragment$OnRemoveConfirmed;", "Lcom/joshuacerdenia/android/nicefeed/ui/dialog/ConfirmActionFragment$OnExportConfirmed;", "Lcom/joshuacerdenia/android/nicefeed/ui/dialog/SortFeedManagerFragment$Callbacks;", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedManagerAdapter$ItemCheckBoxListener;", "Lcom/joshuacerdenia/android/nicefeed/util/OpmlExporter$ExportResultListener;", "()V", "adapter", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/FeedManagerAdapter;", "callbacks", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/ManageFeedsFragment$Callbacks;", "counterTextView", "Landroid/widget/TextView;", "emptyMessageTextView", "fragment", "handler", "Landroid/os/Handler;", "opmlExporter", "Lcom/joshuacerdenia/android/nicefeed/util/OpmlExporter;", "progressBar", "Landroid/widget/ProgressBar;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "searchItem", "Landroid/view/MenuItem;", "selectAllCheckBox", "Landroid/widget/CheckBox;", "speedDial", "Lcom/leinardi/android/speeddial/SpeedDialView;", "toolbar", "Landroidx/appcompat/widget/Toolbar;", "viewModel", "Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/ManageFeedsViewModel;", "defaultSpeedDialItem", "Lcom/leinardi/android/speeddial/SpeedDialActionItem;", "id", "", "iconRes", "handleEditSelected", "", "handleExportAll", "handleExportSelected", "handleRemoveSelected", "handleSortFeeds", "onAllItemsChecked", "", "isChecked", "onAttach", "context", "Landroid/content/Context;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "menu", "Landroid/view/Menu;", "inflater", "Landroid/view/MenuInflater;", "onCreateView", "Landroid/view/View;", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDetach", "onEditCategoryConfirmed", "category", "", "onExportAttempted", "isSuccessful", "fileName", "onExportConfirmed", "onFeedInfoSubmitted", "title", "isChanged", "onItemClicked", "feed", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/FeedManageable;", "onOptionsItemSelected", "item", "onOrderSelected", "order", "onRemoveConfirmed", "onStart", "onStop", "onViewCreated", "view", "resetSelection", "setupSpeedDial", "showFeedsCategorizedNotice", "count", "showFeedsRemovedNotice", "updateCounter", "writeOpml", "uri", "Landroid/net/Uri;", "Callbacks", "Companion", "app_debug"})
public final class ManageFeedsFragment extends com.joshuacerdenia.android.nicefeed.ui.fragment.VisibleFragment implements com.joshuacerdenia.android.nicefeed.ui.dialog.EditCategoryFragment.Callbacks, com.joshuacerdenia.android.nicefeed.ui.dialog.EditFeedFragment.Callback, com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmActionFragment.OnRemoveConfirmed, com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmActionFragment.OnExportConfirmed, com.joshuacerdenia.android.nicefeed.ui.dialog.SortFeedManagerFragment.Callbacks, com.joshuacerdenia.android.nicefeed.ui.adapter.FeedManagerAdapter.ItemCheckBoxListener, com.joshuacerdenia.android.nicefeed.util.OpmlExporter.ExportResultListener {
    private com.joshuacerdenia.android.nicefeed.ui.viewmodel.ManageFeedsViewModel viewModel;
    private androidx.appcompat.widget.Toolbar toolbar;
    private android.widget.ProgressBar progressBar;
    private android.widget.CheckBox selectAllCheckBox;
    private android.widget.TextView counterTextView;
    private android.widget.TextView emptyMessageTextView;
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private com.joshuacerdenia.android.nicefeed.ui.adapter.FeedManagerAdapter adapter;
    private com.leinardi.android.speeddial.SpeedDialView speedDial;
    private android.view.MenuItem searchItem;
    private com.joshuacerdenia.android.nicefeed.util.OpmlExporter opmlExporter;
    private com.joshuacerdenia.android.nicefeed.ui.fragment.ManageFeedsFragment.Callbacks callbacks;
    private final com.joshuacerdenia.android.nicefeed.ui.fragment.ManageFeedsFragment fragment = null;
    private final android.os.Handler handler = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.ui.fragment.ManageFeedsFragment.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public void onAttach(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
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
    
    @java.lang.Override()
    public void onStart() {
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
    
    private final void setupSpeedDial() {
    }
    
    private final com.leinardi.android.speeddial.SpeedDialActionItem defaultSpeedDialItem(int id, int iconRes) {
        return null;
    }
    
    private final void updateCounter() {
    }
    
    private final boolean handleEditSelected() {
        return false;
    }
    
    @java.lang.Override()
    public void onFeedInfoSubmitted(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String category, boolean isChanged) {
    }
    
    @java.lang.Override()
    public void onEditCategoryConfirmed(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
    }
    
    private final void showFeedsCategorizedNotice(java.lang.String category, int count) {
    }
    
    private final boolean handleRemoveSelected() {
        return false;
    }
    
    @java.lang.Override()
    public void onRemoveConfirmed() {
    }
    
    private final void showFeedsRemovedNotice(int count, java.lang.String title) {
    }
    
    private final boolean handleExportAll() {
        return false;
    }
    
    private final boolean handleExportSelected() {
        return false;
    }
    
    @java.lang.Override()
    public void onExportConfirmed() {
    }
    
    public final void writeOpml(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    @java.lang.Override()
    public void onExportAttempted(boolean isSuccessful, @org.jetbrains.annotations.Nullable()
    java.lang.String fileName) {
    }
    
    private final boolean handleSortFeeds() {
        return false;
    }
    
    @java.lang.Override()
    public void onOrderSelected(int order) {
    }
    
    private final void resetSelection() {
    }
    
    @java.lang.Override()
    public void onItemClicked(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable feed, boolean isChecked) {
    }
    
    @java.lang.Override()
    public void onAllItemsChecked(boolean isChecked) {
    }
    
    @java.lang.Override()
    public void onStop() {
    }
    
    @java.lang.Override()
    public void onDetach() {
    }
    
    public ManageFeedsFragment() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u00012\u00020\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/fragment/ManageFeedsFragment$Callbacks;", "Lcom/joshuacerdenia/android/nicefeed/ui/OnToolbarInflated;", "Lcom/joshuacerdenia/android/nicefeed/ui/OnFinished;", "onAddFeedsSelected", "", "onExportOpmlSelected", "app_debug"})
    public static abstract interface Callbacks extends com.joshuacerdenia.android.nicefeed.ui.OnToolbarInflated, com.joshuacerdenia.android.nicefeed.ui.OnFinished {
        
        public abstract void onAddFeedsSelected();
        
        public abstract void onExportOpmlSelected();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/fragment/ManageFeedsFragment$Companion;", "", "()V", "newInstance", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/ManageFeedsFragment;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.joshuacerdenia.android.nicefeed.ui.fragment.ManageFeedsFragment newInstance() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}