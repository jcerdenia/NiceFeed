package com.joshuacerdenia.android.nicefeed.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 $2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0001$B\u0005\u00a2\u0006\u0002\u0010\u0005J\n\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\"\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\b\u0010\u000f\u001a\u00020\tH\u0016J\u0012\u0010\u0010\u001a\u00020\t2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\b\u0010\u0013\u001a\u00020\tH\u0016J\b\u0010\u0014\u001a\u00020\tH\u0016J\b\u0010\u0015\u001a\u00020\tH\u0016J\u0010\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u0018H\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001cH\u0016J\u001a\u0010!\u001a\u00020\t2\u0006\u0010\"\u001a\u00020\u00072\b\b\u0002\u0010#\u001a\u00020\u001cH\u0002\u00a8\u0006%"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/activity/ManagingActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/ManageFeedsFragment$Callbacks;", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedAddingFragment$Callbacks;", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/SettingsFragment$Callbacks;", "()V", "getCurrentFragment", "Landroidx/fragment/app/Fragment;", "onActivityResult", "", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onAddFeedsSelected", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onExportOpmlSelected", "onFinished", "onImportOpmlSelected", "onNewFeedAdded", "feedId", "", "onQuerySubmitted", "query", "onSupportNavigateUp", "", "onToolbarInflated", "toolbar", "Landroidx/appcompat/widget/Toolbar;", "isNavigableUp", "replaceFragment", "fragment", "addToBackStack", "Companion", "app_debug"})
public final class ManagingActivity extends androidx.appcompat.app.AppCompatActivity implements com.joshuacerdenia.android.nicefeed.ui.fragment.ManageFeedsFragment.Callbacks, com.joshuacerdenia.android.nicefeed.ui.fragment.FeedAddingFragment.Callbacks, com.joshuacerdenia.android.nicefeed.ui.fragment.SettingsFragment.Callbacks {
    private static final java.lang.String EXTRA_MANAGING = "com.joshuacerdenia.android.nicefeed.managing";
    private static final int REQUEST_CODE_READ_OPML = 0;
    private static final int REQUEST_CODE_WRITE_OPML = 1;
    private static final java.lang.String OPML_DOC_TYPE = "*/*";
    private static final java.lang.String OPML_FILE_PREFIX = "NiceFeed_";
    private static final java.lang.String OPML_FILE_EXT = ".opml";
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.ui.activity.ManagingActivity.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    @java.lang.Override()
    public void onNewFeedAdded(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId) {
    }
    
    @java.lang.Override()
    public void onQuerySubmitted(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    @java.lang.Override()
    public void onImportOpmlSelected() {
    }
    
    @java.lang.Override()
    public void onAddFeedsSelected() {
    }
    
    @java.lang.Override()
    public void onExportOpmlSelected() {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    @java.lang.Override()
    public void onToolbarInflated(@org.jetbrains.annotations.NotNull()
    androidx.appcompat.widget.Toolbar toolbar, boolean isNavigableUp) {
    }
    
    @java.lang.Override()
    public void onFinished() {
    }
    
    private final androidx.fragment.app.Fragment getCurrentFragment() {
        return null;
    }
    
    private final void replaceFragment(androidx.fragment.app.Fragment fragment, boolean addToBackStack) {
    }
    
    public ManagingActivity() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/activity/ManagingActivity$Companion;", "", "()V", "EXTRA_MANAGING", "", "OPML_DOC_TYPE", "OPML_FILE_EXT", "OPML_FILE_PREFIX", "REQUEST_CODE_READ_OPML", "", "REQUEST_CODE_WRITE_OPML", "newIntent", "Landroid/content/Intent;", "packageContext", "Landroid/content/Context;", "item", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final android.content.Intent newIntent(@org.jetbrains.annotations.NotNull()
        android.content.Context packageContext, int item) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}