package com.example.marvelisimo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.marvelisimo.db.RealmMarvelItem
import com.example.marvelisimo.model.asLiveData
import io.realm.Realm

class FavoritesViewModel : ViewModel(){
    val realm = Realm.getDefaultInstance()

    fun loadFavorites(): LiveData<List<RealmMarvelItem>> {

        val mRealmLiveData = realm.where(RealmMarvelItem::class.java).findAllAsync().asLiveData()
        val list: LiveData<List<RealmMarvelItem>> = Transformations.map(mRealmLiveData) {
                realmResult ->
            realm.copyFromRealm(realmResult)
        }
        return list
    }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }
}