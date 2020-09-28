package com.example.marvelisimo.db

import android.util.Log
import com.example.marvelisimo.model.MarvelItem
import io.realm.Realm

class DbHelper (

    val realm: Realm = Realm.getDefaultInstance()
) {

    fun saveToDb(marvelItem: MarvelItem) {
        val marvelItemToDb = RealmMarvelItem()
        Log.i("SavetoDB", marvelItem.title + marvelItem.imageUrl)

        marvelItemToDb.name = marvelItem.title
        marvelItemToDb.imageUrlBase = marvelItem.imageUrlBase

        realm.beginTransaction()
        realm.copyToRealm(marvelItemToDb)
        realm.commitTransaction()
    }

}