package com.joshuacerdenia.android.nicefeed.ui.fragment;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 82\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005:\u00018B\u0005\u00a2\u0006\u0002\u0010\u0006J\u0012\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J&\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010#\u001a\u00020\u001aH\u0016J\u0016\u0010$\u001a\u00020\u001a2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\'0&H\u0016J\b\u0010(\u001a\u00020\u001aH\u0016J\b\u0010)\u001a\u00020\u001aH\u0016J\u001a\u0010*\u001a\u00020\u001a2\u0006\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010,H\u0016J\b\u0010.\u001a\u00020\u001aH\u0016J\u0010\u0010/\u001a\u00020\u001a2\u0006\u00100\u001a\u00020,H\u0016J\u001a\u00101\u001a\u00020\u001a2\u0006\u00102\u001a\u00020\u001e2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u00103\u001a\u00020\u001aH\u0002J\b\u00104\u001a\u00020\u001aH\u0002J\u000e\u00105\u001a\u00020\u001a2\u0006\u00106\u001a\u000207R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u00069"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/fragment/AddFeedsFragment;", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedAddingFragment;", "Lcom/joshuacerdenia/android/nicefeed/util/OpmlImporter$OnOpmlParsedListener;", "Lcom/joshuacerdenia/android/nicefeed/ui/dialog/ConfirmActionFragment$OnImportConfirmed;", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/TopicAdapter$OnItemClickListener;", "Lcom/joshuacerdenia/android/nicefeed/ui/FeedRequestCallbacks;", "()V", "adapter", "Lcom/joshuacerdenia/android/nicefeed/ui/adapter/TopicAdapter;", "addUrlTextView", "Landroid/widget/TextView;", "fragment", "importOpmlTextView", "linearLayout", "Landroid/widget/LinearLayout;", "opmlImporter", "Lcom/joshuacerdenia/android/nicefeed/util/OpmlImporter;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "searchView", "Landroidx/appcompat/widget/SearchView;", "toolbar", "Landroidx/appcompat/widget/Toolbar;", "viewModel", "Lcom/joshuacerdenia/android/nicefeed/ui/viewmodel/AddFeedsViewModel;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onImportConfirmed", "onOpmlParsed", "feeds", "", "Lcom/joshuacerdenia/android/nicefeed/data/model/feed/Feed;", "onParseOpmlFailed", "onRequestDismissed", "onRequestSubmitted", "url", "", "backup", "onStart", "onTopicSelected", "topic", "onViewCreated", "view", "setupRecyclerView", "setupToolbar", "submitUriForImport", "uri", "Landroid/net/Uri;", "Companion", "app_debug"})
public final class AddFeedsFragment extends com.joshuacerdenia.android.nicefeed.ui.fragment.FeedAddingFragment implements com.joshuacerdenia.android.nicefeed.util.OpmlImporter.OnOpmlParsedListener, com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmActionFragment.OnImportConfirmed, com.joshuacerdenia.android.nicefeed.ui.adapter.TopicAdapter.OnItemClickListener, com.joshuacerdenia.android.nicefeed.ui.FeedRequestCallbacks {
    private com.joshuacerdenia.android.nicefeed.ui.viewmodel.AddFeedsViewModel viewModel;
    private androidx.appcompat.widget.Toolbar toolbar;
    private android.widget.LinearLayout linearLayout;
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private com.joshuacerdenia.android.nicefeed.ui.adapter.TopicAdapter adapter;
    private android.widget.TextView addUrlTextView;
    private android.widget.TextView importOpmlTextView;
    private androidx.appcompat.widget.SearchView searchView;
    private final com.joshuacerdenia.android.nicefeed.ui.fragment.AddFeedsFragment fragment = null;
    private com.joshuacerdenia.android.nicefeed.util.OpmlImporter opmlImporter;
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.ui.fragment.AddFeedsFragment.Companion Companion = null;
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
    public void onStart() {
    }
    
    @java.lang.Override()
    public void onRequestSubmitted(@org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.Nullable()
    java.lang.String backup) {
    }
    
    @java.lang.Override()
    public void onRequestDismissed() {
    }
    
    public final void submitUriForImport(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    @java.lang.Override()
    public void onOpmlParsed(@org.jetbrains.annotations.NotNull()
    java.util.List<com.joshuacerdenia.android.nicefeed.data.model.feed.Feed> feeds) {
    }
    
    @java.lang.Override()
    public void onParseOpmlFailed() {
    }
    
    @java.lang.Override()
    public void onImportConfirmed() {
    }
    
    @java.lang.Override()
    public void onTopicSelected(@org.jetbrains.annotations.NotNull()
    java.lang.String topic) {
    }
    
    public AddFeedsFragment() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/fragment/AddFeedsFragment$Companion;", "", "()V", "newInstance", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/AddFeedsFragment;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.joshuacerdenia.android.nicefeed.ui.fragment.AddFeedsFragment newInstance() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}