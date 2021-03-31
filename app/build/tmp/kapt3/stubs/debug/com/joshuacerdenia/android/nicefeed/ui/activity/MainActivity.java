package com.joshuacerdenia.android.nicefeed.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 42\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005:\u00014B\u0005\u00a2\u0006\u0002\u0010\u0006J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001a\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0002J\u0018\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\nH\u0002J\"\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\u0013\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00100\u001cH\u0016\u00a2\u0006\u0002\u0010\u001dJ\u0012\u0010\u001e\u001a\u00020\u000e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J\u0010\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u0010H\u0016J\u0010\u0010#\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010$\u001a\u00020\u000eH\u0016J\u001a\u0010%\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010&\u001a\u0004\u0018\u00010\u0010H\u0016J\b\u0010\'\u001a\u00020\u000eH\u0016J\u0010\u0010(\u001a\u00020\u000e2\u0006\u0010)\u001a\u00020\fH\u0016J\u0012\u0010*\u001a\u00020\u000e2\b\u0010+\u001a\u0004\u0018\u00010\u001aH\u0014J\b\u0010,\u001a\u00020\u0012H\u0016J\u0018\u0010-\u001a\u00020\u000e2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u0012H\u0016J\u0018\u00101\u001a\u00020\u000e2\u0006\u00102\u001a\u00020\n2\u0006\u00103\u001a\u00020\u0012H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u00065"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/activity/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/FeedListFragment$Callbacks;", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/EntryListFragment$Callbacks;", "Lcom/joshuacerdenia/android/nicefeed/ui/fragment/EntryFragment$Callbacks;", "Lcom/joshuacerdenia/android/nicefeed/ui/OnHomePressed;", "()V", "drawerLayout", "Landroidx/drawerlayout/widget/DrawerLayout;", "getFragment", "Landroidx/fragment/app/Fragment;", "code", "", "loadFeed", "", "feedId", "", "blockAutoUpdate", "", "loadFragments", "main", "navigation", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCategoriesNeeded", "", "()[Ljava/lang/String;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onEntrySelected", "entryId", "onFeedLoaded", "onFeedRemoved", "onFeedSelected", "activeFeedId", "onHomePressed", "onMenuItemSelected", "item", "onNewIntent", "intent", "onSupportNavigateUp", "onToolbarInflated", "toolbar", "Landroidx/appcompat/widget/Toolbar;", "isNavigableUp", "replaceMainFragment", "newFragment", "addToBackStack", "Companion", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity implements com.joshuacerdenia.android.nicefeed.ui.fragment.FeedListFragment.Callbacks, com.joshuacerdenia.android.nicefeed.ui.fragment.EntryListFragment.Callbacks, com.joshuacerdenia.android.nicefeed.ui.fragment.EntryFragment.Callbacks, com.joshuacerdenia.android.nicefeed.ui.OnHomePressed {
    private androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    private static final int REQUEST_CODE_ADD_FEED = 0;
    private static final int FRAGMENT_MAIN = 0;
    private static final int FRAGMENT_NAVIGATION = 1;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_FEED_ID = "com.joshuacerdenia.android.nicefeed.feed_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_ENTRY_ID = "com.joshuacerdenia.android.nicefeed.entry_id";
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.ui.activity.MainActivity.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onNewIntent(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    private final void loadFragments(androidx.fragment.app.Fragment main, androidx.fragment.app.Fragment navigation) {
    }
    
    private final void replaceMainFragment(androidx.fragment.app.Fragment newFragment, boolean addToBackStack) {
    }
    
    private final androidx.fragment.app.Fragment getFragment(int code) {
        return null;
    }
    
    @java.lang.Override()
    public void onToolbarInflated(@org.jetbrains.annotations.NotNull()
    androidx.appcompat.widget.Toolbar toolbar, boolean isNavigableUp) {
    }
    
    @java.lang.Override()
    public void onHomePressed() {
    }
    
    @java.lang.Override()
    public void onMenuItemSelected(int item) {
    }
    
    @java.lang.Override()
    public void onFeedSelected(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId, @org.jetbrains.annotations.Nullable()
    java.lang.String activeFeedId) {
    }
    
    private final void loadFeed(java.lang.String feedId, boolean blockAutoUpdate) {
    }
    
    @java.lang.Override()
    public void onFeedLoaded(@org.jetbrains.annotations.NotNull()
    java.lang.String feedId) {
    }
    
    @java.lang.Override()
    public void onFeedRemoved() {
    }
    
    @java.lang.Override()
    public void onEntrySelected(@org.jetbrains.annotations.NotNull()
    java.lang.String entryId) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String[] onCategoriesNeeded() {
        return null;
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    public MainActivity() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/activity/MainActivity$Companion;", "", "()V", "EXTRA_ENTRY_ID", "", "EXTRA_FEED_ID", "FRAGMENT_MAIN", "", "FRAGMENT_NAVIGATION", "REQUEST_CODE_ADD_FEED", "newIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "feedId", "latestEntryId", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final android.content.Intent newIntent(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        java.lang.String feedId, @org.jetbrains.annotations.NotNull()
        java.lang.String latestEntryId) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}