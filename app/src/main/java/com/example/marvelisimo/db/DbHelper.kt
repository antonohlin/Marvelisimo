package com.example.marvelisimo.db

import com.example.marvelisimo.model.MarvelItem
import io.realm.Realm

class DbHelper (

    val realm: Realm = Realm.getDefaultInstance()
) {

    fun saveToDb(marvelItem: MarvelItem, url: String) {
        val marvelItemToDb = RealmMarvelItem()

        marvelItemToDb.name = marvelItem.title
        marvelItemToDb.imageUrl = url

        realm.beginTransaction()
        realm.copyToRealm(marvelItemToDb)
        realm.commitTransaction()
    }

}