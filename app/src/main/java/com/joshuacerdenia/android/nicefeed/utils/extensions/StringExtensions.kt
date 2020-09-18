package com.joshuacerdenia.android.nicefeed.utils.extensions

fun String.pathified() = this.substringAfter(
    "www.",
    this.substringAfter("://")
).removeSuffix("/")

fun String.simplified() = this.pathified().substringBefore("?")

fun String.shortened() = this.simplified().substringBefore("/")