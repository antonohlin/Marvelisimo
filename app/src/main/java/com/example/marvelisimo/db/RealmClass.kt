package com.example.marvelisimo.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class RealmMarvelItem (
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var description: String? = "",
    var imageUrlBase: String = "",
    var extension: String = "",
    var url: String? = ""
): RealmObject() {

    val imageUrl: String
        get() = imageUrl + "." + extension
}