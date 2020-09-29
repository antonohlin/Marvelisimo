package com.example.marvelisimo.db

import com.example.marvelisimo.model.MarvelItem
import io.realm.Realm

class DbHelper (

    val realm: Realm = Realm.getDefaultInstance()
) {

    fun saveToDb(marvelItem: MarvelItem) {
        val marvelItemToDb = RealmMarvelItem()
        marvelItemToDb.id = marvelItem.id
        marvelItemToDb.name = marvelItem.title
        marvelItemToDb.imageUrlBase = marvelItem.imageUrlBase
        marvelItemToDb.url = marvelItem.url
        marvelItemToDb.description = marvelItem.description
        realm.beginTransaction()
        realm.copyToRealm(marvelItemToDb)
        realm.commitTransaction()
    }

}