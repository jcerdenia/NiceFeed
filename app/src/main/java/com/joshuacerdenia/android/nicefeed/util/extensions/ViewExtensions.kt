package com.joshuacerdenia.android.nicefeed.util.extensions

import android.util.TypedValue
import android.view.View

fun View.addRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}

fun View.hide() = this.apply { visibility = View.GONE }

fun View.show() = this.apply { visibility = View.VISIBLE }

fun View.isVisible() = this.visibility == View.VISIBLE