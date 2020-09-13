package com.joshuacerdenia.android.nicefeed.ui

import androidx.appcompat.widget.Toolbar

interface ToolbarCallbacks {

    fun onToolbarInflated(toolbar: Toolbar, isNavigableUp: Boolean = true)
}