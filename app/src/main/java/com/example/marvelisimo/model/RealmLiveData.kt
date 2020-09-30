package com.example.marvelisimo.model

import androidx.lifecycle.LiveData
import com.example.marvelisimo.db.RealmMarvelItem
import io.realm.RealmChangeListener
import io.realm.RealmResults

class RealmLiveData<T : RealmMarvelItem>(private val results: RealmResults<T>) :
    LiveData<RealmResults<T>>() {
    private val listener: RealmChangeListener<RealmResults<T>> =
        RealmChangeListener { results -> value = results }

    override fun onActive() {
        results.addChangeListener(listener)
    }

    override fun onInactive() {
        results.removeChangeListener(listener)
    }
}

fun <T: RealmMarvelItem> RealmResults<T>.asLiveData() = RealmLiveData<T>(this)