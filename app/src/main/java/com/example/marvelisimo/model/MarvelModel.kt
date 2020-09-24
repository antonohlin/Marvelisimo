package com.example.marvelisimo.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class MarvelItemDataWrapper(
    val code: Int,
    val status: String,
    val data: MarvelItemDataContainer
)

data class MarvelItemDataContainer(
    val total: Int,
    val count: Int,
    val results: Array<MarvelItem>
)

@Parcelize
data class MarvelItem(
    val title: String,
    val imageUrl: Image,
    val description: String = "",
) : Parcelable

//TODO lägg till flera url till serie-bilder

@Parcelize
data class Image(
    val path: String,
    val extension: String
) : Parcelable
