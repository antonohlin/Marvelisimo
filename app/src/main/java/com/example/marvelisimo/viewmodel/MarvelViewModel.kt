package com.example.marvelisimo.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelisimo.api.MarvelRetrofit
import com.example.marvelisimo.db.RealmMarvelItem
import com.example.marvelisimo.model.CharacterDataWrapper
import com.example.marvelisimo.model.ComicDataWrapper
import com.example.marvelisimo.model.MarvelItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.kotlin.where

class MarvelViewModel : ViewModel() {

    init {
        Log.i("Viewmodel","called")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ViewModel", "destroyed!")
    }

    @SuppressLint("CheckResult")
    fun callComics(): LiveData<ComicDataWrapper> {
        val resultList = MutableLiveData<ComicDataWrapper>()
        MarvelRetrofit.marvelService.getAllComics(limit = 100)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, err ->
                if (err?.message != null) Log.d("__", "Error getAllCharacters " + err.message)
                else {
                    resultList.postValue(result)
                }
            }
        return resultList
    }

    @SuppressLint("CheckResult")
    fun callCharacters(): LiveData<CharacterDataWrapper> {
        val resultList = MutableLiveData<CharacterDataWrapper>()
        MarvelRetrofit.marvelService.getAllCharacters(limit = 100)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, err ->
                if (err?.message != null) Log.d("__", "Error getAllCharacters " + err.message)
                else {
                    resultList.postValue(result)
                }
            }
        return resultList
    }

    @SuppressLint("CheckResult")
    fun searchCharacters(searchValue: String): LiveData<CharacterDataWrapper> {
        val resultList = MutableLiveData<CharacterDataWrapper>()
        MarvelRetrofit.marvelService.getAllCharacters(nameStartsWith = searchValue,limit = 100)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, err ->
                if (err?.message != null) Log.d("__", "Error getAllCharacters " + err.message)
                else {
                    resultList.postValue(result)
                }
            }
        return resultList
    }
    @SuppressLint("CheckResult")
    fun searchComics(searchValue: String): LiveData<ComicDataWrapper> {
        val resultList = MutableLiveData<ComicDataWrapper>()
        MarvelRetrofit.marvelService.getAllComics(titleStartsWith = searchValue,limit = 100)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, err ->
                if (err?.message != null) Log.d("__", "Error getAllCharacters " + err.message)
                else {
                    resultList.postValue(result)
                }
            }
        return resultList
    }

    fun loadFavorites(): List<MarvelItem> {
        val realm = Realm.getDefaultInstance()
        val favoritesResult = realm.where<RealmMarvelItem>().findAll()
        val marvelFavorites = favoritesResult.map { realmChar ->
            MarvelItem(realmChar.id, realmChar.name, realmChar.imageUrlBase, "jpg", realmChar.description, realmChar.url)
        }
        return marvelFavorites
    }



//
//    fun saveToDbCharacter(marvelItem: MarvelItem) {
//        val realm = io.realm.Realm.getDefaultInstance()
//        val marvelItemToDb = RealmCharacter()
//
//        marvelItemToDb.id = (0..10000000).random()
//        marvelItemToDb.name = marvelItem.title
//        marvelItemToDb.imageUrl = marvelItem.imageUrl
//
//        realm.beginTransaction()
//        realm.copyToRealm(marvelItemToDb)
//        realm.commitTransaction()

    }


