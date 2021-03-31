package com.joshuacerdenia.android.nicefeed.util;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\tH\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/util/EntryToHtmlFormatter;", "", "textSizeKey", "", "font", "includeHeader", "", "(IIZ)V", "fontFamily", "", "style", "subtitle", "textSize", "title", "getHtml", "entry", "Lcom/joshuacerdenia/android/nicefeed/data/model/entry/EntryMinimal;", "removeStyle", "content", "Companion", "app_debug"})
public final class EntryToHtmlFormatter {
    private final java.lang.String fontFamily = null;
    private final java.lang.String textSize = null;
    private final java.lang.String style = null;
    private java.lang.String title = "";
    private java.lang.String subtitle = "";
    private final boolean includeHeader = false;
    private static final java.lang.String OPEN_STYLE_TAG = "<style>";
    private static final java.lang.String CLOSE_STYLE_TAG = "</style>";
    private static final java.lang.String LINK_COLOR = "#444E64";
    private static final java.lang.String ID_SUBTITLE = "id=\"subtitle\"";
    @org.jetbrains.annotations.NotNull()
    public static final com.joshuacerdenia.android.nicefeed.util.EntryToHtmlFormatter.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHtml(@org.jetbrains.annotations.NotNull()
    com.joshuacerdenia.android.nicefeed.data.model.entry.EntryMinimal entry) {
        return null;
    }
    
    private final java.lang.String removeStyle(java.lang.String content) {
        return null;
    }
    
    public EntryToHtmlFormatter(int textSizeKey, int font, boolean includeHeader) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/joshuacerdenia/android/nicefeed/util/EntryToHtmlFormatter$Companion;", "", "()V", "CLOSE_STYLE_TAG", "", "ID_SUBTITLE", "LINK_COLOR", "OPEN_STYLE_TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}