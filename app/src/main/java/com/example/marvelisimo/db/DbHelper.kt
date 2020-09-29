package com.example.marvelisimo.db

import android.os.AsyncTask.execute
import com.example.marvelisimo.model.MarvelItem
import io.realm.Realm
import io.realm.kotlin.where

class DbHelper(

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

    fun removeFromDb(id: Int) {

        val query = realm.where<RealmMarvelItem>()
        query.equalTo("id", id)
        val result = query.findFirst()
        realm.executeTransaction { realm ->

            if (result != null) {
                result.deleteFromRealm()
            }
        }

    }
}
