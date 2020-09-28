package com.example.marvelisimo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarvelItem(
    val title: String,
    val imageUrl: String,
    val extension: String,
    val description: String?,
    val url: String?
) : Parcelable