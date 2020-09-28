package com.example.marvelisimo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarvelItem(
    val id: Int,
    val title: String,
    val imageUrlBase: String,
    val extension: String,
    val description: String?,
    val url: String?
) : Parcelable {

    val imageUrl:String
        get()=imageUrlBase+"."+extension

}

