package com.joshuacerdenia.android.nicefeed.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchResultItem(
    val title: String?,
    @SerializedName("feedId") val id: String?,
    val website: String?,
    val description: String?,
    val updated: String?,
    @SerializedName("visualUrl") val imageUrl: String?
): Serializable