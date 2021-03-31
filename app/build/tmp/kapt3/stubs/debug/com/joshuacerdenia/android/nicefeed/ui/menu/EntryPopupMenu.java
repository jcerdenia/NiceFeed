package com.joshuacerdenia.android.nicefeed.ui.menu;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u000b2\u00020\u0001:\u0002\u000b\fB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/menu/EntryPopupMenu;", "Landroidx/appcompat/widget/PopupMenu;", "context", "Landroid/content/Context;", "view", "Landroid/view/View;", "listener", "Lcom/joshuacerdenia/android/nicefeed/ui/menu/EntryPopupMenu$OnPopupMenuItemClicked;", "entry", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryLight;", "(Landroid/content/Context;Landroid/view/View;Lcom/joshuacerdenia/android/nicefeed/ui/menu/EntryPopupMenu$OnPopupMenuItemClicked;Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryLight;)V", "Companion", "OnPopupMenuItemClicked", "app_debug"})
public final class EntryPopupMenu extends androidx.appcompat.widget.PopupMenu {
    private final com.joshuacerdenia.android.nicefeed.ui.menu.EntryPopupMenu.OnPopupMenuItemClicked listener = null;
    private final com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight entry = null;
    public static final int ACTION_MARK_AS = 0;
    public static final int ACTION_STAR = 1;
    public static final int ACTION_OPEN = 2;
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.ui.menu.EntryPopupMenu.Companion Companion = null;
    
    public EntryPopupMenu(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.ui.menu.EntryPopupMenu.OnPopupMenuItemClicked listener, @org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight entry) {
        super(null, null);
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&\u00a8\u0006\b"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/menu/EntryPopupMenu$OnPopupMenuItemClicked;", "", "onPopupMenuItemClicked", "", "entry", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryLight;", "action", "", "app_debug"})
    public static abstract interface OnPopupMenuItemClicked {
        
        public abstract void onPopupMenuItemClicked(@org.jetbrains.annotations.NotNull()
        com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight entry, int action);
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/ui/menu/EntryPopupMenu$Companion;", "", "()V", "ACTION_MARK_AS", "", "ACTION_OPEN", "ACTION_STAR", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}