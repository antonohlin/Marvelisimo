package com.example.marvelisimo.model

import android.os.Parcelable
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
    val extension: String,
    val description: String?,
) : Parcelable
//TODO lägg till flera url till serie-bilder

@Parcelize
data class Image(
    val path:String,
    val extension:String
) : Parcelable
