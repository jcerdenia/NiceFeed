package com.joshuacerdenia.android.nicefeed.data.local.database;

import java.lang.System;

@androidx.room.TypeConverters(value = {com.joshuacerdenia.android.nicefeed.data.local.database.TypeConverters.class})
@androidx.room.Database(entities = {com.joshuacerdenia.android.nicefeed.data.model.feed.Feed.class, com.joshuacerdenia.android.nicefeed.data.model.entry.Entry.class, com.joshuacerdenia.android.nicefeed.data.model.cross.FeedEntryCrossRef.class}, version = 1)
@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/data/local/database/NiceFeedDatabase;", "Landroidx/room/RoomDatabase;", "()V", "combinedDao", "Lcom/joshuacerdenia/android/nicefeed/data/local/database/CombinedDao;", "Companion", "app_debug"})
public abstract class NiceFeedDatabase extends androidx.room.RoomDatabase {
    private static final java.lang.String DATABASE_NAME = "database";
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.data.local.database.NiceFeedDatabase.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.joshuacerdenia.android.nicefeed.data.local.database.CombinedDao combinedDao();
    
    public NiceFeedDatabase() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/data/local/database/NiceFeedDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "build", "Lcom/joshuacerdenia/android/nicefeed/data/local/database/NiceFeedDatabase;", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.joshuacerdenia.android.nicefeed.data.local.database.NiceFeedDatabase build(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}